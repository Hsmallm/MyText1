package com.example.administrator.text1.ui.testOther;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.text1.R;
import com.seaway.android.common.toast.Toast;

/**
 * 功能描述：测试手势识别类，并测试识别各种手势,并进行相关的手势的处理
 * Created by HM on 2016/5/17.
 */
public class TextGestureDetectorActivity extends Activity implements View.OnTouchListener{

    private GestureDetector mGestureDetector;
    private TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_gesturedetector);

        //实例化一个手势识别类
        //给GestureDetector设置OnGestureListener监听
        mGestureDetector = new GestureDetector(new GestureListener());
        //给GestureDetector设置OnDoubleTapListener监听
        mGestureDetector.setOnDoubleTapListener(new DoubleTapListener());

        txt = (TextView) findViewById(R.id.txt);
        txt.setOnTouchListener(this);
        txt.setFocusable(true);
        txt.setClickable(true);
        txt.setLongClickable(true);
    }

    /**
     * 在onTouch方法中，我们调用mGestureDetector里面的onTouchEvent方法，将捕捉到的MotionEvent，交给GestureDetector处理
     * @param v
     * @param event
     * @return
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Toast.showToast(this,""+mGestureDetector.onTouchEvent(event));
        return mGestureDetector.onTouchEvent(event);
    }

    /**
     * OnGestureListener监听
     */
    private class GestureListener implements GestureDetector.OnGestureListener{

        /**
         * 用户轻触屏幕，由一个MotionEvent ACTION_DOWN触发
         * @param e
         * @return
         */
        @Override
        public boolean onDown(MotionEvent e) {
            Log.e("MyGesture","onDown");
            Toast.showToast(TextGestureDetectorActivity.this,"onDown");
            return false;
        }

        /**
         用户轻触触摸屏，尚未松开或拖动，由一个1个MotionEvent ACTION_DOWN触发
         * 注意和onDown()的区别，强调的是没有松开或者拖动的状态
         * 而onDown也是由一个MotionEventACTION_DOWN触发的，但是他没有任何限制，
         * 也就是说当用户点击的时候，首先MotionEventACTION_DOWN，onDown就会执行，
         * 如果在按下的瞬间没有松开或者是拖动的时候onShowPress就会执行，如果是按下的时间超过瞬间
         * （这块我也不太清楚瞬间的时间差是多少，一般情况下都会执行onShowPress），拖动了，就不执行onShowPress。
         * @param e
         */
        @Override
        public void onShowPress(MotionEvent e) {
            Log.e("MyGesture","onShowPress");
            Toast.showToast(TextGestureDetectorActivity.this,"onShowPress");
        }

        /**
         * 用户（轻触触摸屏后）松开，由一个1个MotionEvent ACTION_UP触发
         ///轻击一下屏幕，立刻抬起来，才会有这个触发
         //从名子也可以看出,一次单独的轻击抬起操作,当然,如果除了Down以外还有其它操作,那就不再算是Single操作了,所以这个事件 就不再响应
         * @param e
         * @return
         */
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            Log.e("MyGesture","onSingleTapUp");
            Toast.showToast(TextGestureDetectorActivity.this,"onSingleTapUp");
            return true;
        }

        /**
         * 用户按下触摸屏，并拖动，由1个MotionEvent ACTION_DOWN, 多个ACTION_MOVE触发
         * @param e1
         * @param e2
         * @param distanceX
         * @param distanceY
         * @return
         */
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            Log.e("MyGesture","onScroll");
            Toast.showToast(TextGestureDetectorActivity.this,"onScroll");
            return true;
        }

        /**
         * 用户长按触摸屏，由多个MotionEvent ACTION_DOWN触发
         * @param e
         */
        @Override
        public void onLongPress(MotionEvent e) {
            Log.e("MyGesture","onLongPress");
            Toast.showToast(TextGestureDetectorActivity.this,"onLongPress");
        }

        /**
         * 用户按下触摸屏、快速移动后松开，由1个MotionEvent ACTION_DOWN, 多个ACTION_MOVE, 1个ACTION_UP触发
         * @param e1
         * @param e2
         * @param velocityX
         * @param velocityY
         * @return
         */
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            Log.e("MyGesture","onFling");
            Toast.showToast(TextGestureDetectorActivity.this,"onFail");
            return true;
        }
    }

    /**
     * OnDoubleTapListener监听
     */
    private class DoubleTapListener implements GestureDetector.OnDoubleTapListener{

        /**
         * onSingleTapConfirmed(MotionEvent e)：单击事件。用来判定该次点击是SingleTap而不是DoubleTap，如果连续点击两次就是DoubleTap手势，
         * 如果只点击一次，系统等待一段时间后没有收到第二次点击则判定该次点击为SingleTap而不是DoubleTap，然后触发SingleTapConfirmed事件。
         * 触发顺序是：OnDown->OnsingleTapUp->OnsingleTapConfirmed
         * 关于onSingleTapConfirmed和onSingleTapUp的一点区别： OnGestureListener有这样的一个方法onSingleTapUp，
          和onSingleTapConfirmed容易混淆。二者的区别是：onSingleTapUp，只要手抬起就会执行，而对于onSingleTapConfirmed来说，如果双击的话，则onSingleTapConfirmed不会执行。
         * @param e
         * @return
         */
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            Log.e("MyGesture","onSingleTapConfirmed");
            Toast.showToast(TextGestureDetectorActivity.this,"onSingleTapConfirmed");
            return false;
        }

        /**
         * onDoubleTap(MotionEvent e)：双击事件
         * @param e
         * @return
         */
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            Log.e("MyGesture","onDoubleTap");
            Toast.showToast(TextGestureDetectorActivity.this,"onDoubleTap");
            return false;
        }

        /**
         * onDoubleTapEvent(MotionEvent e)：双击间隔中发生的动作。指触发onDoubleTap以后，在双击之间发生的其它动作，包含down、up和move事件；
         * @param e
         * @return
         */
        @Override
        public boolean onDoubleTapEvent(MotionEvent e) {
            Log.e("MyGesture","onDoubleTapEvent");
            Toast.showToast(TextGestureDetectorActivity.this,"onDoubleTapEvent");
            return false;
        }
    }
}
