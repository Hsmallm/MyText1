package com.example.administrator.text1.ui.testUITopBar;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.text1.R;
import com.seaway.android.common.toast.Toast;

import at.technikum.mti.fancycoverflow.FancyCoverFlow;
import at.technikum.mti.fancycoverflow.FancyCoverFlowAdapter;

/**
 * 自定义UI模版，即自定义一个自带自定义属性的新控件
 * Created by hzhm on 2016/7/9.
 */
public class testUITopBar extends Activity {

    private TopBar mTopBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_ui_topbar);

        mTopBar = (TopBar) findViewById(R.id.topbar);
        mTopBar.setOnTopBarClickListener(new TopBar.topbarClickListener() {
            @Override
            public void leftClick() {
                Toast.showToast(testUITopBar.this,"left");
            }

            @Override
            public void rightClick() {
                Toast.showToast(testUITopBar.this,"right");
            }
        });
        mTopBar.setLeftBtnIsVisible(true);
        mTopBar.setRightBtnIsVisible(true);
    }
}
