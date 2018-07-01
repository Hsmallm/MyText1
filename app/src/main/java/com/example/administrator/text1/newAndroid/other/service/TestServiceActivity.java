package com.example.administrator.text1.newAndroid.other.service;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.administrator.text1.R;
import com.example.administrator.text1.newAndroid.other.LogUtil;

/**
 * @author HuangMing on 2017/12/5.
 *
 */

public class TestServiceActivity extends AppCompatActivity {

    private TestService.DownLoadBinder downLoadBinder;

    /**
     * service服务连接处理类...
     */
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //服务连接成功后，会返回IBinder对象，从而来对此进行操作
            downLoadBinder = (TestService.DownLoadBinder) service;
            downLoadBinder.startDownload();
            downLoadBinder.getProgress();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_service2);

        Button startBtn = (Button) findViewById(R.id.start);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TestServiceActivity.this, TestService.class);
                startService(intent);
            }
        });
        Button stopBtn = (Button) findViewById(R.id.stop);
        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TestServiceActivity.this, TestService.class);
                stopService(intent);
            }
        });

        Button bindBtn = (Button) findViewById(R.id.bind);
        bindBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TestServiceActivity.this, TestService.class);
                //绑定服务，获得一个持久化连接，这时候就会回调Service里面的的onBind(),获得一个IBinder实例，这样就可以自由的与服务之间进行通讯了
                //(注：BIND_AUTO_CREATE：表示活动与服务绑定后自动创建服务，这样会使得onCreate()得到执行，而onStartCommand()不会执行)
                bindService(intent, serviceConnection, BIND_AUTO_CREATE);
            }
        });
        Button unbindBtn = (Button) findViewById(R.id.unbind);
        unbindBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unbindService(serviceConnection);
            }
        });

        Button intentService = (Button) findViewById(R.id.intent_service);
        intentService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtil.d("TestServiceActivity","Thread id is"+Thread.currentThread().getId());
                Intent intent = new Intent(TestServiceActivity.this, TestIntentService.class);
                startService(intent);
            }
        });
    }
}
