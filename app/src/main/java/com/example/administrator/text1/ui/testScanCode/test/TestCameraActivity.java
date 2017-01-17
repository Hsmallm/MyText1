package com.example.administrator.text1.ui.testScanCode.test;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.ImageView;

import com.example.administrator.text1.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by hzhm on 2017/1/17.
 * <p>
 * 功能描述：测试Camera类的相关使用...
 */

public class TestCameraActivity extends Activity implements SurfaceHolder.Callback {

    private ImageView imageView;
    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;
    private Camera camera;
    private Timer mTimer;
    private TimerTask mTimerTask;

    private Camera.AutoFocusCallback mAutoFocusCallback;
    private Camera.PreviewCallback mPreviewCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_camera);

        surfaceView = (SurfaceView) findViewById(R.id.surfaceview);
        imageView = (ImageView) findViewById(R.id.image);
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);
        //自动聚焦接口回调
        mAutoFocusCallback = new Camera.AutoFocusCallback() {
            @Override
            public void onAutoFocus(boolean success, Camera camera) {
                if (success) {
                    camera.setOneShotPreviewCallback(mPreviewCallback);
                }
            }
        };

        //拍照接口回调
        mPreviewCallback = new Camera.PreviewCallback() {
            @Override
            public void onPreviewFrame(byte[] data, Camera camera) {
                if (data != null) {
                    Camera.Parameters parameters = camera.getParameters();
                    int imageFormat = parameters.getPreviewFormat();
                    Log.i("map", "Image Format: " + imageFormat);

                    Log.i("CameraPreviewCallback", "data length:" + data.length);
                    if (imageFormat == ImageFormat.NV21) {
                        // get full picture
                        Bitmap image = null;
                        int w = parameters.getPreviewSize().width;
                        int h = parameters.getPreviewSize().height;

                        Rect rect = new Rect(0, 0, w, h);
                        //*** BitmapFactory.decodeByteArray只支持一定的格式，camara支持的previewformat格式为NV21，
                        // 所以在获得bitmap时，需要进行转换。通过YuvImage类来转换成JPEG格式
                        YuvImage img = new YuvImage(data, ImageFormat.NV21, w, h, null);
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        if (img.compressToJpeg(rect, 100, baos)) {
                            image = BitmapFactory.decodeByteArray(baos.toByteArray(), 0, baos.size());
                            image = roteBitmap(90,image);
                            imageView.setImageBitmap(image);
                        }
                    }
                }
            }
        };

        //设置一个定时器...
        mTimer = new Timer();
        mTimerTask = new CameraTimer();
        mTimer.schedule(mTimerTask, 0, 5000);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        //初始化Camera
        camera = Camera.open();
        //设置Camera预览视图的大小，并你添加到相应的camera里面
        Camera.Parameters parameters = camera.getParameters();
        parameters.setPreviewSize(480, 320);
        camera.setParameters(parameters);
        try {
            //设置预览视图显示
            camera.setPreviewDisplay(surfaceHolder);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //设置预览视图开始显示
        camera.startPreview();
        camera.setDisplayOrientation(90);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (camera != null) {
            camera.stopPreview();
            camera.release();
            ;
            camera = null;
        }
    }

    private Bitmap roteBitmap(float angle, Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
    }

    /**
     * 自定义一个定时任务：设置相机的自动获取焦点
     */
    class CameraTimer extends TimerTask {
        @Override
        public void run() {
            if (camera != null) {
                camera.autoFocus(mAutoFocusCallback);
            }
        }
    }
}
