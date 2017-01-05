package com.example.administrator.text1.utils;

import android.app.Application;
import android.content.Context;

/**
 * 功能描述：activity之间数据传递和共享的方式之Application
 * Created by hzhm on 2016/6/13.
 */
public class MyApplication extends Application {

    private static Context context;
    private String text;

//    public static Context getInstance(){
//        return context;
//    }
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
//        context = getApplicationContext();
//    }

    public void setText(String text){
        this.text = text;
    }

    public String getText(){
        return text;
    }
}
