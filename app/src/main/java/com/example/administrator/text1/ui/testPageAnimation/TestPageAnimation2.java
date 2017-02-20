package com.example.administrator.text1.ui.testPageAnimation;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.text1.R;
import com.seaway.android.common.toast.Toast;

/**
 * 功能描述：Activity之间实现页面切换动画：overridePendingTransition();
 * Created by hzhm on 2016/8/10.
 */
public class TestPageAnimation2 extends Activity implements View.OnClickListener {

    private LinearLayout lin;
    private TextView title;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_pageanim);

        lin = (LinearLayout) findViewById(R.id.pageanim_ll);
        lin.setBackgroundColor(Color.parseColor("#ff0000"));
        title = (TextView) findViewById(R.id.pageanim_title);
        title.setText("Activity2");
        btn = (Button) findViewById(R.id.pageanim_btn);
        btn.setOnClickListener(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            int id = bundle.getInt("id");
            String name = bundle.getString("name");
            Toast.showToast(this, id + name);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pageanim_btn:
                startActivity(new Intent(this, TestPageAnimation.class));
                overridePendingTransition(0, 0);//当enterAnim、exitAnim都为0时，表示当前无任何动画
                break;
        }
    }
}
