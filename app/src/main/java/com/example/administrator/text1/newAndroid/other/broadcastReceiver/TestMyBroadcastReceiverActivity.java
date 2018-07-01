package com.example.administrator.text1.newAndroid.other.broadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.administrator.text1.R;
import com.example.administrator.text1.newAndroid.other.MyApplication;
import com.seaway.android.common.toast.Toast;

/**
 * @author HuangMing on 2017/12/28.
 *         功能描述：BroadcastReceiver广播接收器
 *         一、广播的类型？？？
 *         1、标准广播：一种完全异步的广播，广播发出之后，所以的接收器几乎都会在同一时间接收这条广播消息
 *         2、有序广播：一种同步的广播，广播发出之后，同一时间只会有一个广播接收器能够接收到这条广播，有先后顺序，且前面的接收器可以拦截广播致使后面的接收器无法接收...
 *         二、注册广播的两种方式？？？
 *         1、动态注册：java代码中完成注册(注：缺点在于程序启动之后才能接收到广播，因为注册的代码也是在启动之后)
 *         2、静态注册：即在AndroidManifest里面完成注册(注：主要处理那些程序未启动就能接收到的广播)
 *         (注：在onReceive()里面是不能开启新线程的，因此一些复杂的逻辑或是耗时的操作不要在这里面处理，这里面一般都是打开程序的其他组件、启动服务...)
 */

public class TestMyBroadcastReceiverActivity extends AppCompatActivity {

    private NetworkChangeReceiver networkChangeReceiver;
    private IntentFilter intentFilter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_thread);

        Button btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //发送自定义标准广播
                Intent intent = new Intent("com.example.administrator.text1.MY_BROADCAST");
//                sendBroadcast(intent);

                //发送自定义有序广播(注：在AndroidManifest里面设置当前广播的优先级)
                sendOrderedBroadcast(intent, null);
            }
        });
    }

    /**
     * 二、1、动态注册
     */
    private void register() {
        //实例化intentFilter指明接收相应广播的意图
        intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        networkChangeReceiver = new NetworkChangeReceiver();
        //动态注册广播
        registerReceiver(networkChangeReceiver, intentFilter);
    }

    class NetworkChangeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isAvailable()) {
                Toast.showToast(MyApplication.getContext(), "network on!");
            } else {
                Toast.showToast(MyApplication.getContext(), "network off!");
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //注销广播...
        unregisterReceiver(networkChangeReceiver);
    }
}
