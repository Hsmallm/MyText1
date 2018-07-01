package com.example.administrator.text1.newAndroid.other.service2;

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
 * @author HuangMing on 2017/12/27.
 *         功能描述：如何实现活动来控制服务中的相关操作？？？
 *         1、创建这个匿名类ServiceConnection，重写里面的两个方法，分别会在服务与活动成功绑定和解除绑定时候调用
 *         2、调用bindService(intent, serviceConnection, BIND_AUTO_CREATE);绑定服务
 *         3、BIND_AUTO_CREATE：表示服务与活动绑定之后会自动创建，此时服务只会执行onCreate()方法，而onStartCommand方法不会执行...
 *         此时onBind()也将得到执行，也就会返回给活动这个控制对象
 *         (注：根据Android系统机制，一个服务只要是被创建或是绑定之后，就会一直处于运行状态；但是此时销毁也得调用stopService和unbindService才能得以销毁)
 */

public class TestMyServiceActivity extends AppCompatActivity implements View.OnClickListener {

    private Button startBtn;
    private Button stopBtn;
    private Button bindBtn;
    private Button unBindBtn;
    private Button intentBtn;

    private MyService.DownloadBinder downloadBinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_service);

        startBtn = (Button) findViewById(R.id.start_service);
        stopBtn = (Button) findViewById(R.id.stop_service);
        bindBtn = (Button) findViewById(R.id.bind_service);
        unBindBtn = (Button) findViewById(R.id.unbind_service);
        intentBtn = (Button) findViewById(R.id.intent_service);

        startBtn.setOnClickListener(this);
        stopBtn.setOnClickListener(this);
        bindBtn.setOnClickListener(this);
        unBindBtn.setOnClickListener(this);
        intentBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.start_service) {
            //开启服务
            Intent intent = new Intent(this, MyService.class);
            startService(intent);
        } else if (id == R.id.stop_service) {
            //停止服务
            Intent intent = new Intent(this, MyService.class);
            stopService(intent);
        } else if (id == R.id.bind_service) {
            //绑定服务
            Intent intent = new Intent(this, MyService.class);
            //BIND_AUTO_CREATE：表示服务与活动绑定之后会自动创建，此时服务只会执行onCreate()方法，而onStartCommand方法不会执行...
            bindService(intent, serviceConnection, BIND_AUTO_CREATE);
        } else if (id == R.id.unbind_service) {
            //解除绑定服务
            unbindService(serviceConnection);
        } else if (id == R.id.intent_service) {
            //解除绑定服务
            LogUtil.d("TestMyServiceActivity", "thread id is" + Thread.currentThread().getId());
            Intent intent = new Intent(this,MyIntentService.class);
            startService(intent);
        }
    }

    /**
     * ServiceConnection：创建这个匿名类，重写里面的两个方法，分别会在服务与活动成功绑定和解除绑定时候调用
     */
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            downloadBinder = (MyService.DownloadBinder) service;
            downloadBinder.startDownload();
            downloadBinder.getProgress();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            LogUtil.d("TestMyServiceActivity", "onServiceDisconnected executed!");
        }
    };
}
