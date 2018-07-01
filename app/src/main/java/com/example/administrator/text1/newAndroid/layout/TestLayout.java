package com.example.administrator.text1.newAndroid.layout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.administrator.text1.R;

/**
 * Created by Administrator on 2017/11/2.
 * 功能描述：Android系统四种常用的布局方式
 * LinearLayout：线性布局：layout_weight比重属性的使用
 * RelativeLayout：相对布局：alignParentLeft、centerInParent、layout_above、layout_below、toLeftOf...相关属性的使用
 * FrameLayout：帧布局:layout_gravity属性的使用
 * PercentFrameLayout/PercentFrameLayout：百分比布局
 */

public class TestLayout extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_layout);
    }
}
