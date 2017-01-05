package com.example.administrator.text1.ui.testCanvas.testCanvas;

import android.app.Activity;
import android.os.Bundle;

import com.example.administrator.text1.R;

/**
 * Created by hzhm on 2016/12/8.
 */

public class TestCanvasActivity extends Activity {

    private TestCanvasToChart testCanvasToChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_chart);

        testCanvasToChart = (TestCanvasToChart) findViewById(R.id.textChart);
        testCanvasToChart.setProgress(120);
    }
}
