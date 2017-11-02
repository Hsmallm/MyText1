package com.example.administrator.text1.newAndroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.administrator.text1.R;

/**
 * Created by Administrator on 2017/11/1.
 * 功能描述：Activity的启动模式(在AndroidManifest中给当前活动设置android:launchMode=""属性)
 * standard：为活动默认的启动模式，系统不管活动是不是在返回堆栈中，总是会创建新的实例
 * singleTop：如果当前活动已经处于堆栈的顶部，每当启动该活动时候，不会重新创建，直接使用堆栈顶部的活动（如果不位于堆栈顶部才会重新创建）
 * singleTask：让当前活动在整个应用的上下文中只存在一个实例
 * singleInstance：设置为该模式的活动，系统会创建一个新的堆栈来管理这个活动
 */

public class SecondActivity extends BaseActivity {

    public static final String TAG = "SecondActivity";

    private Button btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "Task id is " + getTaskId());
        setContentView(R.layout.activity_test_startup_type);

        btn = (Button) findViewById(R.id.btn);
        btn.setText("SecondActivity");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThirdActivity.newInstance(SecondActivity.this);
            }
        });
    }
}
