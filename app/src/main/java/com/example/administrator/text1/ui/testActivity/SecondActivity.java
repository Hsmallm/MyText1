package com.example.administrator.text1.ui.testActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.example.administrator.text1.R;

/**
 * Created by hzhm on 2016/6/20.
 */
public class SecondActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_focusable);
        Log.e("onCreate","--------SecondActivity-onCreate--------");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("onStart","--------SecondActivity-onStart--------");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("onRestart","--------SecondActivity-onRestart--------");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("onResume","--------SecondActivity-onResume--------");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("onPause","--------SecondActivity-onPause--------");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("onStop","--------SecondActivity-onStop--------");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("onDestroy","--------SecondActivity-onDestroy--------");
    }
}
