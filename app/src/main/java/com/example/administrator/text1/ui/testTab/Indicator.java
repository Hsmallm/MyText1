package com.example.administrator.text1.ui.testTab;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.example.administrator.text1.R;

/**
 * 功能描述：自定义指示器，实现切换下划线滑动效果
 * Created by hzhm on 2016/7/6.
 */
public class Indicator extends LinearLayout {

    private Paint mPaint;

    private int mLeft;//下划线距离坐标的距离（注：主要就是看mLeft值的不断变化来绘制）
    private int mTop;//下划线距离上边的距离
    private int mWidth;//下划线的宽度
    private int mHeight = 5;//下划线的高度
    private int mColor;//下划线的颜色
    private int mChildCount;//子View的个数


    public Indicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 必须设置背景，否则onDraw不执行
        // (注：ViewGroup默认是不走onDraw方法的，因为ViewGroup是不需要绘制的，需要绘制的是ViewGroup的子item，这里我们设置一下背景颜色，ViewGroup就会走onDraw方法去绘制它自己的背景)
        setBackgroundColor(Color.TRANSPARENT);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.Indicator,0,0);
        mColor = ta.getColor(R.styleable.Indicator_testcolor,0X0000FF);
        ta.recycle();

        mPaint = new Paint();
        mPaint.setColor(mColor);
        mPaint.setAntiAlias(true);
    }

    /**
     * 当初始化视图成功后，获取控件里面子视图数
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mChildCount = getChildCount();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mTop = getMeasuredHeight() + 10;
        int width = getMeasuredWidth();
        int height = mHeight + mTop;
        mWidth = width/mChildCount;
        setMeasuredDimension(width,height);
    }

    /**
     * 画出指示器下划线
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        Rect rect = new Rect(mLeft,mTop,mLeft+mWidth,mTop+mHeight);
        canvas.drawRect(rect,mPaint);
        super.onDraw(canvas);
    }

    /**
     * 指示符滚动
     * @param position 现在的位置
     * @param offset  偏移量 0 ~ 1
     */
    public void scroll(int position, float offset){
        mLeft = (int) ((position + offset)*mWidth);
        invalidate();
    }
}
