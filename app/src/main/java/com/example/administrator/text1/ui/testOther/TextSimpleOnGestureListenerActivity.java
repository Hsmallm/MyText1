package com.example.administrator.text1.ui.testOther;

import android.app.Activity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.text1.R;
import com.seaway.android.common.toast.Toast;

/**
 * 功能描述：通过创建一个继承SimpleOnGestureListener这个类，从而来实例化GestureDetector对象
 * 通过OnFling, OnFling应用——识别向左滑还是向右滑
 * Created by HM on 2016/5/17.
 */
public class TextSimpleOnGestureListenerActivity extends Activity implements View.OnTouchListener {

    private GestureDetector mGestureDetector;
    private TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_gesturedetector);

        mGestureDetector = new GestureDetector(new SimpleOnGestureListene());
        txt = (TextView) findViewById(R.id.txt);
        txt.setOnTouchListener(this);
        txt.setFocusable(true);
        txt.setClickable(true);
        txt.setLongClickable(true);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }

    private class SimpleOnGestureListene extends GestureDetector.SimpleOnGestureListener{

        final int FLING_MIN_DISTANCE = 100;
        final int FLING_MIN_VELOCETY = 200;

        // 触发条件 ：
        // X轴的坐标位移大于FLING_MIN_DISTANCE，且移动速度大于FLING_MIN_VELOCITY个像素/秒

        // 参数解释：
        // e1：第1个ACTION_DOWN MotionEvent
        // e2：最后一个ACTION_MOVE MotionEvent
        // velocityX：X轴上的移动速度，像素/秒
        // velocityY：Y轴上的移动速度，像素/秒
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if(e1.getX() - e2.getX() > FLING_MIN_DISTANCE && Math.abs(velocityX) > FLING_MIN_VELOCETY){
                Toast.showToast(TextSimpleOnGestureListenerActivity.this,"Fling:left");
            }else if(e2.getX() - e1.getX() > FLING_MIN_DISTANCE && Math.abs(velocityX) > FLING_MIN_VELOCETY){
                Toast.showToast(TextSimpleOnGestureListenerActivity.this,"Fling:right");
            }
            return super.onFling(e1, e2, velocityX, velocityY);

        }
    }
}
