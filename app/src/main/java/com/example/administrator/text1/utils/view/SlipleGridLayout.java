package com.example.administrator.text1.utils.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.text1.R;

/**
 * Created by hzhm on 2016/12/6.
 * 功能描述：自定义多行多列的GridLayout组件
 * 适用范围：仅限于布局一些固定的多行多列的网格布局
 */

public class SlipleGridLayout extends ViewGroup {

    private int width;//相关空间的宽度
    private int row;//控件的行数
    private int column;//控件的列数
    private int rowHeinht;//每列控件的行高

    public SlipleGridLayout(Context context) {
        super(context);
    }

    /**
     * 控件布局于XMl之后，进入此控件的入口方法
     * @param context
     * @param attrs
     */
    public SlipleGridLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlipleGridLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SlipleGrid);
        row = typedArray.getInteger(R.styleable.SlipleGrid_grid_row2, 1);
        column = typedArray.getInteger(R.styleable.SlipleGrid_grid_column2, 3);
        rowHeinht = (int) typedArray.getDimension(R.styleable.SlipleGrid_grid_row_height2, 80);
        typedArray.recycle();
    }

    /**
     * 计算控件宽、高
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        height = row * rowHeinht;

        widthMeasureSpec = MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * 布局相关子控件的显示位置
     * @param changed
     * @param l
     * @param t
     * @param r
     * @param b
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            int left = 0;
            int right = 0;
            int top = 0;
            int indexOfX = i % column;//影响X轴的变量基数
            int indexOfY = i / column;//影响Y轴的变量基数
            left = getPaddingLeft() + width / column * indexOfX;
            right = getPaddingLeft() + width / column * (indexOfX + 1);
            top = getTopPaddingOffset() + indexOfY * rowHeinht;
            view.layout(left, top, right, top + rowHeinht);
            Log.e("余数" + i, i % column + "");
            Log.e("除数" + i, i / column + "");
        }
    }
}
