package com.example.administrator.text1.utils.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by User on 16/7/15.
 */
public class AccountViewPager extends ViewPager {
    private boolean handleScroll;

    public AccountViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (handleScroll) return super.onInterceptTouchEvent(ev);
        else return false;
    }

    public void setScrollable(boolean scrollable) {
        handleScroll = scrollable;
    }
}
