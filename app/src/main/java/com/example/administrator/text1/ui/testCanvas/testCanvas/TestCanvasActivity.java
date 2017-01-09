package com.example.administrator.text1.ui.testCanvas.testCanvas;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.example.administrator.text1.R;
import com.example.administrator.text1.ui.testRunningTxt.RunningTextView;

/**
 * Created by hzhm on 2016/12/8.
 *
 * 功能描述：实现金融圆环刻度滚动...
 */

public class TestCanvasActivity extends Activity {

    private TestCanvasToChart testCanvasToChart;
    private RunningTextView txtAddRate;
    private TextView txtRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_chart);

        testCanvasToChart = (TestCanvasToChart) findViewById(R.id.textChart);
        txtAddRate = (RunningTextView) findViewById(R.id.add_rate);
        txtRefresh = (TextView) findViewById(R.id.refresh);

        //设置数字滚动...
        txtAddRate.playNumber(Double.parseDouble("5.00"));
        txtRefresh.setClickable(true);
        txtRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //设置金融圆环刻度滚动...
                testCanvasToChart.setProgress(120);
                //设置数字滚动...
                txtAddRate.playNumber(Double.parseDouble("5.00"));
            }
        });
        //设置金融圆环刻度滚动...
        testCanvasToChart.setProgress(120);
    }
}
