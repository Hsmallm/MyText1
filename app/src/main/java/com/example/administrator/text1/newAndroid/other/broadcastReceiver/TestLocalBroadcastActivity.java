package com.example.administrator.text1.newAndroid.other.broadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.administrator.text1.R;
import com.example.administrator.text1.newAndroid.other.MyApplication;
import com.seaway.android.common.toast.Toast;

/**
 * @author HuangMing on 2017/12/28.
 *         功能描述：本地广播，即为发送和接收的广播仅限于本应用之内
 *         LocalBroadcastManager：本地广播管理对象，即对本地广播的发送和注册、注销进行管理...
 *         (注：本地广播不可以通过静态注册的方式来接收)
 */

public class TestLocalBroadcastActivity extends AppCompatActivity {

    private LocalBroadcastManager localBroadcastManager;
    private LocalReceiver localReceiver;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_thread);

        Button btn = (Button) findViewById(R.id.btn);
        //1、实例化本地广播管理对象
        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //2、通过localBroadcastManager发生本地广播
                Intent intent = new Intent("com.example.administrator.text1.LOCAL_BROADCAST");
                localBroadcastManager.sendBroadcast(intent);
            }
        });

        //3、通过localBroadcastManager注册本地广播
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.example.administrator.text1.LOCAL_BROADCAST");
        localReceiver = new LocalReceiver();
        localBroadcastManager.registerReceiver(localReceiver, intentFilter);
    }

    class LocalReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.showToast(MyApplication.getContext(), "this is TestLocalBroadcastActivity");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ////4、通过localBroadcastManager注销本地广播
        localBroadcastManager.unregisterReceiver(localReceiver);
    }
}

