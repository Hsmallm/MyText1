package com.example.administrator.text1.ui.testRunningTxt;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.administrator.text1.R;

import java.text.DecimalFormat;

/**
 * Created by hzhm on 2016/11/23.
 */

public class TestRunningTxt extends Activity {

    private EditText runningEdit;
    private RunningTextView runningTxt;

    private DecimalFormat format;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_running_txt);

        runningEdit = (EditText) findViewById(R.id.running_edit);
        runningEdit.setVisibility(View.GONE);
        runningTxt = (RunningTextView) findViewById(R.id.running_txt);

        //第一种：数字递减
        runningTxt.setIsSub(false);
        runningTxt.setStartNmb(100.00);
        runningTxt.playNumber(Double.parseDouble("10.00"));

        //第二种：数字递增（这种也是默认的数字递进方式）
//        runningTxt.playNumber(Double.parseDouble("10.00"));
        runningTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runningTxt.playNumber(Double.parseDouble("10.00"));
            }
        });
    }
}
