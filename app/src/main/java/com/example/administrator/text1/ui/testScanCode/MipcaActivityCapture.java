package com.example.administrator.text1.ui.testScanCode;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.example.administrator.text1.R;
import com.example.administrator.text1.ui.testScanCode.ScanCodeSup.camera.CameraManager;
import com.example.administrator.text1.ui.testScanCode.ScanCodeSup.decoding.CaptureActivityHandler;
import com.example.administrator.text1.ui.testScanCode.ScanCodeSup.decoding.InactivityTimer;
import com.example.administrator.text1.ui.testScanCode.ScanCodeSup.view.ViewfinderView;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

import java.io.IOException;
import java.util.Vector;

/**
 * @author Ryan.Tang
 *         <p>
 *         功能描述：二维码扫描器主界面...
 *         SurfaceView与ViewfinderView这两个类的分别作用是什么？？？
 *         SurfaceView：surfaceView的主要作用是与camera相关联,取景，即打开相机，将景象显示在屏幕上(注：surfaceview总之能够获相机硬件捕捉到的数据并显示出来)
 *         ViewfinderView：扫码自定义界面，用于绘制扫码的个性化界面...
 */
public class MipcaActivityCapture extends Activity implements Callback {

    private CaptureActivityHandler handler;//这个类处理所有消息传递包捕获的信息
    private ViewfinderView viewfinderView;//扫码自定义界面
    private boolean hasSurface;//是否正在...
    private Vector<BarcodeFormat> decodeFormats;
    private String characterSet;
    private InactivityTimer inactivityTimer;//任务控制器...
    private MediaPlayer mediaPlayer;//多媒体播放类...
    private boolean playBeep;//声音播放开关...
    private static final float BEEP_VOLUME = 0.10f;//声音的大小
    private boolean vibrate;//震动播放开关...

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture);
        //ViewUtil.addTopView(getApplicationContext(), this, R.string.scan_card);
        CameraManager.init(getApplication());
        viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);

        Button mButtonBack = (Button) findViewById(R.id.button_back);
        mButtonBack.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                MipcaActivityCapture.this.finish();
            }
        });
        hasSurface = false;
        inactivityTimer = new InactivityTimer(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //*** 实例化相机的预览对象...
        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        if (hasSurface) {
            initCamera(surfaceHolder);
        } else {
            surfaceHolder.addCallback(this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
        decodeFormats = null;
        characterSet = null;

        playBeep = true;
        //得到并实例化一个声音管理类（系统级服务）
        AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
        //如果当前返回的声音模式，不是正常的模式则设置声音播放开关关闭
        if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
            playBeep = false;
        }
        initBeepSound();
        vibrate = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        CameraManager.get().closeDriver();
    }

    @Override
    protected void onDestroy() {
        inactivityTimer.shutdown();
        super.onDestroy();
    }

    /**
     * 扫码之后的返回结果的处理...
     *
     * @param result
     * @param barcode
     */
    public void handleDecode(Result result, Bitmap barcode) {
        inactivityTimer.onActivity();
        playBeepSoundAndVibrate();
        String resultString = result.getText();
        if (resultString.equals("")) {
            Toast.makeText(MipcaActivityCapture.this, "Scan failed!", Toast.LENGTH_SHORT).show();
        } else {
            //当当前Activity结束时，将相关参数设置到intent、bundle里面，以给onActivityResult回调使用...
            Intent resultIntent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putString("result", resultString);
            bundle.putParcelable("bitmap", barcode);
            resultIntent.putExtras(bundle);
            this.setResult(RESULT_OK, resultIntent);
        }
        MipcaActivityCapture.this.finish();
    }

    /**
     * 初始化照相机
     *
     * @param surfaceHolder
     */
    private void initCamera(SurfaceHolder surfaceHolder) {
        try {
            CameraManager.get().openDriver(surfaceHolder);
        } catch (IOException ioe) {
            return;
        } catch (RuntimeException e) {
            return;
        }
        //初始化照相机的同时初始化消息处理对象
        if (handler == null) {
            handler = new CaptureActivityHandler(this, decodeFormats,
                    characterSet);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;
    }

    public ViewfinderView getViewfinderView() {
        return viewfinderView;
    }

    public Handler getHandler() {
        return handler;
    }

    public void drawViewfinder() {
        viewfinderView.drawViewfinder();
    }

    /**
     * 初始化声音...
     */
    private void initBeepSound() {
        if (playBeep && mediaPlayer == null) {
            //
            setVolumeControlStream(AudioManager.STREAM_MUSIC);
            //实例化多媒体播放对象
            mediaPlayer = new MediaPlayer();
            //设置音乐流类型
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            //并设置完成监听...
            mediaPlayer.setOnCompletionListener(beepListener);
            //实例化数据源
            AssetFileDescriptor file = getResources().openRawResourceFd(
                    R.raw.beep);
            try {
                //设置数据源
                mediaPlayer.setDataSource(file.getFileDescriptor(),
                        file.getStartOffset(), file.getLength());
                file.close();
                //设置音频流体积大小...
                mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
                //准备播放...
                mediaPlayer.prepare();
            } catch (IOException e) {
                mediaPlayer = null;
            }
        }
    }

    private static final long VIBRATE_DURATION = 200L;

    /**
     * 播放声音及其震动
     */
    private void playBeepSoundAndVibrate() {
        if (playBeep && mediaPlayer != null) {
            mediaPlayer.start();
        }
        if (vibrate) {
            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(VIBRATE_DURATION);
        }
    }

    /**
     * beep完成玩,倒带另一个队列。
     */
    private final OnCompletionListener beepListener = new OnCompletionListener() {
        public void onCompletion(MediaPlayer mediaPlayer) {
            mediaPlayer.seekTo(0);
        }
    };

}