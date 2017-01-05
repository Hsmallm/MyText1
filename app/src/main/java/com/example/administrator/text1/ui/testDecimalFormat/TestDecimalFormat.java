package com.example.administrator.text1.ui.testDecimalFormat;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.example.administrator.text1.R;
import com.example.administrator.text1.ui.testRunningTxt.RunningTextView;

import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * Created by hzhm on 2016/11/24.
 * 功能描述：测试小数点后数字的格式化工具类：DecimalFormat
 * 1、格式小数点后几位的格式
 *    format = new DecimalFormat("0.0"):格式小数点后一位；
 *    format = new DecimalFormat("0.00"):格式小数点后俩位；
 *    ......
 * 2、设置保留小数的取舍模式：format.setRoundingMode（注：如果是保留两位小数，则要输入小数点后三位，对第三位进行取舍）
 */

public class TestDecimalFormat extends Activity{

    private EditText runningEdit;
    private RunningTextView runningTxt;

    private DecimalFormat format;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_running_txt);

        runningEdit = (EditText) findViewById(R.id.running_edit);
        runningTxt = (RunningTextView) findViewById(R.id.running_txt);
        //实例化DecimalFormat对象，
        format = new DecimalFormat("0.0");
        //设置保留小数的取舍模式
        format.setRoundingMode(RoundingMode.UP);

        runningTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(runningEdit.getText())){
                    String txt = String.valueOf(runningEdit.getText());
                    runningTxt.setText(format.format(Double.parseDouble(txt)));
                }
            }
        });
    }
}
