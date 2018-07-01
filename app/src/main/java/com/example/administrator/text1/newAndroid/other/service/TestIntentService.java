package com.example.administrator.text1.newAndroid.other.service;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.example.administrator.text1.newAndroid.other.LogUtil;

/**
 * @author HuangMing on 2017/12/5.
 *         功能描述：IntentService：这个服务可以很好的处理在后台服务中创建异步、并自动停止服务（注：必须得提供一个无参=构造函数，并在其内部调用父类的有参函数；
 *         在onHandleIntent处理一些耗时操作，且根据IntentService的特性，执行完耗时操作后服务会自动停止...）
 */

public class TestIntentService extends IntentService {


    public TestIntentService() {
        super("TestIntentService");
    }

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public TestIntentService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        LogUtil.d("TestIntentService", "Thread id is" + Thread.currentThread().getId());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.d("TestIntentService", "onDestroy");
    }
}
