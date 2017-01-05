package com.example.administrator.text1.utils.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;

/**
 * 功能描述：自定义ViewGroup(即自定义视图控件)，实现抽屉的展开和收起效果
 * 相关技术要点：1.onMeasure里面完成自身宽高的计算，以及给出所有子视图childview的建议宽高和计算模式
 *               2.onLayout里面完成对所有子视图childview的布局
 * Created by HM on 2016/4/13.
 */
public class HideOrShowFirstViewGroup extends ViewGroup implements ValueAnimator.AnimatorUpdateListener {

    private ValueAnimator valueAnimator;
    private boolean hideFirst = true;

    public HideOrShowFirstViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFocusableInTouchMode(true);
        setFocusable(true);
    }

    public boolean isHideFirst() {
        return hideFirst;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        getChildAt(0).layout(0, 0, getMeasuredWidth(), getChildAt(0).getMeasuredHeight());
        getChildAt(1).layout(getChildAt(0).getLeft(), getChildAt(0).getBottom(), getMeasuredWidth(), getChildAt(0).getBottom() + getChildAt(1).getMeasuredHeight());
        getChildAt(2).layout(getChildAt(1).getLeft(), getChildAt(1).getBottom(), getMeasuredWidth(), getChildAt(1).getBottom() + getChildAt(2).getMeasuredHeight());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(width, height);
        int centerItemHeight = getChildAt(1).getLayoutParams().height;
        getChildAt(1).measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(centerItemHeight, MeasureSpec.EXACTLY));
        getChildAt(0).measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(height - centerItemHeight, MeasureSpec.EXACTLY));
        getChildAt(2).measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(height - centerItemHeight, MeasureSpec.EXACTLY));
        if (hideFirst) {
            hideFirst(0);
        } else {
            showFirst(0);
        }
    }

    public void showFirst(int duration) {
        if (duration <= 0) {
            scrollTo(0, 0);
        } else {
            if (null == valueAnimator) {
                valueAnimator = new ValueAnimator();
//                valueAnimator.setInterpolator(new DecelerateInterpolator(1));
                valueAnimator.addUpdateListener(this);
            } else {
                valueAnimator.cancel();
            }
            valueAnimator.setDuration(duration);
            //(注：setIntValues(x,y),这个方法表示屏幕动画的移动,即由x移动到y)
            valueAnimator.setIntValues(getScrollY(), 0);
            Log.e("aaa", getScrollY()+"");
            valueAnimator.start();
        }
        hideFirst = false;
    }

    public void hideFirst(int duration) {
        if (duration <= 0) {
            //scrollTo() 方法可把内容滚动到指定的坐标。
            scrollTo(0, getChildAt(0).getMeasuredHeight());
        } else {
            if (null == valueAnimator) {
                valueAnimator = new ValueAnimator();
//                valueAnimator.setInterpolator(new DecelerateInterpolator(1));
                valueAnimator.addUpdateListener(this);
            } else {
                valueAnimator.cancel();
            }
            valueAnimator.setDuration(duration);
            valueAnimator.setIntValues(getScrollY(), getChildAt(0).getBottom());
            Log.e("bbb", getScrollY()+"");
            Log.e("ccc", getChildAt(0).getBottom()+"");
            valueAnimator.start();
        }
        hideFirst = true;
    }


    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        scrollTo(0, (Integer) animation.getAnimatedValue());
        Log.e("ddd", animation.getAnimatedValue()+"");
    }
}