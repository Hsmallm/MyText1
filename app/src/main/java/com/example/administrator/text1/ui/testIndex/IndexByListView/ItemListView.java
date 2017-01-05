package com.example.administrator.text1.ui.testIndex.IndexByListView;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by hzhm on 2016/9/13.
 * 功能描述：自定义一个ListView，即为ListView嵌套ListView实现中的内嵌的ListView，解决不能将子Item全部显示的问题
 * 问题分析：在ListView嵌套ListView实现过程中，内嵌的ListView往往会无法正常的显示其高度，原因是在控件绘制出来之前要对ListView的大小进行计算，而之前我们是没有
 *           对内嵌的ListView的高度进行绘制的
 */
public class ItemListView extends ListView {
    public ItemListView(Context context) {
        super(context);
    }

    public ItemListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ItemListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        int aa = Integer.MAX_VALUE >> 2;
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
