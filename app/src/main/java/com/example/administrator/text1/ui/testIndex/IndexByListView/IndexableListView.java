package com.example.administrator.text1.ui.testIndex.IndexByListView;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * Created by hzhm on 2016/6/21.
 * 功能描述：自定义一个ListView实现自带右边索引条（即为：右边的索引条是关联在自定义的LIstView上）
 */
public class IndexableListView extends ListView {

    private boolean mIsFastScrollEnable = false;
    private IndexScroller scroller = null;//主要负责绘制后面的索引条
    private GestureDetector gestureDetector = null;

    public IndexableListView(Context context) {
        super(context);
    }

    public IndexableListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public IndexableListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //初始化IndexScroller对象
    @Override
    public void setFastScrollEnabled(boolean enable) {
        mIsFastScrollEnable = enable;
        if (mIsFastScrollEnable) {
            if (scroller == null) {
                scroller = new IndexScroller(getContext(), this);
            }
        } else {
            if (scroller != null) {
                scroller.hide();
                scroller = null;
            }
        }
    }

    /**
     * 用于绘制右边的索引,绘制到当前对象上(注：draw方法是进行实时调用绘制的,因为在IndexScroll里面会不断调用mListView.invalidate();)
     */
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (scroller != null) {
            //绘制右边的索引
            scroller.draw(canvas);
            scroller.show();
        }
    }

    /**
     * 在一级ListView列表控件onInterceptTouchEvent拦截事件处理机制中，
     * 通过判断触屏位置是否在索引条目范围内部，来对事件进行是否拦截处理
     * return true: 表示触摸事件已经被拦截处理，不会分发到其子控件
     * return false: 表示触摸事件未被被拦截处理，会分发到其子控件
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (scroller != null) {
                    if (scroller.contains(ev.getX(), ev.getY())) {
                        return true;
                    } else {
                        return super.onInterceptTouchEvent(ev);
                    }
                }
        }
        return super.onInterceptTouchEvent(ev);
    }

    /**
     * 给ListView设置onTouch监听（即为：触摸ListView的事件处理）
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        //如果有scroller自己来处理触摸事件时，该方法返回为true:表示你已经处理了此事件 他的父组件将不会继续处理
        //(注：只有调用scroller.onTouchEvent(ev),才会执行里面的触屏事件，因为scroller是个普通类，不是视图；他的触屏事件，就是Listview传过去的ev)
        if (scroller != null && scroller.onTouchEvent(ev)) {
            return true;
        }

        if (gestureDetector == null) {
            //使用手势识别类来处理其触摸事件
            gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                    //显示右边的索引条
                    scroller.show();
                    return super.onFling(e1, e2, velocityX, velocityY);
                }
            });
        }
        gestureDetector.onTouchEvent(ev);
        return super.onTouchEvent(ev);
    }

    //给IndexScroller（自定义索引条）设置完成adaptr适配
    @Override
    public void setAdapter(ListAdapter adapter) {
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
