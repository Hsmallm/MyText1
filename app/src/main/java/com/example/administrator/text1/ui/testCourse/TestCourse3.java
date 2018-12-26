package com.example.administrator.text1.ui.testCourse;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.administrator.text1.R;

/**
 * @author HuangMing on 2018/11/27.
 *         Des：
 *         3、广播（Broadcast）可以向android系统中所有应用程序发送广播，而需要跨进程通讯的应用程序可以监听这些广播。
 *         一、广播（Broadcast）是什么？
 *         是在组件之间传播数据（Intent）的一种机制；这些组件可以位于同一进程或者不同的进程中，这样它就像Binder机制一样，起到通信的作用；
 *         广播机制非常灵活，每个应用程序都可以对自己感兴趣的广播进行注册，这个程序也只会收到自己所关心的广播内容，这些广播可能是来自于系统的，也可能是来自于其他应用程序的。
 *         为什么需要广播机制呢？广播机制，本质上它就是一种组件间的通信方式，如果是两个组件位于不同的进程当中，那么可以用Binder机制来实现，如果两个组件是在同一个进程中，
 *         那么它们之间可以用来通信的方式就更多了，广播机制和Binder机制不一样的地方在于，广播的发送者和接收者事先是不需要知道对方的存在的，
 *         这样带来的好处便是，系统的各个组件可以松耦合地组织在一起，这样系统就具有高度的可扩展性，容易与其它系统进行集成
 *         二、广播主要可以分为两种类型：
 *         <p>
 *         标准广播（Normal broadcasts）
 *         标准广播是一种完全异步执行的广播，在广播发出之后，所有的广播接收器几乎会在同一时刻接收到这条广播消息。这种广播效率比较高，但同时也意味着它是无法被截断的。
 *         <p>
 *         有序广播（Ordered broadcasts）
 *         有序广播则是一种同步执行的广播，在广播发出之后，同一时刻只会有一个广播接收器能够收到这条广播消息，当这个广播接收器中的逻辑执行完毕之后，广播才会继续传递
 *         三、两种注册广播的方式：动态注册和静态注册
 *         <p>
 *         动态注册就是在代码中注册
 *         <p>
 *         静态注册就是在AndroidManifest.xml 中注册
 *         四、本地广播
 *         系统广播可以被其他任何程序接收到，这样就很容易引起安全性的问题。比如我们发送的一些携带关键性数据的广播有可能被其他的应用程序截获，或者其他的程序不停地向我们的广播接收器里发送各种垃圾广播。
 *         为了能够简单地解决广播安全性问题，Android 引入了一套本地广播机制，使用这个机制发出的广播只能够在应用程序的内部进行传递，并且广播接收器也只能接收来自本应用程序发出的广播，这样所有的安全性问题就都不存在了。
 *         本地广播的用法并不复杂，主要就是使用了一个LocalBroadcastManager 来对广播进行管理，并提供了发送广播和注册广播接收器的方法。
 */

public class TestCourse3 extends AppCompatActivity implements View.OnClickListener {

    private Button btn;
    private Button btnSend;
    private Button btnNative;

    private NetWorkChangeReceiver netWorkChangeReceiver;
    private LocalBroadcastManager localBroadcastManager;
    private LocalReceiver localReceiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_course3);

        btn = (Button) findViewById(R.id.btn);
        btnSend = (Button) findViewById(R.id.btn_send);
        btnNative = (Button) findViewById(R.id.btn_native);

        btn.setOnClickListener(this);
        btnSend.setOnClickListener(this);
        btnNative.setOnClickListener(this);

        registerNativeBroadcast();
    }

    /**
     * 注册本地广播
     */
    private void registerNativeBroadcast() {
        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.owen.broadcast");
        localReceiver = new LocalReceiver();
        localBroadcastManager.registerReceiver(localReceiver, intentFilter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn:
                registerBroadcast();
                break;
            case R.id.btn_send:
                sendBroadcast();
                break;
            case R.id.btn_native:
                sendNativeBroadcast();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(netWorkChangeReceiver);
        localBroadcastManager.unregisterReceiver(localReceiver);
    }

    /**
     * 动态注册广播接收器(动态注册：优点，自由地控制注册与注销，在灵活性方面有很大优势；缺点，即必须要在程序启动之后才能接收到广播)
     * IntentFilter 的 addAction() 方法中添加了一个值为 android.net.conn.CONNECTIVITY_CHANGE 的 action，用于接收系统对网络状态改变广播。
     */
    private void registerBroadcast() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        netWorkChangeReceiver = new NetWorkChangeReceiver();
        registerReceiver(netWorkChangeReceiver, intentFilter);
    }

    /**
     * 发送自定义标准广播
     */
    private void sendBroadcast() {
        Intent intent = new Intent("com.example.administrator.text1.ui.testCourse.BootCompletedReceiver");
        intent.putExtra("info", "Hi, My Dear brother");
        sendBroadcast(intent);
    }

    /**
     * 发送本地广播
     */
    private void sendNativeBroadcast() {
        Intent intent = new Intent("com.owen.broadcast");
        intent.putExtra("owen", "Hi my local brother");
        localBroadcastManager.sendBroadcast(intent);
    }

    /**
     * 网络变化广播接收器
     */
    private class NetWorkChangeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isAvailable()) {
                com.seaway.android.common.toast.Toast.showToast(com.example.administrator.text1.newAndroid.other.MyApplication.getContext(), "网络已连接！");
            } else {
                com.seaway.android.common.toast.Toast.showToast(com.example.administrator.text1.newAndroid.other.MyApplication.getContext(), "网络不可用！");
            }
        }
    }

    /**
     * 本地广播接收器
     */
    private class LocalReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            com.seaway.android.common.toast.Toast.showToast(com.example.administrator.text1.newAndroid.other.MyApplication.getContext(), intent.getStringExtra("owen"));
        }
    }
}
