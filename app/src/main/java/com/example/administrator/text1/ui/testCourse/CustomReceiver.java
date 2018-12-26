package com.example.administrator.text1.ui.testCourse;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * @author HuangMing on 2018/11/27.
 */

public class CustomReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String info = intent.getStringExtra("info");
        com.seaway.android.common.toast.Toast.showToast(com.example.administrator.text1.newAndroid.other.MyApplication.getContext(), info);
    }
}
