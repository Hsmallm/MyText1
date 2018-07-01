package com.example.administrator.text1.newAndroid.other.broadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.administrator.text1.newAndroid.other.MyApplication;
import com.seaway.android.common.toast.Toast;

/**
 * @author HuangMing on 2017/12/28.
 */

public class BootCompleteReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.showToast(MyApplication.getContext(), "Boot Complete!");
    }
}
