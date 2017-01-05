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

import java.util.ArrayList;

/**
 * 功能描述：自定义图表功能（柱型图表）
 * Created by hzhm on 2016/6/7.
 */
public class TextChart2 extends View {

    public static final int XYMargin = 40;

    private Paint mPaint;
    private int mWidth, mHeight;
    private int distance;

    private ArrayList<Integer> mXLevels = new ArrayList<>();
    private ArrayList<Integer> mYlevels = new ArrayList<>();
    private ArrayList<String> mColors = new ArrayList<>();
    private ArrayList<Integer> mLengths = new ArrayList<>();

    public TextChart2(Context context) {
        super(context);
    }

    public TextChart2(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setColor(Color.parseColor("#27a1e5"));
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mWidth = getWidth();
        mHeight = getHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawXYAndTitle(canvas);
        drawContent(canvas);
        drawPoint(canvas);
    }

    /**
     * 绘制X、Y坐标轴
     * @param canvas
     */
    private void drawXYAndTitle(Canvas canvas) {
        mPaint.setColor(Color.parseColor("#555555"));
        //绘制X、Y坐标轴
        canvas.drawLine(2 * XYMargin, mHeight - 4 * XYMargin, mWidth - 2 * XYMargin, mHeight - 4 * XYMargin, mPaint);
        canvas.drawLine(2 * XYMargin, mHeight - 4 * XYMargin, 2 * XYMargin, 2 * XYMargin, mPaint);
        //绘制X、坐标头标题
        mPaint.setColor(Color.parseColor("#27a1e5"));
        mPaint.setTextAlign(Paint.Align.LEFT);
        mPaint.setTextSize(30);
        canvas.drawText("投入量(H)", mWidth - 4 * XYMargin, mHeight - 3 * XYMargin+30, mPaint);
        canvas.drawText("产出量(H)", XYMargin, XYMargin, mPaint);
    }

    /**
     * 绘制Y轴参数及虚线
     * @param canvas
     */
    private void drawPoint(Canvas canvas) {
        Path mPath = new Path();
        Paint paint = new Paint();
        paint.setColor(Color.parseColor("#999999"));
        paint.setStyle(Paint.Style.STROKE);
        for(int index = 1; index < mXLevels.size() + 1; index++){
            mPath.moveTo(3*XYMargin + (index - 1) * distance,mLengths.get(index - 1));
            mPath.lineTo(2 * XYMargin,mLengths.get(index - 1));
            PathEffect effect = new DashPathEffect(new float[]{5,5,5,5},0);
            paint.setPathEffect(effect);
            //绘制虚线
            canvas.drawPath(mPath,paint);

            //绘制Y轴参数
            canvas.drawText(String.valueOf(mYlevels.get(index-1)),2 * XYMargin-35,mLengths.get(index - 1),mPaint);
        }
    }

    /**
     * 绘制柱型图
     * @param canvas
     */
    private void drawContent(Canvas canvas) {
//        mPaint.setColor(Color.parseColor("#f25a2b"));
//        canvas.drawRect(3 * XYMargin, 4 * XYMargin, 4 * XYMargin, mHeight - 4 * XYMargin, mPaint);
//        canvas.drawRect(5 * XYMargin, 6 * XYMargin, 6 * XYMargin, mHeight - 4 * XYMargin, mPaint);
//        canvas.drawRect(7 * XYMargin, 8 * XYMargin, 8 * XYMargin, mHeight - 4 * XYMargin, mPaint);
//        canvas.drawRect(9 * XYMargin, 10 * XYMargin, 10 * XYMargin, mHeight - 4 * XYMargin, mPaint);

        //绘制柱型图
        //计算出X、Y轴真实的宽高
        int RealWidth = mWidth - 4 * XYMargin;
        int RealHeight = mHeight - 6 * XYMargin;
        distance = RealWidth / mLengths.size();
        for (int index = 1; index < mXLevels.size() + 1; index++) {
            mPaint.setColor(Color.parseColor(mColors.get(index - 1)));
            canvas.drawRect(3*XYMargin + (index - 1) * distance, mLengths.get(index - 1), 2*XYMargin+index * distance, mHeight - 4 * XYMargin, mPaint);
        }

        //绘制X轴参数
        mPaint.setColor(Color.parseColor("#555555"));
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setTextSize(30);
        for(int index = 1; index < mXLevels.size() + 1; index++){
            canvas.drawText(String.valueOf(mXLevels.get(index-1)),3*XYMargin + (index - 1) * distance+distance/3,mHeight - 4 * XYMargin+30,mPaint);
        }
    }

    /**
     * 初始化颜色数组
     * @param colors
     */
    public void initColors(ArrayList<String> colors){
        mColors.remove(colors);
        mColors.addAll(colors);
    }

    /**
     * 初始化X轴参数数组
     * @param levels
     */
    public void initXLevels(ArrayList<Integer> levels){
        mXLevels.remove(levels);
        mXLevels.addAll(levels);
    }

    public void initYLevels(ArrayList<Integer> levels){
        mYlevels.remove(levels);
        mYlevels.addAll(levels);
    }

    /**
     * 初始化柱型高度数组
     * @param length
     */
    public void initLengths(ArrayList<Integer> length){
        mLengths.remove(length);
        mLengths.addAll(length);
    }
}
