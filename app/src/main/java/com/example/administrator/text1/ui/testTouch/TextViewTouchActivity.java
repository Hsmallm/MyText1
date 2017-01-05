package com.example.administrator.text1.ui.testTouch;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.example.administrator.text1.R;

/**
 * 功能描述：测试View中事件的分发机制
 * Created by HM on 2016/5/18.
 */
public class TextViewTouchActivity extends Activity{

    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_viewtouch);

        btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("TAG","onClick");
            }
        });

        btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.e("TAG","onTouch action:" + event.getAction());
                return false;
            }
        });
        ///描述tag打印可知：
        /**
         * 1.当onTouch方法中返回值为“false”，事件是先打印onTouch(其中onTouch打印了两边，一次是ACTION_DOWN，一次是ACTION_UP，按下和抬起这两次操作)，再打印onClick
         * 2.当onTouch方法中返回值为“true”，事件是只要打印onTouch（...），而没有打印onClick
         */

        ///根据tag打印分析：
        //(注：只要你触摸到了任何一个控件，就一定会调用该控件的dispatchTouchEvent方法,若该控件没有这个方法，则继续向上找他的父类)
        /**
         * View中dispatchTouchEvent方法的源码：
         * public boolean dispatchTouchEvent(MotionEvent event) {
            if (mOnTouchListener != null && (mViewFlags & ENABLED_MASK) == ENABLED &&
             mOnTouchListener.onTouch(this, event)) {
                return true;
           }
                return onTouchEvent(event);
           }
         * 其实dispatchTouchEvent方法很简单，只要满足了if里面的三个条件即可
         * 条件一：mOnTouchListener != null,mOnTouchListener正是在setOnTouchListener方法里赋值的，也就是说只要我们给控件注册了touch事件，mOnTouchListener就一定被赋值了。
         * 条件二：(mViewFlags & ENABLED_MASK) == ENABLED是判断当前点击的控件是否是enable的，按钮默认都是enable的，因此这个条件恒定为true。
         * 条件三：mOnTouchListener.onTouch(this, event),其实也就是去回调控件注册touch事件时的onTouch方法，这个条件了也是最为关键的，
         * 它直接决定了事件的一个处理走向
         *
         * 由此就印证了上述tag打印，即：
         * 1.当回调控件注册touch事件时的onTouch方法时，如果返回为true,那么dispatchTouchEvent方法返回也为true,也就不会在执行onTouchEvent(event)方法
         * 2.当回调控件注册touch事件时的onTouch方法时，如果返回为false,那么dispatchTouchEvent方法返回也为false，就会执行下面的onTouchEvent(event)方法
         * 也就是说，onClick（）方法肯定在onTouchEvent()方法里面
         */

        ///View里面事件分发机制的总结：
        /**
         * 1.只要你触摸到了任何一个控件，就一定会调用该控件的dispatchTouchEvent方法,若该控件没有这个方法，则继续向上找他的父类里面的
         * 2.dispatchTouchEvent方法里面包含有onTouch（）方法、和onTouchEvent(event)方法（onClick（）方法肯定在onTouchEvent()方法里面）,
             这两个方法；onTouch（）方法优先于onTouchEvent(event)方法执行
         * 3.当回调控件注册touch事件的onTouch方法返回为true时，onTouchEvent(event)方法不执行；
             当回调控件注册touch事件的onTouch方法返回为false时，onTouchEvent(event)方法才执行
         * 4.dispatchTouchEvent方法返回true的三个条件：
         * 条件一：mOnTouchListener != null,mOnTouchListener正是在setOnTouchListener方法里赋值的，也就是说只要我们给控件注册了touch事件，mOnTouchListener就一定被赋值了。
         * 条件二：(mViewFlags & ENABLED_MASK) == ENABLED是判断当前点击的控件是否是enable的，按钮默认都是enable的，因此这个条件恒定为true。
         * 条件三：mOnTouchListener.onTouch(this, event),其实也就是去回调控件注册touch事件时的onTouch方法，这个条件了也是最为关键的，
         *
         */
    }
}
