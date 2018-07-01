package com.example.administrator.text1.newAndroid.layout;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.administrator.text1.R;
import com.example.administrator.text1.utils.ToastUtil;

/**
 * Created by Administrator on 2017/11/2.
 * 功能描述：自定义LinearLayout,实现标题栏公共功能的复用
 */

public class TitleLayout extends LinearLayout {

    public TitleLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //动态加载布局文件
        LayoutInflater.from(context).inflate(R.layout.activity_test_title,this);
        Button backBtn = (Button) findViewById(R.id.back);
        Button editBtn = (Button) findViewById(R.id.edit);
        backBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showNormalToast("This is a Back!");
            }
        });
        editBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showNormalToast("This is a Edit!");
            }
        });
    }
}
