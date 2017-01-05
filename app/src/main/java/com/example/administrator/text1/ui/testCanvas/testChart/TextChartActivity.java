package com.example.administrator.text1.ui.testCanvas.testChart;

import android.app.Activity;
import android.os.Bundle;

import com.example.administrator.text1.R;
import com.example.administrator.text1.ui.testCanvas.testCanvas.TestCanvasToChart;

/**
 * 功能描述：自定义图表功能
 * Created by hzhm on 2016/6/6.
 */
public class TextChartActivity extends Activity{

    private TestCanvasToChart textChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_chart);

        textChart = (TestCanvasToChart) findViewById(R.id.textChart);
        textChart.setProgress(120);

        //初始化自定义图表的规格和属性
//        ArrayList<Integer> xLevels = new ArrayList<>();
//        ArrayList<Integer> yLevels = new ArrayList<>();
//        ArrayList<Integer> lengths = new ArrayList<>();
//        ArrayList<String> colors = new ArrayList<>();
//
//        xLevels.add(0);
//        xLevels.add(1);
//        xLevels.add(2);
//        xLevels.add(3);
//        xLevels.add(4);
//        xLevels.add(5);
//
//        yLevels.add(1000);
//        yLevels.add(800);
//        yLevels.add(600);
//        yLevels.add(400);
//        yLevels.add(200);
//        yLevels.add(100);
//
//        int distance = TextChart2.XYMargin;
//        lengths.add(4*distance);
//        lengths.add(6*distance);
//        lengths.add(8*distance);
//        lengths.add(10*distance);
//        lengths.add(12*distance);
//        lengths.add(14*distance);
//
//        colors.add("#f25a2b");
//        colors.add("#ff0000");
//        colors.add("#FFFF00");
//        colors.add("#00ff00");
//        colors.add("#27a1e5");
//        colors.add("#034f79");
//
//        textChart.initColors(colors);
//        textChart.initXLevels(xLevels);
//        textChart.initYLevels(yLevels);
//        textChart.initLengths(lengths);

//        ArrayList<Integer> mXLevel = new ArrayList<>();
//        ArrayList<Integer> mYLevel = new ArrayList<>();
//        ArrayList<String> mGridLevelText = new ArrayList<>();
//        ArrayList<Integer> mGridColorLevel = new ArrayList<>();
//        ArrayList<Integer> mGridTxtColorLevel = new ArrayList<>();
//        //初始化x轴坐标区间
//        mXLevel.add(0);
//        mXLevel.add(60);
//        mXLevel.add(90);
//        mXLevel.add(100);
//        mXLevel.add(110);
//        mXLevel.add(120);
//        //初始化y轴坐标区间
//        mYLevel.add(0);
//        mYLevel.add(90);
//        mYLevel.add(140);
//        mYLevel.add(160);
//        mYLevel.add(180);
//        mYLevel.add(200);
//        //初始化区间颜色
//        mGridColorLevel.add(Color.parseColor("#1FB0E7"));
//        mGridColorLevel.add(Color.parseColor("#4FC7F4"));
//        mGridColorLevel.add(Color.parseColor("#4FDDF2"));
//        mGridColorLevel.add(Color.parseColor("#90E9F4"));
//        mGridColorLevel.add(Color.parseColor("#B2F6F1"));
//        //初始化区间文字提示颜色
//        mGridTxtColorLevel.add(Color.parseColor("#EA8868"));
//        mGridTxtColorLevel.add(Color.parseColor("#EA8868"));
//        mGridTxtColorLevel.add(Color.parseColor("#EA8868"));
//        mGridTxtColorLevel.add(Color.WHITE);
//        mGridTxtColorLevel.add(Color.BLACK);
//        //初始化区间文字
//        mGridLevelText.add("异常");
//        mGridLevelText.add("过高");
//        mGridLevelText.add("偏高");
//        mGridLevelText.add("正常");
//        mGridLevelText.add("偏低");
//
//        textChart.initGridColorLevel(mGridColorLevel);
//        textChart.initGridLevelText(mGridLevelText);
//        textChart.initGridTxtColorLevel(mGridTxtColorLevel);
//        textChart.initXLevelOffset(mXLevel);
//        textChart.initYLevelOffset(mYLevel);
//        textChart.initTitleXY("投入量(H)", "产出量(H)");
    }
}
