package com.example.administrator.text1.newAndroid.other;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

import com.example.administrator.text1.R;
import com.seaway.android.common.toast.Toast;

import java.io.File;

/**
 * @author HuangMing on 2017/11/30.
 *         功能描述：VideoView：这个类将视频的显示和控制集于一身，他的用法和MediaPlayer相似，因为他只是做了很好的封装，内部还是用的MediaPlayer来实现的
 */

public class TestMediaOfVideo extends AppCompatActivity {

    private VideoView videoView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_media_video);

        Button startBtn = (Button) findViewById(R.id.start);
        Button pauseBtn = (Button) findViewById(R.id.pause);
        Button resumeBtn = (Button) findViewById(R.id.resume);
        videoView = (VideoView) findViewById(R.id.video);
        if (ContextCompat.checkSelfPermission(TestMediaOfVideo.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(TestMediaOfVideo.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        } else {
            initVideoView();
        }
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoView.start();
            }
        });
        pauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoView.pause();
            }
        });
        resumeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoView.resume();
            }
        });
    }

    private void initVideoView() {
        File file = Environment.getExternalStorageDirectory();
        videoView.setVideoPath("/storage/emulated/0/system/media/audio/ringtones/1305256678video.mp4");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initVideoView();
            } else {
                Toast.showToast(MyApplication.getContext(), "拒绝权限将无法使用程序");
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (videoView != null) {
            videoView.suspend();
        }
    }
}
