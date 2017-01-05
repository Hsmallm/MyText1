package com.example.administrator.text1.ui.testActivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.example.administrator.text1.R;
import com.example.administrator.text1.common.base.BaseActivity;

/**
 * Created by hzhm on 2016/6/20.
 */
public class TestFragmentActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_setgesture);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.content,new TestFragment());
        ft.commitAllowingStateLoss();
    }
}
