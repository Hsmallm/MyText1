package com.example.administrator.text1.ui.testTouch;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Button;

/**
 * 功能描述：测试Touch触屏事件的分发机制，onInterceptTouchEvent、onTouchEvent
 * Created by HM on 2016/5/17.
 */
public class MyView extends Button{

    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, AttributeSet attr){
        super(context,attr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("TextTouchActivity", "MyView onTouchEvent.");
        Log.e("TextTouchActivity","MyView onTouchEvent default return "
                + super.onTouchEvent(event) + event.getAction());
        return false;
    }
}
