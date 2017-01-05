package com.example.administrator.text1.ui.testSlide.textLrSlidePage;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * 功能描述：自定义ViewGroup(即自定义视图控件)，实现多视图切换功能
 * 相关技术要点：1.onMeasure里面完成自身宽高的计算，以及给出所有子视图childview的建议宽高和计算模式
 * 2.onLayout里面完成对所有子视图childview的布局
 * 3.理解掌握ViewGroup里面scrollTo、scrollBy、getScrollX、getScrollY这4个方法的含义
 * Created by HM on 2016/4/13.
 */
public class TextViewGroup extends ViewGroup {
    final public int DURATION = 1000;//持续时长
    //手势识别类
    private GestureDetector detector;
    //实现View平滑滚动的一个Helper类。
    private Scroller scroller;
    private int minFingerDetectorInterval;//最小手指间距
    float startX = 0;
    float startY = 0;
    private int scrollX;
    GestureDetector.OnGestureListener gestureListener = new GestureDetector.SimpleOnGestureListener() {
//        @Override
//        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
//            scrollX += distanceX;
//            scrollTo(scrollX, 0);
//            return true;
//        }
//
//        @Override
//        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
////                scrollTo(-768, 0);
//                scroller.startScroll(scrollX, 0, 768, 0, DURATION);
//                postInvalidate();
////            else {
//////                scrollTo(768, 0);
////                scroller.startScroll(scrollX,0,scrollX,0,DURATION);
////                postInvalidate();
////            }
//            return true;
//        }
    };

    public TextViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        detector = new GestureDetector(gestureListener);
        scroller = new Scroller(context);
        minFingerDetectorInterval = context.getResources().getDisplayMetrics().widthPixels / 24;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        getChildAt(0).layout(-768, 0, 0, getChildAt(0).getMeasuredHeight());
        getChildAt(1).layout(0, 0, 768, getChildAt(1).getMeasuredHeight());
        getChildAt(2).layout(768, 0, 1536, getChildAt(2).getMeasuredHeight());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = 1000;
        setMeasuredDimension(width, height);
        getChildAt(0).measure(width, MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY));
        getChildAt(1).measure(width, MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY));
        getChildAt(2).measure(width, MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY));
    }


    //这个触屏事件的触发流程：1.开始在该控件的onInterceptTouchEvent()里面实现了ACTION_DOWN,返回false >>>
    // 2.则该事件就继续分发给子View里面的onInterceptTouchEvent(),直到事件被拦截或是没有了这个事件的View，事件就会返回到父控件OnTouch(),即为onTouchEvent（）
    // 3.onTouchEvent（）里面了ACTION_UP返回为true，所以该事件就被消耗掉了，没能继续向上传递
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            startX = ev.getX();
            startY = ev.getY();
            Log.e("Down", "111111");
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!detector.onTouchEvent(event)) {
            if (event.getAction() == MotionEvent.ACTION_MOVE && Math.abs(event.getX() - startX) > Math.abs(event.getY() - startY)
                    && Math.abs(event.getX() - startX) + Math.abs(event.getY() - startY) > minFingerDetectorInterval) {
                Log.e("MOVE", "222222");
            }
            if (event.getAction() == MotionEvent.ACTION_UP) {
                //向右滑动
                if (event.getX() > startX) {
                    //如果已经位于最左端，则就不能滑动
                    if (scroller.getCurrX() < 0) {
                        return true;
                    } else if(scroller.getCurrX() == 0){
                        Log.e("aa",scroller.getCurrX()+"");
                        scroller.startScroll(scroller.getCurrX(), 0, -768, 0, DURATION);
                    }else if(scroller.getCurrX() > 0){
                        Log.e("bb",scroller.getCurrX()+"");
                        scroller.startScroll(scroller.getCurrX(), 0, -768, 0, DURATION);
                    }
                //向左滑动
                } else {
                    //如果已经位于最右端，则就不能滑动
                    if (scroller.getCurrX() > 0) {
                        return true;
                    } else if(scroller.getCurrX() < 0){
                        //设置mScroller滚动的位置时，并不会导致View的滚动，通常是用mScroller记录/计算View滚动的位置，再重写View的computeScroll()，完成实际的滚动。
                        scroller.startScroll(scroller.getCurrX(), 0, 768, 0, DURATION);
                        Log.e("cc",scroller.getCurrX()+"");
                    }else if(scroller.getCurrX() == 0){
                        Log.e("dd",scroller.getCurrX()+"");
                        scroller.startScroll(scroller.getCurrX(), 0, 768, 0, DURATION);
                    }
                }
                invalidate();
                Log.e("UP", "666666");
            }
        }
        return true;
    }

    //这个方法computeScroll()：当我们执行ontouch或invalidate(）或postInvalidate()都会导致这个方法的执行
    @Override
    public void computeScroll() {
        super.computeScroll();
        if (scroller.computeScrollOffset()) {
            scrollTo(scroller.getCurrX(), 0);
            postInvalidate();
        }
    }
}
