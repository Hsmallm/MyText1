/*
 * Copyright (C) 2008 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.administrator.text1.ui.testScanCode.ScanCodeSup.decoding;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.administrator.text1.R;
import com.example.administrator.text1.ui.testScanCode.MipcaActivityCapture;
import com.example.administrator.text1.ui.testScanCode.ScanCodeSup.camera.CameraManager;
import com.example.administrator.text1.ui.testScanCode.ScanCodeSup.view.ViewfinderResultPointCallback;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

import java.util.Vector;


/**
 * This class handles all the messaging which comprises the state machine for capture.
 * <p>
 * 功能描述：这个类处理所有消息传递包捕获的信息
 */
public final class CaptureActivityHandler extends Handler {

    private static final String TAG = CaptureActivityHandler.class.getSimpleName();

    private final MipcaActivityCapture activity;
    private final DecodeThread decodeThread;
    private State state;

    /**
     * 定义当前状态的几种枚举类型...
     */
    private enum State {
        PREVIEW,//开始
        SUCCESS,//成功
        DONE//结束
    }

    public CaptureActivityHandler(MipcaActivityCapture activity, Vector<BarcodeFormat> decodeFormats,
                                  String characterSet) {
        this.activity = activity;
        //初始化解码图像线程、并开启线程
        decodeThread = new DecodeThread(activity, decodeFormats, characterSet,
                new ViewfinderResultPointCallback(activity.getViewfinderView()));
        decodeThread.start();
        state = State.SUCCESS;
        // 开始自己捕捉预览和解码。
        CameraManager.get().startPreview();
        restartPreviewAndDecode();
    }

    @Override
    public void handleMessage(Message message) {
        switch (message.what) {
            case R.id.auto_focus:
                //如果当前状态为开始时，设置相机自动聚焦
                if (state == State.PREVIEW) {
                    CameraManager.get().requestAutoFocus(this, R.id.auto_focus);
                }
                break;
            case R.id.restart_preview:
                Log.d(TAG, "Got restart preview message");
                //如果已经重启预览，重启捕捉预览和解码器...
                restartPreviewAndDecode();
                break;
            case R.id.decode_succeeded:
                Log.d(TAG, "Got decode succeeded message");
                //如果解码已经成功，则回调当前结果...
                state = State.SUCCESS;
                Bundle bundle = message.getData();
                Bitmap barcode = bundle == null ? null :
                        (Bitmap) bundle.getParcelable(DecodeThread.BARCODE_BITMAP);

                activity.handleDecode((Result) message.obj, barcode);
                break;
            case R.id.decode_failed:
                // 如果解码已经失败，则重启捕捉预览......
                state = State.PREVIEW;
                CameraManager.get().requestPreviewFrame(decodeThread.getHandler(), R.id.decode);
                break;
            case R.id.return_scan_result:
                Log.d(TAG, "Got return scan result message");
                //如果返回扫码结果,则返回当前结果，并结束当前Activity
                activity.setResult(Activity.RESULT_OK, (Intent) message.obj);
                activity.finish();
                break;
            case R.id.launch_product_query:
                Log.d(TAG, "Got product query message");
                //如果返回查询,则跳转到相关的第三方网页...
                String url = (String) message.obj;
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                activity.startActivity(intent);
                break;
        }
    }

    /**
     * 取消当前操作...
     */
    public void quitSynchronously() {
        state = State.DONE;
        //停止捕捉预览
        CameraManager.get().stopPreview();
        Message quit = Message.obtain(decodeThread.getHandler(), R.id.quit);
        quit.sendToTarget();
        try {
            //停止或是阻塞当前线程...
            decodeThread.join();
        } catch (InterruptedException e) {
            // continue
        }

        // Be absolutely sure we don't send any queued up messages
        removeMessages(R.id.decode_succeeded);
        removeMessages(R.id.decode_failed);
    }

    /**
     * 重启捕捉预览和解码器...
     */
    private void restartPreviewAndDecode() {
        if (state == State.SUCCESS) {
            state = State.PREVIEW;
            //重启捕捉预览...
            CameraManager.get().requestPreviewFrame(decodeThread.getHandler(), R.id.decode);
            //重启自动获取焦点...
            CameraManager.get().requestAutoFocus(this, R.id.auto_focus);
            //重新绘制扫码界面
            activity.drawViewfinder();
        }
    }

}
