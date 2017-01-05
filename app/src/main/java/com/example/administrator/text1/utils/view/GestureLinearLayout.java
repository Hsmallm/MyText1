package com.example.administrator.text1.utils.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;

import com.sina.weibo.sdk.utils.LogUtil;

/**
 * 功能描述：自定义LinearLayout线性布局控件，在dispatchTouchEvent里面完成上下滑动事件监听
 * dispatchTouchEvent：这个方法里面会捕捉到触屏事件
 * Created by HM on 2016/4/12.
 */
public class GestureLinearLayout extends LinearLayout {
    private float startX;
    private float startY;
    private long downTime;
    private SlideListener slideListener;

    public GestureLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setSlideListener(SlideListener listener) {
        slideListener = listener;
    }

    /**
     * 分派触屏事件
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //捕捉第一次按住事件发生后执行代码的区域
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            startX = ev.getX();
            startY = ev.getY();
            downTime = System.currentTimeMillis();
        //捕捉松开事件发生后执行代码的区域
        } else if (ev.getAction() == MotionEvent.ACTION_UP) {
            if (null != slideListener) {
                //如果1、横向移动距离的额2倍任小于纵向移动的距离；2、如果纵向移动的距离大于触发移动事件的最短距离，
                // (注：ViewConfiguration.get(getContext()).getScaledTouchSlop()表示：触发移动事件的最短距离)
                if (Math.abs(ev.getX() - startX) * 2 < Math.abs(ev.getY() - startY) && Math.abs(ev.getY() - startY) > ViewConfiguration.get(getContext()).getScaledTouchSlop()) {
                    LogUtil.e("XXXX", (System.currentTimeMillis() - downTime) + "");
                    //如果滑动事件的时间小于200
                    if (System.currentTimeMillis() - downTime < 200) {
                        //如果当前Y轴坐标大于开始时坐标
                        if (ev.getY() > startY) {
                            slideListener.onSlideDown();
                        } else {
                            slideListener.onSlideUp();
                        }
                    }
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    public interface SlideListener {
        void onSlideUp();

        void onSlideDown();
    }
}
