package com.example.administrator.text1.ui.testIndex;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * Created by hzhm on 2016/9/13.
 */
public class IndexableRecycleView extends RecyclerView {

    private boolean mIsFastScrollEnable = false;
    private IndexRcyScroller scroller = null;//主要负责绘制后面的索引条
    private GestureDetector gestureDetector = null;

    public IndexableRecycleView(Context context) {
        super(context);
    }

    public IndexableRecycleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public IndexableRecycleView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    //初始化IndexScroller对象,设置是否绘制右侧索引
    public void setmIsFastScrollEnable(boolean mIsFastScrollEnable) {
        this.mIsFastScrollEnable = mIsFastScrollEnable;
        if (mIsFastScrollEnable) {
            if (scroller == null) {
                scroller = new IndexRcyScroller(getContext(), this);
            }
        } else {
            if (scroller != null) {
//                scroller.hide();
                scroller = null;
            }
        }
    }

    //用于绘制右边的索引,绘制到当前对象上(注：draw方法是进行实时调用绘制的)
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (scroller != null) {
            //绘制右边的索引
            scroller.draw(canvas);
            scroller.show();
        }
    }

    //给ListView设置onTouch监听（即为：触摸ListView的事件处理）
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        //如果有scroller自己来处理触摸事件时，该方法返回为true:表示你已经处理了此事件 他的父组件将不会继续处理
        //(注：只有调用scroller.onTouchEvent(ev),才会执行里面的触屏事件，因为scroller是个普通类，不是视图；他的触屏事件，就是Listview传过去的ev)
        if (scroller != null && scroller.onTouchEvent(ev)) {
            return true;
        }
        return super.onTouchEvent(ev);
    }

    //给IndexScroller（自定义索引条）设置完成adaptr适配
    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
        if (scroller != null) {
            scroller.setAdapter(adapter);
        }
    }

    //给IndexScroller（自定义索引条）设置完成屏幕大小改变自适应
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (scroller != null) {
            scroller.onSizeChanged(w, h, oldw, oldh);
        }
    }
}
