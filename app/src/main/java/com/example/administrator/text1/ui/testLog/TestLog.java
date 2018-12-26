package com.example.administrator.text1.ui.testLog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.administrator.text1.R;
import com.example.administrator.text1.utils.LogUtil;

import java.util.Calendar;
import java.util.Date;

/**
 * @author HuangMing on 2018/11/26.
 */

public class TestLog extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "TestLog";

    private Button btLogV;
    private Button btLogD;
    private Button btLogI;
    private Button btLogW;
    private Button btLogE;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_log);

        btLogV = (Button) findViewById(R.id.bt_log_v);
        btLogD = (Button) findViewById(R.id.bt_log_d);
        btLogI = (Button) findViewById(R.id.bt_log_i);
        btLogW = (Button) findViewById(R.id.bt_log_w);
        btLogE = (Button) findViewById(R.id.bt_log_e);

        btLogV.setOnClickListener(this);
        btLogD.setOnClickListener(this);
        btLogI.setOnClickListener(this);
        btLogW.setOnClickListener(this);
        btLogE.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_log_v:
                MyLog.v(TAG, "This is logV");
                break;
            case R.id.bt_log_d:
                MyLog.delFile();
                break;
            case R.id.bt_log_i:
                testCalendar();
                break;
            case R.id.bt_log_w:
                Log.w(TAG, "This is logW");
                break;
            case R.id.bt_log_e:
                Log.e(TAG, "This is logE");
                break;
            default:
                break;
        }
    }

    /**
     * Calendar：是Android开发中需要获取时间时必不可少的一个工具类，通过这个类可以获得的时间信息还是很丰富的
     */
    private void testCalendar() {
        Date date = new Date();
        //获取实例
        Calendar calendar = Calendar.getInstance();
        //设置时间
        calendar.setTime(date);
        //设置每月的最大天数
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        //设置每天的最大小时
        calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
        //设置每小时的最大分钟
        calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
        //设置每分钟的最大秒数
        calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
        calendar.getTime();
    }
}
