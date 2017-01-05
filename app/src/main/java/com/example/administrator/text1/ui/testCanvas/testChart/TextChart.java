package com.example.administrator.text1.ui.testCanvas.testChart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

/**
 * 功能描述：自定义图表功能(区块图表)
 * Created by hzhm on 2016/6/6.
 */
public class TextChart extends View {

    private Paint mPaint;
    private int mWidth, mHeight;
    private int mRealWidth, mRealHeight;
    private String mTitleX, mTitleY;

    //坐标图的xy交点原点坐标
    private int[] mZeroPos = new int[2];
    //坐标图的Y轴最大值坐标
    private int[] mMaxYPos = new int[2];
    //坐标图的X轴最大值坐标
    private int[] mMaxXPos = new int[2];

    //x轴坐标区间
    private ArrayList<Integer> mXLevel = new ArrayList<>();
    //y轴坐标区间
    private ArrayList<Integer> mYLevel = new ArrayList<>();
    //区间文字
    private ArrayList<String> mGridLevelText = new ArrayList<>();
    //区间颜色
    private ArrayList<Integer> mGridColorLevel = new ArrayList<>();
    //区间文字提示颜色
    private ArrayList<Integer> mGridTxtColorLevel = new ArrayList<>();

    private int mGridLevel = mXLevel.size() - 1;
    //title字符的大小
    private int mXYTitleTextSize = 40;
    private int mMeasureXpos, mMeasureYpos;

    public TextChart(Context context) {
        super(context);
    }

