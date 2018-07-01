package com.example.administrator.text1.newAndroid.other;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.seaway.android.common.toast.Toast;

import com.example.administrator.text1.R;

import java.io.File;
import java.io.IOException;

/**
 * @author HuangMing on 2017/11/30.
 *         功能描述：
 *         1、MediaPalyer：Android播放音频文件的实现类，他对多种格式的音频文件提供了全面的控制与实现
 *         2、getExternalStorageDirectory()和getExternalFilesDir()的区别：
 *         getExternalStorageDirectory()：这个是获取sdCard的根路径；
 *         getExternalFilesDir：这个不应该存在SD卡的根目录下，而是获取mnt/sdcard/Android/data/< package name >/files/…这个目录下的相应文件
 *                              当然也是在SD卡下，但是是SD卡下面的私有目录，访问时候也得添加访问权限
 */

public class TestMediaOfMusic extends AppCompatActivity {

    private MediaPlayer mediaPlayer = new MediaPlayer();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_sharedpreferences);

        Button startBtn = (Button) findViewById(R.id.btn);
        startBtn.setText("start");
        Button pauseBtn = (Button) findViewById(R.id.btn2);
        pauseBtn.setText("pause");
        Button stopBtn = (Button) findViewById(R.id.btn3);
        stopBtn.setText("stop");
        Button btn4 = (Button) findViewById(R.id.btn4);
        btn4.setText("App独立文件路径");
        Button btn5 = (Button) findViewById(R.id.btn5);
        btn5.setText("App专属文件路径");
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
            }
        });
        pauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.pause();
            }
        });
        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //重新设置音频文件到播放前的状态...
                mediaPlayer.reset();
                initMedia();
            }
        });

        //a、getExternalStorageDirectory()：这个是获取sdCard的根路径
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //这个sdCard的路径为/storage/emulated/0 即为SD卡根路径
                File sdCard = Environment.getExternalStorageDirectory();
                File pictures = new File(sdCard, "Pictures");

                File directory_picture = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                Toast.showToast(MyApplication.getContext(), directory_picture.getPath());
            }
        });

        //b、getExternalFilesDir：这个不应该存在SD卡的根目录下
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //1、存储在internal storage,这是你app私有的目录，你的shared preference文件，数据库文件，都存储在这里.
                //(打印路径为：/data/user/0/com.example.administrator.text1/files)
                File dir = getFilesDir();
//                Toast.showToast(MyApplication.getContext(), dir.getPath());

                //2、存储在external storage，这类文件不应该存在SD卡的根目录下，
                //而应该存在/storage/emulated/0/Android/data/< package name >/files/…这个目录下。这类文件应该随着App的删除而一起删除。
                //(注：getExternalFilesDir(null)参数传入的为null，这样默认访问的是files文件夹，我们可以指定子文件夹)
                File externalDir = getExternalFilesDir("Caches");
                Toast.showToast(MyApplication.getContext(), externalDir.getPath());
            }
        });

        //检查设备是否安装SD卡...
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            //检查是否获取的SD卡的访问权限
            if (ContextCompat.checkSelfPermission(TestMediaOfMusic.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                //请求获取的SD卡的访问权限
                ActivityCompat.requestPermissions(TestMediaOfMusic.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            } else {
                initMedia();
            }
        } else {
            Toast.showToast(MyApplication.getContext(), "未插入SD卡");
        }

    }

    private void initMedia() {
        try {
            File file = new File(Environment.getExternalStorageDirectory(), "2015959315938631.ogg");
            mediaPlayer.setDataSource("/storage/emulated/0/system/media/audio/ringtones/Over_the_horizon.ogg");
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取权限回调
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initMedia();
            } else {
                Toast.showToast(MyApplication.getContext(), "拒绝权限将无法使用应用程序");
                finish();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }
}
