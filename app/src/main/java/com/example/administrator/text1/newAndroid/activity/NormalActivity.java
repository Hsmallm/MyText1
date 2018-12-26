package com.example.administrator.text1.newAndroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.administrator.text1.R;

/**
 * Created by Administrator on 2017/11/1.
 */

public class NormalActivity extends AppCompatActivity {

    public static final String TAG = "NormalActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal);
    }

    /**
     * Activity活动被回收之前调用次方法（主要用于活动被回收前保持一些关键的数据）
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        String tempData = "something test Data";
        outState.putString(TAG,tempData);
    }

    /**
     * onStart()：Activity有不可见到可见时候调用
     */
    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG,"onStart");
    }

    /**
     * onResume()：Activity转杯和用户交互前被调用
     */
    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,"onResume");
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode, @Nullable Bundle options) {
        super.startActivityForResult(intent, requestCode, options);
        Log.d(TAG,"startActivityForResult");
    }

    /**
     * onPause()：系统准备启动另外一个活动时候调用
     */
    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG,"onPause");
    }

    /**
     * onStop()：当前Activity完全不可见时候被调用
     */
    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG,"onStop");
    }

    /**
     * onDestroy()：当前Activity被销毁前被调用
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"onDestroy");
    }

    /**
     * onRestart()：当前Activity由停止状态变为运行状态之前被调用
     */
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG,"onRestart");
    }
}
