package com.example.administrator.text1.ui.testTouch;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.administrator.text1.R;

/**
 * 功能描述：测试ViewGroup中的事件的分发机制
 * Created by HM on 2016/5/18.
 */
public class TextViewGroupTouchActivity extends Activity {

    private LinearLayout myLayout2;
    private Button btn1;
    private Button btn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_viewgroup_touch);

        myLayout2 = (LinearLayout) findViewById(R.id.mylayout2);
        btn1 = (Button) findViewById(R.id.button1);
        btn2 = (Button) findViewById(R.id.button2);

        myLayout2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.e("Tag","myLayout: onTouch");
                return false;
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Tag","Button1: onClick");
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Tag","Button2: onClick");
            }
        });

        ///描述Tag打印：
        //(注：只要你触摸了任何控件，就一定会调用该控件的dispatchTouchEvent方法。这个说法没错，只不过还不完整而已。
        // 实际情况是，当你点击了某个控件，首先会去调用该控件所在布局的dispatchTouchEvent方法，然后在布局的dispatchTouchEvent方法中找到被点击的相应控件，
        // 再去调用该控件的dispatchTouchEvent方法；如果该控件所在布局没有找到这个方法，则继续向上找他父类的dispatchTouchEvent方法)
        // 该demo中，button所在的布局为MyLayout2，但是它里面并没有dispatchTouchEvent方法，则继续向上找LinearLayout、ViewGroup，最终在ViewGroup中找到
        /**
         * ViewGroup中dispatchTouchEvent方法
         * if (disallowIntercept || !onInterceptTouchEvent(ev)) {
                ev.setAction(MotionEvent.ACTION_DOWN);
                final int scrolledXInt = (int) scrolledXFloat;
                final int scrolledYInt = (int) scrolledYFloat;
                final View[] children = mChildren;
                final int count = mChildrenCount;
                for (int i = count - 1; i >= 0; i--) {
                    final View child = children[i];
                    if ((child.mViewFlags & VISIBILITY_MASK) == VISIBLE
                        || child.getAnimation() != null) {
                         child.getHitRect(frame);
                        if (frame.contains(scrolledXInt, scrolledYInt)) {
                             final float xc = scrolledXFloat - child.mLeft;
                             final float yc = scrolledYFloat - child.mTop;
                             ev.setLocation(xc, yc);
                             child.mPrivateFlags &= ~CANCEL_NEXT_UP_EVENT;
                                if (child.dispatchTouchEvent(ev))  {
                                    mMotionTarget = child;
                                    return true;
                                    }
                                }
                             }
                         }
                     }
                  }
         */

        ///根据Tag分析可知：
        /**
         * 首先在第13行可以看到一个条件判断，如果disallowIntercept和!onInterceptTouchEvent(ev)两者有一个为true，
         * 就会进入到这个条件判断中。disallowIntercept是指是否禁用掉事件拦截的功能，默认是false，
         * 也可以通过调用requestDisallowInterceptTouchEvent方法对这个值进行修改。
         * 那么当第一个值为false的时候就会完全依赖第二个值来决定是否可以进入到条件判断的内部，第二个值是什么呢？
         * 竟然就是对onInterceptTouchEvent方法的返回值取反！也就是说如果我们在onInterceptTouchEvent方法中返回false，就会让第二个值为true，
         * 从而进入到条件判断的内部，如果我们在onInterceptTouchEvent方法中返回true，就会让第二个值为false，从而跳出了这个条件判断。
         这个时候你就可以思考一下了，由于我们刚刚在MyLayout中重写了onInterceptTouchEvent方法，让这个方法返回true，
         导致所有按钮的点击事件都被屏蔽了，那我们就完全有理由相信，按钮点击事件的处理就是在第13行条件判断的内部进行的！
         那我们重点来看下条件判断的内部是怎么实现的。在第19行通过一个for循环，遍历了当前ViewGroup下的所有子View，
         然后在第24行判断当前遍历的View是不是正在点击的View，如果是的话就会进入到该条件判断的内部，
         然后在第29行调用了该View的dispatchTouchEvent，之后的流程就和 Android事件分发机制完全解析，
         带你从源码的角度彻底理解(上) 中讲解的是一样的了。我们也因此证实了，按钮点击事件的处理确实就是在这里进行的。
         */

        /// ViewGroup里面事件分发机制的总结：
        /**
         * 1. Android事件分发是先传递到ViewGroup，再由ViewGroup传递到View的。
         * 2. 在ViewGroup中可以通过onInterceptTouchEvent方法对事件传递进行拦截，onInterceptTouchEvent方法返回true代表不允许事件继续向子View传递，返回false代表不对事件进行拦截，默认返回false。
         * 3. 子View中如果将传递的事件消费掉，ViewGroup中将无法接收到任何事件。
         好了，Android事件分发机制完全解析到此全部结束，结合上下两篇，相信大家对事件分发的理解已经非常深刻了。
         */
    }
}
