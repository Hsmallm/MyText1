package com.example.administrator.text1.newAndroid.other;

import android.app.Application;
import android.content.Context;

/**
 * @author HuangMing on 2017/11/27.
 *         功能描述：自定义一个Application，存储一些全局的状态信息(注：Application的生命周期与应用程序的生命周期是一样的)
 */

public class MyApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
}
