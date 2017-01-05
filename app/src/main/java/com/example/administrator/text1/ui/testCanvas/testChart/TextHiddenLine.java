package com.example.administrator.text1.ui.testCanvas.testChart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.util.AttributeSet;
import android.view.View;

/**
 * 功能描述：自定义虚线
 * Created by hzhm on 2016/6/7.
 */
public class TextHiddenLine extends View {

    private Paint mPaint;

    public TextHiddenLine(Context context) {
        super(context);
    }

    public TextHiddenLine(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.DKGRAY);
        mPaint.setStyle(Paint.Style.STROKE);
        Path path = new Path();
        //设置路径的起始点坐标
        path.moveTo(0, 100);
        path.lineTo(480, 100);
        //设置虚线
        PathEffect effects = new DashPathEffect(new float[]{5, 5, 5, 5}, 0);
        mPaint.setPathEffect(effects);
        canvas.drawPath(path, mPaint);
    }
}
