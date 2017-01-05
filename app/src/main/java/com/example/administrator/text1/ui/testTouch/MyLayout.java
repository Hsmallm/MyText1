package com.example.administrator.text1.ui.testTouch;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.FrameLayout;

/**
 * 功能描述：测试Touch触屏事件的分发机制,onInterceptTouchEvent、onTouchEvent
 * Created by HM on 2016/5/17.
 */
public class MyLayout extends FrameLayout{

    public MyLayout(Context context) {
        super(context);
    }

    public MyLayout(Context context, AttributeSet attr){
        super(context,attr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.e("TextTouchActivity", "MyLayout onInterceptTouchEvent.");
        Log.e("TextTouchActivity","MyLayout onInterceptTouchEvent default return "
                + super.onInterceptTouchEvent(ev));
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("TextTouchActivity", "MyLayout onTouchEvent.");
        Log.e("TextTouchActivity","MyLayout onTouchEvent default return "
                + super.onTouchEvent(event));
        return super.onTouchEvent(event);
    }
}