    public TextChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setAntiAlias(true);
        mPaint.setFilterBitmap(true);
    }

    public TextChart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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

        initPosition();
        drawXYTitle(canvas);
        drawXYLine(canvas);
        drawXYContent(canvas);
    }

    private void initPosition() {
        //初始化坐标图的xy交点原点坐标
        mZeroPos[0] = mXYTitleTextSize * 2;
        mZeroPos[1] = mHeight - mXYTitleTextSize * 4;
        //初始化坐标图的X轴最大值坐标
        mMaxXPos[0] = mWidth;
        mMaxXPos[1] = mHeight - mXYTitleTextSize * 4;
        //初始化坐标图的Y轴最大值坐标
        mMaxYPos[0] = mXYTitleTextSize * 2;
        mMaxYPos[1] = mXYTitleTextSize * 2;
    }

    /**
     * 画Y、X轴顶点的title
     *
     * @param canvas
     */
    private void drawXYTitle(Canvas canvas) {
        mPaint.setColor(Color.parseColor("#1FB0E7"));
        mPaint.setTextSize(30);
        mPaint.setTextAlign(Paint.Align.LEFT);
        //画Y、X轴顶的title
        canvas.drawText(mTitleY, mMaxYPos[0] - mXYTitleTextSize * 2, mMaxYPos[1] - mXYTitleTextSize + 20, mPaint);
        canvas.drawText(mTitleX, mMaxXPos[0] - mXYTitleTextSize * 4, mMaxXPos[1] + mXYTitleTextSize * 2, mPaint);
    }

    /**
     * 画XY轴
     *
     * @param canvas
     */
    private void drawXYLine(Canvas canvas) {
        mPaint.setColor(Color.DKGRAY);
        mPaint.setTextAlign(Paint.Align.LEFT);
        //画XY轴
        canvas.drawLine(mMaxYPos[0], mMaxYPos[1], mZeroPos[0], mZeroPos[1], mPaint);
        canvas.drawLine(mZeroPos[0], mZeroPos[1], mMaxXPos[0], mMaxXPos[1], mPaint);
    }

    /**
     * 画出区间内相应内容
     *
     * @param canvas
     */
    private void drawXYContent(Canvas canvas) {
        //区间的长度比坐标轴提示文字长度小1
        mGridLevel = mXLevel.size() - 1;
        //计算出X、Y轴的真实的坐标轴长度
        mRealWidth = (mWidth - mXYTitleTextSize * 2);
        mRealHeight = (mHeight - mXYTitleTextSize * 4);
        //分别计算出X、Y组坐标轴上的等分间距（注：由于Y轴上的我们没有绘制0，所以在等分时，是除以（mGridLevel + 1）
        int offsetX = mRealWidth / (mGridLevel);
        int offsetY = mRealHeight / (mGridLevel + 1);
        //循环绘制content
        for (int index = 0; index < mGridLevel + 1; index++) {
            mPaint.setColor(Color.DKGRAY);
            mPaint.setTextAlign(Paint.Align.RIGHT);
            mPaint.setTextSize(mXYTitleTextSize - 5);
            //1、绘制X轴的那些坐标区间点，包含0点坐标
            canvas.drawText(String.valueOf(mXLevel.get(index)), mZeroPos[0] + (index * offsetX), mZeroPos[1] + mXYTitleTextSize, mPaint);

            if (index != 0) {
                //2、绘制Y轴坐标区间点，不包含0点坐标，X轴已经画过了
                canvas.drawText(String.valueOf(mYLevel.get(index)), mZeroPos[0], mZeroPos[1] - (index * offsetY), mPaint);
            }

            if (index == mGridLevel) {
                //坐标区间 = 真实区间 + 1
                break;
            }

            mPaint.setColor(mGridColorLevel.get(mGridLevel - 1 - index));
            mPaint.setStyle(Paint.Style.FILL);
            //3、绘制区间叠加图谱方块，从远到0坐标，因为小的图会覆盖大的图
            canvas.drawRect(mMaxYPos[0], mMaxYPos[1] + index * offsetY, mMaxXPos[0] - index * offsetX, mMaxXPos[1], mPaint);

            mPaint.setColor(mGridTxtColorLevel.get(index));
            mPaint.setTextAlign(Paint.Align.RIGHT);
            mPaint.setTextSize(mXYTitleTextSize);
            //4、绘制每个方块状态区间的提示文字
            canvas.drawText(mGridLevelText.get(index), mMaxXPos[0] - offsetX * index - mXYTitleTextSize, mMaxYPos[1] + index * offsetY + mXYTitleTextSize, mPaint);
        }
        //绘制当前坐标
//        drawNotice(canvas, offsetX, offsetY);
    }

    private void drawNotice(Canvas canvas, int offsetX, int offsetY) {
        int realPosX = 0;
        int realPosY = 0;
        //计算传入的x值与真实屏幕坐标的像素值的百分比差值转换
        for (int index = 0; index < mGridLevel; index++) {
            if (mMeasureXpos >= mXLevel.get(index) && mMeasureXpos < mXLevel.get(index + 1)) {
                int subValue = mMeasureXpos - mXLevel.get(index);
                int offset = mXLevel.get(index + 1) - mXLevel.get(index);
                realPosX = mZeroPos[0] + index * offsetX + (subValue / offset);
                break;
            }
        }
        //计算传入的y值与真实屏幕坐标的像素值的百分比差值转换
        for (int index = 0; index < mGridLevel; index++) {
            if (mMeasureYpos >= mYLevel.get(index) && mMeasureYpos < mYLevel.get(index + 1)) {
                int subValue = mMeasureYpos - mYLevel.get(index);
                int offset = mYLevel.get(index + 1) - mYLevel.get(index);
                realPosY = mZeroPos[1] - index * offsetY - (offsetY - (subValue / offset));
                break;
            }
        }
        //画我们传入的坐标点的标记小红点
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(realPosX, realPosY, 8, mPaint);

        int[] centerPos = {mZeroPos[0] + mRealWidth / 2, mZeroPos[1] - mRealHeight / 2};

        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        RectF rectF = null;
        Path path = new Path();
        //画红点旁边的提示框和文字，有四个区域，然后提示框的小三角指标方位不同
        if (realPosX <= centerPos[0] && realPosY >= centerPos[1]) {
            //left-bottom
            //画三角形
            path.moveTo(realPosX + 5, realPosY + 5);
            path.lineTo(realPosX + 15, realPosY + 15);
            path.lineTo(realPosX + 15, realPosY - 15);
            //画矩形背景
            rectF = new RectF(realPosX + 15, realPosY - 40, realPosX + 200, realPosY + 30);
            canvas.drawRoundRect(rectF, 15, 15, mPaint);
            //画提示框的文字
            mPaint.reset();
            mPaint.setColor(Color.RED);
            mPaint.setTextSize(mXYTitleTextSize - 5);
            canvas.drawText("(" + mMeasureXpos + ", " + mMeasureYpos + ")", realPosX + 30, realPosY, mPaint);
        } else if (realPosX <= centerPos[0] && realPosY < centerPos[1]) {
            //left-top
            path.moveTo(realPosX + 5, realPosY + 5);
            path.lineTo(realPosX + 15, realPosY + 15);
            path.lineTo(realPosX + 15, realPosY - 15);

            rectF = new RectF(realPosX + 15, realPosY - 20, realPosX + 200, realPosY + 50);
            canvas.drawRoundRect(rectF, 15, 15, mPaint);

            mPaint.reset();
            mPaint.setColor(Color.RED);
            mPaint.setTextSize(mXYTitleTextSize - 5);
            canvas.drawText("(" + mMeasureXpos + ", " + mMeasureYpos + ")", realPosX + 30, realPosY + 20, mPaint);
        } else if (realPosX > centerPos[0] && realPosY >= centerPos[1]) {
            //right-bottom
            path.moveTo(realPosX - 5, realPosY + 5);
            path.lineTo(realPosX - 15, realPosY + 15);
            path.lineTo(realPosX - 15, realPosY - 15);

            rectF = new RectF(realPosX - 200, realPosY - 40, realPosX - 15, realPosY + 30);
            canvas.drawRoundRect(rectF, 15, 15, mPaint);

            mPaint.reset();
            mPaint.setColor(Color.RED);
            mPaint.setTextSize(mXYTitleTextSize - 5);
            canvas.drawText("(" + mMeasureXpos + ", " + mMeasureYpos + ")", realPosX - 180, realPosY, mPaint);
        } else if (realPosX > centerPos[0] && realPosY < centerPos[1]) {
            //right-top
            path.moveTo(realPosX - 5, realPosY + 5);
            path.lineTo(realPosX - 15, realPosY + 15);
            path.lineTo(realPosX - 15, realPosY - 15);

            rectF = new RectF(realPosX - 200, realPosY - 20, realPosX - 15, realPosY + 50);
            canvas.drawRoundRect(rectF, 15, 15, mPaint);

            mPaint.reset();
            mPaint.setColor(Color.RED);
            mPaint.setTextSize(mXYTitleTextSize - 5);
            canvas.drawText("(" + mMeasureXpos + ", " + mMeasureYpos + ")", realPosX - 180, realPosY + 30, mPaint);
        }

        path.close();
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawPath(path, mPaint);
    }

    //设置当前比值
    public void updateValues(int x, int y) {
        mMeasureXpos = x;
        mMeasureYpos = y;

        postInvalidate();
    }

    //设置XY轴顶角的title字体大小
    public void setTitleTextSize(int size) {
        mXYTitleTextSize = size;
    }

    //初始化X轴的坐标区间点值，可以不均等分
    public void initXLevelOffset(ArrayList<Integer> list) {
        mXLevel.clear();
        mXLevel.addAll(list);
    }

    //初始化Y轴的坐标区间点值，可以不均等分
    public void initYLevelOffset(ArrayList<Integer> list) {
        mYLevel.clear();
        mYLevel.addAll(list);
    }

    //初始化每个区间的提示文字，如果不想显示可以设置""
    public void initGridLevelText(ArrayList<String> list) {
        mGridLevelText.clear();
        mGridLevelText.addAll(list);
    }

    //初始化每个区间的颜色
    public void initGridColorLevel(ArrayList<Integer> list) {
        mGridColorLevel.clear();
        mGridColorLevel.addAll(list);
    }

    //初始化每个区间的提示文字颜色
    public void initGridTxtColorLevel(ArrayList<Integer> list) {
        mGridTxtColorLevel.clear();
        mGridTxtColorLevel.addAll(list);
    }

    //初始化XY轴title
    public void initTitleXY(String x, String y) {
        mTitleX = x;
        mTitleY = y;
    }
}
