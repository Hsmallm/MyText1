package com.example.administrator.text1.newAndroid.other.service2;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.example.administrator.text1.newAndroid.other.LogUtil;

/**
 * @author HuangMing on 2017/12/27.
 *         功能描述：IntentService：这个服务是一个代码会在异步执行、且耗时操作执行完会自动停止服务
 */

public class MyIntentService extends IntentService {

    public MyIntentService() {
        //创建这个无参的构造函数，必须的调用父类的有参的构造函数
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        LogUtil.d("MyIntentService", "thread id is" + Thread.currentThread().getId());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.d("MyIntentService", "onDestroy executed!");
    }
}
