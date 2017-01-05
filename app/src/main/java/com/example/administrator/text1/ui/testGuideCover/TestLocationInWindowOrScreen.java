package com.example.administrator.text1.ui.testGuideCover;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.administrator.text1.R;

/**
 * Created by hzhm on 2016/9/9.
 * 功能描述：比较getLocationInWindow与getLocationOnScreen获取坐标点的区别
 * <p/>
 * 关键技术点：
 * getLocationInWindow --- 获取当前窗体内的绝对坐标
 * getLocationOnScreen --- 获取整个屏幕内的绝对坐标（注意：这个值是要从屏幕顶端算起，也就是包括了通知栏的高度。）
 * View.getLeft() , View.getTop(), View.getBottom(), View.getRight()---下面一组是获取相对在它父窗口里的坐标。
 * View.getLocationInWindow()和 View.getLocationOnScreen()在window占据全部screen时，返回值相同，不同的典型情况是在Dialog中时。
 * 当Dialog出现在屏幕中间时，View.getLocationOnScreen()取得的值要比View.getLocationInWindow()取得的值要大。
 */
public class TestLocationInWindowOrScreen extends Activity {

    private TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_guidecover);

        txt = (TextView) findViewById(R.id.cover_txt);
        int left = txt.getLeft();
        int right = txt.getRight();
        int top = txt.getTop();
        int bottom = txt.getBottom();
        Log.e("txt:", "left---" + left + "right---" + right + "top---" + top + "bottom---" + bottom);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        int[] location = new int[2];

        txt.getLocationInWindow(location);
        int x1 = location[0];
        int y1 = location[1];

        txt.getLocationOnScreen(location);
        int x2 = location[0];
        int y2 = location[1];
        Log.e("InWindow", "x1---" + x1 + "---y1---" + y1);
        Log.e("OnScreen", "x2---" + x2 + "---y2---" + y2);
    }
}
