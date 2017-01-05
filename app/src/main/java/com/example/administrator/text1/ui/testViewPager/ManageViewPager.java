package com.example.administrator.text1.ui.testViewPager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by hzhm on 2016/12/1.
 */

public class ManageViewPager extends ViewPager {

    private boolean noScroll = false;

    public ManageViewPager(Context context) {
        super(context);
    }

    public ManageViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setNoScroll(boolean mNoScroll){
        this.noScroll = mNoScroll;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if(noScroll){
            return false;
        }else {
            return super.onInterceptTouchEvent(ev);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if(noScroll){
            return false;
        }else {
            return super.onTouchEvent(ev);
        }
    }
}
