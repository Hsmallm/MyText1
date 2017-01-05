package com.example.administrator.text1.ui.testTouch;

import android.app.Activity;
import android.os.Bundle;

import com.example.administrator.text1.R;

/**
 * 功能描述：测试Touch触屏事件的分发机制,onInterceptTouchEvent、onTouchEvent
 * Created by HM on 2016/5/17.
 */
public class TextTouchActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_touch);

        //总结：
        // （可以得出结论:onInterceptTouchEvent默认返回值是false,MyLayout里onTouchEvent默认返回值是false，
        // 所以只消费了ACTION_DOWN事件，MyView里onTouch默认返回值是true,调用了俩次:ACTION_DOW,ACTION_UP。）

        // 1、ViewGroup里的onInterceptTouchEvent默认值是false这样才能把事件传给子视图View里的onTouchEvent.
        // 2、ViewGroup里的onTouchEvent默认值是false。
        // 3、View里的onTouchEvent返回默认值是true.这样才能执行多次touch事件。


    }
}
