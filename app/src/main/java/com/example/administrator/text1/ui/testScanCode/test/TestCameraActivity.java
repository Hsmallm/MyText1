package com.example.administrator.text1.ui.testScanCode.test;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.administrator.text1.R;
import com.google.android.gms.common.api.GoogleApiClient;

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

    private static final float BEEP_VOLUME = 0.10f;
    private static final long VIBRATE_DURATION = 200L;

    private ImageView imageView;
    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;
    private Camera camera;
    private Timer mTimer;
    private TimerTask mTimerTask;

    private MediaPlayer mediaPlayer;
    private boolean palyBeep;

    private GoogleApiClient client;

    //自动聚焦接口回调
    private Camera.AutoFocusCallback mAutoFocusCallback = new Camera.AutoFocusCallback() {
        @Override
        public void onAutoFocus(boolean success, Camera camera) {
            if (success) {
                //设置拍照监听...
                camera.setOneShotPreviewCallback(mPreviewCallback);
            }
        }
    };

    //拍照接口回调
    private Camera.PreviewCallback mPreviewCallback = new Camera.PreviewCallback() {
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
                        image = roteBitmap(90, image);
                        imageView.setImageBitmap(image);
                        playBeepSoundAndVibrate();
                    }
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_camera);

        surfaceView = (SurfaceView) findViewById(R.id.surfaceview);
        imageView = (ImageView) findViewById(R.id.image);
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);

        initBeepSound();
        setTimer();
    }

    /**
     * 设置一个定时器,完成自动获取焦点、拍照、显示功能...
     */
    private void setTimer(){
        mTimer = new Timer();
        mTimerTask = new CameraTimer();
        mTimer.schedule(mTimerTask, 0, 5000);
    }

    /**
     * 初始化声音播放组件
     */
    private void initBeepSound() {
        palyBeep = true;
        //获取系统的声音管理对象...
        AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        //判断当前声音模式是否为‘正常’模式
        if (audioManager.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
            palyBeep = false;
        }

        if (palyBeep && mediaPlayer != null) {
            setVolumeControlStream(AudioManager.STREAM_MUSIC);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnCompletionListener(listener);
            AssetFileDescriptor file = getResources().openRawResourceFd(R.raw.beep);
            try {
                mediaPlayer.setDataSource(file.getFileDescriptor(), file.getStartOffset(), file.getLength());
                file.close();
                mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
                mediaPlayer.prepare();
            } catch (IOException e) {
                mediaPlayer = null;
            }
        }
    }

    /**
     * 播放声音、开始震动
     */
    private void playBeepSoundAndVibrate() {
        if (palyBeep && mediaPlayer != null) {
            mediaPlayer.start();
        }
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(VIBRATE_DURATION);
    }

    /**
     * 播放完成时监听...
     */
    private final MediaPlayer.OnCompletionListener listener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            mp.seekTo(0);
        }
    };

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        //初始化Camera
        camera = Camera.open();
        //设置Camera预览视图的大小，并你添加到相应的camera里面
        Camera.Parameters parameters = camera.getParameters();
        // 获取当前屏幕管理器对象
        WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        // 获取屏幕信息的描述类
        Display display = windowManager.getDefaultDisplay();
        parameters.setPreviewSize(480,320);
        camera.setParameters(parameters);
        try {
            //设置预览视图显示
            camera.setPreviewDisplay(surfaceHolder);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //设置开始预览视图显示（打开相机）
        camera.startPreview();
        camera.setDisplayOrientation(90);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (camera != null) {
            //停止预览视图的显示...
            camera.stopPreview();
            camera.release();
            camera = null;
        }
    }

    /**
     * 旋转图片
     *
     * @param angle
     * @param bitmap
     * @return
     */
    private Bitmap roteBitmap(float angle, Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    /**
     * 自定义一个定时任务：设置相机的自动获取焦点
     */
    class CameraTimer extends TimerTask {
        @Override
        public void run() {
            if (camera != null) {
                //设置自动获取焦点监听...
                camera.autoFocus(mAutoFocusCallback);
            }
        }
    }
}
