package com.example.administrator.text1.advanceAndroid.TestView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author HuangMing on 2018/2/1.
 */

public class CustomViewGroup2 extends ViewGroup {

    public CustomViewGroup2(Context context) {
        super(context);
    }

    public CustomViewGroup2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 1、获取此ViewGroup上级控件传过来的建议宽高、测量模式
     * 2、计算所以子视图childView宽高，再通过所以子视图的宽高计算自身的宽高
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        measureChildren(widthMeasureSpec, heightMeasureSpec);

        int width = 0;
        int height = 0;

        int cWidth = 0;
        int cHeight = 0;
        MarginLayoutParams cParams = null;
        int cCount = getChildCount();

        int lHeight = 0;
        int rHeight = 0;
        int tWidth = 0;
        int bWidth = 0;

        for (int i = 0; i < cCount; i++) {
            View childView = getChildAt(i);
            cWidth = childView.getMeasuredWidth();
            cHeight = childView.getMeasuredHeight();
            cParams = (MarginLayoutParams) childView.getLayoutParams();

            if (i == 0 || i == 1) {
                tWidth = cWidth + cParams.leftMargin + cParams.rightMargin;
            }
            if (i == 2 || i == 3) {
                bWidth = cWidth + cParams.leftMargin + cParams.rightMargin;
            }
            if (i == 0 || i == 2) {
                lHeight = cHeight + cParams.topMargin + cParams.bottomMargin;
            }
            if (i == 1 || i == 3) {
                rHeight = cHeight + cParams.topMargin + cParams.bottomMargin;
            }
        }

        width = Math.max(tWidth, bWidth);
        height = Math.max(lHeight, rHeight);
        setMeasuredDimension((widthMode == MeasureSpec.EXACTLY) ? widthSize : width, (heightMode == MeasureSpec.EXACTLY) ? heightSize : height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int cWidth = 0;
        int cHeight = 0;
        MarginLayoutParams cParams = null;
        int cCount = getChildCount();

        int cl = 0;
        int ct = 0;
        int cr = 0;
        int cb = 0;

        for (int i = 0; i < cCount; i++) {
            View childView = getChildAt(i);
            cWidth = childView.getMeasuredWidth();
            cHeight = childView.getMeasuredHeight();
            cParams = (MarginLayoutParams) childView.getLayoutParams();

            switch (i) {
                case 0:
                    cl = cParams.leftMargin;
                    ct = cParams.topMargin;
                    break;
                case 1:
                    cl = getWidth() - cWidth - cParams.leftMargin - cParams.rightMargin;
                    ct = cParams.topMargin;
                    break;
                case 2:
                    cl = cParams.leftMargin;
                    ct = getHeight() - cHeight - cParams.topMargin - cParams.bottomMargin;
                    break;
                case 3:
                    cl = getWidth() - cWidth - cParams.leftMargin - cParams.rightMargin;
                    ct = getHeight() - cHeight - cParams.topMargin - cParams.bottomMargin;
                    break;
                case 4:
                    cl = getWidth() / 2 - cWidth / 2 - cParams.leftMargin - cParams.rightMargin;
                    ct = getHeight() / 2 - cHeight / 2 - cParams.topMargin - cParams.bottomMargin;
                    break;
                default:
                    break;
            }

            cr = cWidth + cl;
            cb = cHeight + ct;
            childView.layout(cl, ct, cr, cb);
        }

    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }
}
