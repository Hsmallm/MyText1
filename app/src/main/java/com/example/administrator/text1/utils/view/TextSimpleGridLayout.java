package com.example.administrator.text1.utils.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.text1.R;

/**
 * 功能描述：自定义一个ViewGroup控件，实现自定义的九风格布局
 * Created by HM on 2016/5/13.
 */
public class TextSimpleGridLayout extends ViewGroup {

    private int column;
    private int dividerWidth;
    private int rowHeight;
    private int childHeight;
    private int childWidth;
    private float childHeightWidthRatio;


    public TextSimpleGridLayout(Context context) {
        this(context, null);
    }

    public TextSimpleGridLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TextSimpleGridLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //自定义View视图属性类（TypedArray）,可以添加到自定义视图组建属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TextSimpleGridLayout, defStyleAttr, 0);
        column = typedArray.getInteger(R.styleable.TextSimpleGridLayout_grid_column, 3);
        dividerWidth = (int) typedArray.getDimension(R.styleable.TextSimpleGridLayout_grid_divider_width, 0);
        childHeightWidthRatio = typedArray.getFloat(R.styleable.TextSimpleGridLayout_grid_item_height_width_ratio, 0);
        rowHeight = (int) typedArray.getDimension(R.styleable.TextSimpleGridLayout_grid_row_height, 1);
        typedArray.recycle();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TextSimpleGridLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //获取父类容器传过来的宽
        int width = MeasureSpec.getSize(widthMeasureSpec);
        childWidth = getChildSize(width);

        //通过宽高比和子视图宽度获得子视图高度
        if (childHeightWidthRatio == 0) {
            childHeight = rowHeight;
        } else {
            childHeight = (int) (childWidth * childHeightWidthRatio);
        }
        //通过子视图的宽高计算出该容器的具体宽高
        width = getPaddingLeft() + getPaddingRight() + column * (childWidth + dividerWidth) - dividerWidth;
        int height = getPaddingTop() + getPaddingBottom() + ((getChildCount() - 1) / column + 1) * (childHeight + dividerWidth) - dividerWidth;

        //获得子视图的宽高和计量模式
        int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY);
        int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.EXACTLY);
        //循环遍历所以子视图，并设置其宽高
        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).measure(childWidthMeasureSpec, childHeightMeasureSpec);
        }
        //设置容器的宽高
        setMeasuredDimension(width, height);
    }

    /**
     * 根据父类容器的宽计算子视图建议的宽度
     * @param width
     * @return
     */
    private int getChildSize(int width) {
        int childSpan = ((width - (column - 1) * dividerWidth) - getPaddingLeft() - getPaddingRight()) / column;
        return childSpan;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //循环遍历出所以的子视图，并完成其布局（layout）
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            int left = (getPaddingLeft() + i % column * childWidth + i % column * dividerWidth);
            int top = (getPaddingTop() + i / column * childHeight + i / column * dividerWidth);
            child.layout(left, top, (left + childWidth), (top + childHeight));
        }
    }
}
