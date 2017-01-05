package com.example.administrator.text1;

import android.app.Application;
import android.content.Context;

/**
 * Created by Administrator on 2016/2/25.
 */
public class TextAppliction extends Application{

    private static Context context;

    public static Context getInstance(){
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }
}
