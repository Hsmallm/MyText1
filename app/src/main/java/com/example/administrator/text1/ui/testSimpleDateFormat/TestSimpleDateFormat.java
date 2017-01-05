package com.example.administrator.text1.ui.testSimpleDateFormat;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.text1.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 功能描述：SimpleDateFormat：格式化时间工具类
 * 相关字段的含有：
 HH：24小时制(0-23)
 mm：分
 ss：秒
 S：毫秒
 E：星期几
 D：一年中的第几天
 F：一月中的第几个星期(会把这个月总共过的天数除以7)
 w：一年中的第几个星期
 W：一月中的第几星期(会根据实际情况来算)
 a：上下午标识
 k：和HH差不多，表示一天24小时制(1-24)。
 K：和hh<span style="font-family: Arial, Helvetica, sans-serif;">差不多</span><span style="font-family: Arial, Helvetica, sans-serif;">，表示一天12小时制(0-11)。</span>
 z：表示时区
 * Created by hzhm on 2016/8/11.
 */
public class TestSimpleDateFormat extends Activity {

    private TextView txt;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_pageanim);

        txt = (TextView) findViewById(R.id.pageanim_title);
        btn = (Button) findViewById(R.id.pageanim_btn);
        btn.setVisibility(View.GONE);

        long str = System.currentTimeMillis();
        Date date = new Date(str);
        /// 注：天的大小写的区分：大写的DD表示：一年之中的多少天；而小写的dd表示：一个月之中的多少号
        SimpleDateFormat format  = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat format2  = new SimpleDateFormat("yyyy-MM-DD");
        SimpleDateFormat format3  = new SimpleDateFormat("现在是yyyyyy年MM月dd号，下午HH时mm分ss秒SS毫秒，星期E,今年的第D天，这个月的第F星期，" +
                "今年的第w星期，这个月的第W星期，今天的a， k1~24制时间 K0-11小时制时间 z时区");

        String time = format3.format(date);
        txt.setText(time);
    }
}
