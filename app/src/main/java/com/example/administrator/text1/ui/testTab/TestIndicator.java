package com.example.administrator.text1.ui.testTab;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposePathEffect;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.text1.R;

import java.util.List;

/**
 * 功能描述：自定义指示器
 * 实现功能：动态绘制三角形指示图标、选中文本的颜色的改变、实现点击事件、实现容器的移动，当tab移动到最后一个时
 * Created by hzhm on 2016/7/7.
 */
public class TestIndicator extends LinearLayout {

    private LinearLayout linearLayout;

    private Paint mPaint;
    private Path mPath;
    private int mTriangleWidth;
    private int mTriangleHeight;
    private static final float RADIO_TRIANGLE_WIDTH = 1 / 6F;
    private final int DISTQANCE_TRIANGLE_WIDTH_MAX = (int) (getScreenWidth() / 3 * RADIO_TRIANGLE_WIDTH);//设置Triangle的最大宽度
    private int mInitTriangleX;//初始化的偏移位置
    private int mTriangleX;//移动式的偏移位置
    private int mTabVisibleCount;
    private static final int COUNT_DEFOUT = 4;
    private List<String> mTitles;

    public TestIndicator(Context context) {
        this(context, null);
    }

    public TestIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);

        //获取自定义属性
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TestIndicator);
        mTabVisibleCount = ta.getInt(R.styleable.Indicator_testcolor, COUNT_DEFOUT);
        ta.recycle();

        //初始化画笔
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.parseColor("#ffffff"));
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setPathEffect(new CornerPathEffect(3));//设置画笔连接点为圆角
    }

    /**
     * 绘制三角型
     *
     * @param canvas
     */
    @Override
    protected void dispatchDraw(Canvas canvas) {
        canvas.save();
        canvas.translate(mInitTriangleX + mTriangleX, getHeight());
        canvas.drawPath(mPath, mPaint);
        canvas.restore();
        super.dispatchDraw(canvas);

    }

    /**
     * 当控件的宽高发生变化时回调的方法
     *
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mTriangleWidth = (int) (w / mTabVisibleCount * RADIO_TRIANGLE_WIDTH);
        mTriangleWidth = Math.min(mTriangleWidth,DISTQANCE_TRIANGLE_WIDTH_MAX);
        mInitTriangleX = w / mTabVisibleCount / 2 - mTriangleWidth / 2;

        initTriangle();
    }

    /**
     * 初始化三角型
     */
    private void initTriangle() {

        mTriangleHeight = mTriangleWidth / 2;

        mPath = new Path();
        mPath.moveTo(0, 0);
        mPath.lineTo(mTriangleWidth, 0);
        mPath.lineTo(mTriangleWidth / 2, -mTriangleHeight);
        mPath.close();
    }

    /**
     * 当切换页面时相应的滚动三角区域（注：主要是通过其距离左边的偏移量决定）
     *
     * @param position       现在的位置
     * @param positionOffset 偏移量 0 ~ 1
     */
    public void scrollTriangleX(int position, float positionOffset) {
        int mTabWidth = getWidth() / mTabVisibleCount;
        mTriangleX = (int) ((position + positionOffset) * mTabWidth);

        if (mTabVisibleCount != 1) {
            //容器的移动，当tab移动到最后一个时
            if (position >= (mTabVisibleCount - 1) && positionOffset > 0 && getChildCount() > mTabVisibleCount) {
                this.scrollTo((int) ((position - (mTabVisibleCount - 1)) * mTabWidth + positionOffset * mTabWidth), 0);
            }
        } else {
            this.scrollTo((int) (position * mTabWidth + positionOffset * mTabWidth), 0);
        }

        //重绘
        invalidate();
    }

    /**
     * 当页面初始化完成，获取子视个数图并平均分配宽度
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        int mCount = getChildCount();
        if (mCount == 0) return;

        for (int i = 0; i < mCount; i++) {
            View view = getChildAt(i);
            LinearLayout.LayoutParams lp = (LayoutParams) view.getLayoutParams();
            lp.weight = 0;
            lp.width = getScreenWidth() / mTabVisibleCount;
            view.setLayoutParams(lp);
        }
    }

    /**
     * 获取屏幕宽度
     *
     * @return
     */
    public int getScreenWidth() {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics out = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(out);
        return out.widthPixels;
    }

    /**
     * 设置可见TextView个数
     *
     * @param count
     */
    public void setVisibleCount(int count) {
        mTabVisibleCount = count;
    }

    /**
     * 根据titles动态生成对应的TextView
     *
     * @param titles
     * @return
     */
    public void setTitles(List<String> titles, ViewPager viewpager) {
        if (titles != null && titles.size() > 0) {
            mTitles = titles;
            for (String title : mTitles) {
                addView(createTab(title));
            }
            setItemViewOnclick(viewpager);
        }
    }

    /**
     * 根据title生成对应的TextView
     *
     * @param title
     * @return
     */
    private View createTab(String title) {
        TextView tv = new TextView(getContext());
        LinearLayout.LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        lp.width = getScreenWidth() / mTabVisibleCount;
        tv.setText(title);
        tv.setGravity(Gravity.CENTER);
        tv.setTextColor(Color.parseColor("#ffffff"));
        tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
        tv.setLayoutParams(lp);
        return tv;
    }

    /**
     * 设置选择文本为高亮
     * @param por
     */
    public void setLightTextColor(int por){
        resetTextColor();
        TextView view = (TextView) getChildAt(por);
        view.setTextColor(Color.parseColor("#ff0000"));
    }

    /**
     * 重置颜色
     */
    private void resetTextColor(){
        for(int i = 0; i<getChildCount(); i++){
            TextView view = (TextView) getChildAt(i);
            view.setTextColor(Color.parseColor("#ffffff"));
        }
    }

    /**
     * 设置Tab的点击事件
     *
     * @param viewPager
     */
    private void setItemViewOnclick(final ViewPager viewPager) {
        int counts = getChildCount();
        for (int i = 0; i < counts; i++) {
            final int j = i;
            View view = getChildAt(i);
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewPager.setCurrentItem(j);
                }
            });
        }
    }
}
