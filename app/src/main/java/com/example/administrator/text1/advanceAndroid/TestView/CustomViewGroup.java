package com.example.administrator.text1.advanceAndroid.TestView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author HuangMing on 2018/1/31.
 *         功能描述：自定义ViewGroup
 *         上面提到了ViewGroup会为childView指定测量模式，下面简单介绍下三种测量模式：
 *         EXACTLY：表示设置了精确的值，一般当childView设置其宽、高为精确值、match_parent时，ViewGroup会将其设置为EXACTLY；
 *         AT_MOST：表示子布局被限制在一个最大值内，一般当childView设置其宽、高为wrap_content时，ViewGroup会将其设置为AT_MOST；
 *         UNSPECIFIED：表示子布局想要多大就多大，一般出现在AadapterView的item的heightMode中、ScrollView的childView的heightMode中；此种模式比较少见。
 *         (注：理解为，子布局childView在xml里面设置了精确值时候，父控件给他EXACTLY测量模式；子布局childView在xml里面设置了wrap_content时候，父控件给他AT_MOST测量模式；
 *         即为父类给子类设置的测量模式是由子类自己决定...)
 */

public class CustomViewGroup extends ViewGroup {

    public CustomViewGroup(Context context) {
        super(context);
    }

    public CustomViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 1、获得此ViewGroup上级容器为其推荐的宽和高，以及计算模式
     * 2、计算所有ChildView的宽度和高度 然后根据ChildView的计算结果，设置自己的宽和高(如果此容器是wrap_content)
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        /**
         * 获得此ViewGroup上级容器为其推荐的宽和高，以及计算模式
         */
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);


        // 计算出所有的childView的宽和高
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        /**
         * 记录如果是wrap_content是设置的宽和高
         */
        int width = 0;
        int height = 0;

        int cCount = getChildCount();

        int cWidth = 0;
        int cHeight = 0;
        MarginLayoutParams cParams = null;

        // 用于计算左边两个childView的高度
        int lHeight = 0;
        // 用于计算右边两个childView的高度，最终高度取二者之间大值
        int rHeight = 0;

        // 用于计算上边两个childView的宽度
        int tWidth = 0;
        // 用于计算下面两个childiew的宽度，最终宽度取二者之间大值
        int bWidth = 0;

        /**
         * 根据childView计算的出的宽和高，以及设置的margin计算容器的宽和高，主要用于容器是warp_content时
         */
        for (int i = 0; i < cCount; i++) {
            View childView = getChildAt(i);
            cWidth = childView.getMeasuredWidth();
            cHeight = childView.getMeasuredHeight();
            cParams = (MarginLayoutParams) childView.getLayoutParams();

            // 上面两个childView
            if (i == 0 || i == 1) {
                tWidth += cWidth + cParams.leftMargin + cParams.rightMargin;
            }

            if (i == 2 || i == 3) {
                bWidth += cWidth + cParams.leftMargin + cParams.rightMargin;
            }

            if (i == 0 || i == 2) {
                lHeight += cHeight + cParams.topMargin + cParams.bottomMargin;
            }

            if (i == 1 || i == 3) {
                rHeight += cHeight + cParams.topMargin + cParams.bottomMargin;
            }

        }

        width = Math.max(tWidth, bWidth);
        height = Math.max(lHeight, rHeight);

        /**
         * 如果是wrap_content设置为我们计算的值
         * 否则：直接设置为父容器计算的值
         */
        setMeasuredDimension((widthMode == MeasureSpec.EXACTLY) ? sizeWidth
                : width, (heightMode == MeasureSpec.EXACTLY) ? sizeHeight
                : height);
    }

    /**
     * 遍历所有childView根据其宽和高，以及margin进行布局
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int cCount = getChildCount();
        int cWidth = 0;
        int cHeight = 0;
        MarginLayoutParams cParams = null;
        for (int i = 0; i < cCount; i++) {
            View childView = getChildAt(i);
            cWidth = childView.getMeasuredWidth();
            cHeight = childView.getMeasuredHeight();
            cParams = (MarginLayoutParams) childView.getLayoutParams();
            int cl = 0;
            int ct = 0;
            int cr = 0;
            int cb = 0;
            switch (i) {
                case 0:
                    cl = cParams.leftMargin;
                    ct = cParams.topMargin;
                    break;
                case 1:
                    //这里计算右上角控件的左上角坐标时候(注意：这边是Margin值，都得减去，因为Margin值会填充整个容器的大小)
                    cl = getWidth() - cWidth - cParams.leftMargin
                            - cParams.rightMargin;
                    ct = cParams.topMargin;
                    break;
                case 2:
                    cl = cParams.leftMargin;
                    ct = getHeight() - cHeight - cParams.bottomMargin;
                    break;
                case 3:
                    cl = getWidth() - cWidth - cParams.leftMargin
                            - cParams.rightMargin;
                    ct = getHeight() - cHeight - cParams.bottomMargin;
                    break;
                default:
                    break;
            }
            cr = cl + cWidth;
            cb = ct + cHeight;
            childView.layout(cl, ct, cr, cb);
        }
    }

    @Override
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        //只需要ViewGroup能够支持margin即可
        return new MarginLayoutParams(getContext(), attrs);
    }
}
