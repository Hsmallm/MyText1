package com.example.administrator.text1.ui.testPageAnimation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.administrator.text1.R;

/**
 * 功能描述：Activity之间实现页面切换动画：overridePendingTransition(int enterAnim, int exitAnim);
 * enterAnim：为进入的Activity页面动画
 * exitAnim: 为退出的Activity的页面的动画
 * Created by hzhm on 2016/8/10.
 */
public class TestPageAnimation extends Activity implements View.OnClickListener{

    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_pageanim);

        btn = (Button) findViewById(R.id.pageanim_btn);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.pageanim_btn:
                startActivity(new Intent(TestPageAnimation.this,TestPageAnimation2.class));
                overridePendingTransition(R.animator.anim_in_from_top,R.animator.anim_out_from_bottom);
                break;
        }
    }
}
