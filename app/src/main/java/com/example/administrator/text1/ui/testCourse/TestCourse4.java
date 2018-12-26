package com.example.administrator.text1.ui.testCourse;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.administrator.text1.R;

/**
 * @author HuangMing on 2018/11/27.
 *         Des：
 *         4、Service和Content Provider类似，也可以访问其他应用程序中的数据，Content Provider返回的是Cursor对象，而Service返回的是Java对象，这种可以跨进程通讯的服务叫AIDL服务。
 *         一、AIDL
 *         AIDL （Android Interface Definition Language），Android接口定义语言，Android提供的IPC （Inter Process Communication，进程间通信）的一种独特实现。
 *         二、为什么使用AIDL
 *         使用AIDL只有在你允许来自不同应用的客户端跨进程通信访问你的service，并且想要在你的service种处理多线程的时候才是必要的。
 *         如果你不需要执行不同应用之间的IPC并发，你应该通过实现Binder建立你的接口，或者如果你想执行IPC，但是不需要处理多线程。那么使用Messenger实现你的接口。
 */

public class TestCourse4 extends AppCompatActivity implements View.OnClickListener {

    private Button btCourse;

    private IMyAidlInterface iMyAidlInterface;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_course4);

        btCourse = (Button) findViewById(R.id.bt_course);
        btCourse.setOnClickListener(this);

        //绑定服务
        Intent intent = new Intent(this, UpdateService.class);
        bindService(intent, new Connection(), Context.BIND_AUTO_CREATE);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_course:
                try {
                    if (iMyAidlInterface != null) {
                        com.seaway.android.common.toast.Toast.showToast(com.example.administrator.text1.newAndroid.other.MyApplication.getContext(), iMyAidlInterface.getValue());
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }

    private class Connection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //连接成功监听回调，通过service实例化AIDL接口
            iMyAidlInterface = IMyAidlInterface.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    }
}
