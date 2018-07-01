package com.example.administrator.text1.newAndroid.other.broadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.administrator.text1.newAndroid.other.MyApplication;
import com.seaway.android.common.toast.Toast;

import java.util.logging.Handler;

/**
 * @author HuangMing on 2017/12/28.
 */

public class MyBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.showToast(MyApplication.getContext(), "receiver in MyBroadcastReceiver!");
        Log.d("MyBroadcastReceiver", "received in received in received in");
    }
}
