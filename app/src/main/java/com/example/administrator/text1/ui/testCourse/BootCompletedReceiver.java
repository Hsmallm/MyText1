package com.example.administrator.text1.ui.testCourse;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * @author HuangMing on 2018/11/27.
 */

public class BootCompletedReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // 不要再 onReceive() 方法中添加过多的逻辑或者进行任何耗时的操作，
        // 当onReceive()方法运行了较长时间而没有结束时，程序就会出现ANR(Application Not Responding)。
        // 广播接收器更多是扮演打开其他组件的角色，比如创建一条状态栏通知，或者启动一个服务。
        com.seaway.android.common.toast.Toast.showToast(com.example.administrator.text1.newAndroid.other.MyApplication.getContext(), "接收到开机广播啦！");
    }
}
