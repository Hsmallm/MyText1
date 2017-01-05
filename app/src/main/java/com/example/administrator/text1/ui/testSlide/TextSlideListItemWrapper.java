package com.example.administrator.text1.ui.testSlide;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.Scroller;

/**
 * 功能描述：实现侧滑删除功能
 * 触屏事件的触发流程：1、onInterceptTouchEvent()里面进行水平滑动的拦截，返回true，传递给其控件的onTouchEvent处理
 * 2、调用dector.onTouchEvent(event)方法，让手势识别类进行事件的处理
 * 3、当dector.onTouchEvent(event)返回为false时，表示该事件还没有被消耗掉，则继续交给onTouchEvent()处理
 * Created by HM on 2016/5/20.
 */
public class TextSlideListItemWrapper extends LinearLayout{
    final public int DURATION = 250;//持续时长
    GestureDetector dector;//手势识别类
    private int scrollX;//水平滑动距离
    private Scroller scroller;//Scroller类是为了实现View平滑滚动的一个Helper类。
    private boolean isInteruptTriggered;//是否阻断触发
    private int minFingerDetectorInterval;//最小手指间距
    private ViewSlideListener listener;//视图的滑动监听类
    private float firstFingerY;//开始触屏时Y轴的位置
    private float firstFingerX;//开始触屏时X轴的位置
    private boolean blockSlide;//是否限制滑动
    GestureDetector.OnGestureListener gestureListener = new GestureDetector.SimpleOnGestureListener() {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if (!isInteruptTriggered) {//onInteruptTouchEvent未拦截，但onTouchEvent直接被调用
                requestDisallowInterceptTouchEvent(true);
                isInteruptTriggered = true;
                if (null != listener) {
                    listener.onViewSliding(TextSlideListItemWrapper.this);
                }
            }
            scroller.forceFinished(true);//强制结束此次滑屏操作
            scrollX += distanceX;
            checkBorder();
            scrollTo(scrollX, 0);//滑动scrollX长度的视图
            postInvalidate();//使用postInvalidate()刷新界面
            return true;
        }

        //onFling()方法里面我们设置视图的滑动(即为，视图向左或是向右的滑动)
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (scrollX != 0 || scrollX != getChildAt(1).getMeasuredWidth()) {
                int interval = velocityX > 0 ? -scrollX : getChildAt(1).getMeasuredWidth() - scrollX;
                scroller.startScroll(scrollX, 0, interval, 0, DURATION);
                postInvalidate();//使用postInvalidate()刷新界面
            }
            return true;
        }

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }
    };

    @SuppressLint("NewApi")
    public TextSlideListItemWrapper(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        dector = new GestureDetector(context, gestureListener);
        scroller = new Scroller(context);
        minFingerDetectorInterval = context.getResources().getDisplayMetrics().widthPixels / 24;
    }

    public TextSlideListItemWrapper(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TextSlideListItemWrapper(Context context) {
        this(context, null, 0);
    }

    public void setSlideListener(ViewSlideListener listener) {
        this.listener = listener;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (blockSlide) return false;
        //如果dector里面没能消耗掉该事件，则该事件将在这里面进行操作
        if (!dector.onTouchEvent(event)) {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (scrollX != 0 || scrollX != getChildAt(1).getMeasuredWidth()) {
                    int interval = scrollX < getChildAt(1).getMeasuredWidth() / 2 ? -scrollX : getChildAt(1).getMeasuredWidth() - scrollX;
                    scroller.startScroll(scrollX, 0, interval, 0, DURATION);
                    postInvalidate();
                }
                //(注：当你的手指（或者其它）移动屏幕的时候会触发这个事件，
                // 比如当你的手指在屏幕上拖动一个listView或者一个ScrollView而不是去按上面的按钮时会触发这个事件。)
            } else if (event.getAction() == MotionEvent.ACTION_CANCEL) {
                if (scrollX != 0) {
                    scroller.startScroll(scrollX, 0, -scrollX, 0, DURATION);
                    postInvalidate();
                }
            }

        }
        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (isInEditMode()) {
            getChildAt(0).layout(0, 0, getMeasuredWidth(), getMeasuredHeight());
            getChildAt(1).setAlpha(0.5f);//XML编辑模式下方便看到遮挡的View
            getChildAt(1).layout(getMeasuredWidth() - getChildAt(1).getMeasuredWidth(), 0, getMeasuredWidth(), getMeasuredHeight());
        } else {
            getChildAt(0).layout(0, 0, getMeasuredWidth(), getMeasuredHeight());
            getChildAt(1).layout(getMeasuredWidth(), 0, getChildAt(1).getMeasuredWidth() + getMeasuredWidth(), getMeasuredHeight());
        }

    }

    /**
     * 检查边界
     */
    private void checkBorder() {
        if (scrollX >= getChildAt(1).getMeasuredWidth()) {
            scroller.forceFinished(true);
            scrollX = getChildAt(1).getMeasuredWidth();
        } else if (scrollX <= 0) {
            scroller.forceFinished(true);
            scrollX = 0;
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (blockSlide) {
            return false;
        }
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            firstFingerY = ev.getY();
            firstFingerX = ev.getX();
            dector.onTouchEvent(ev);//让Detector记录Down事件，否则onScroll的最后一次事件是上次的Up事件的位�?
            scroller.forceFinished(true);//强制结束此次滑屏操作
            isInteruptTriggered = false;
        }
        //判断滑动方向为水平方向则拦截事件
        if (ev.getAction() == MotionEvent.ACTION_MOVE
                && Math.abs(ev.getY() - firstFingerY) + Math.abs(ev.getX() - firstFingerX) > minFingerDetectorInterval
                && Math.abs(ev.getY() - firstFingerY) < Math.abs(ev.getX() - firstFingerX)) {
            if (null != listener) {
                listener.onViewSliding(this);
            }
            isInteruptTriggered = true;
            requestDisallowInterceptTouchEvent(true);//阻止父层的View截获touch事件
            return true;//(注：当onInterceptTouchEvent()方法返回为true时，touch事件的传递将会传给自身控件的onTouch()方法，而不会继续向下分发给子View)
        }
        return false;
    }

    /**
     * 计算滑动
     */
    public void computeScroll() {
        if (scroller.computeScrollOffset()) {
            scrollX = scroller.getCurrX();
            checkBorder();
            scrollTo(scrollX, 0);
            postInvalidate();
        }
    }

    public void closeMenu() {
        if (scrollX != 0) {
            scroller.startScroll(scrollX, 0, -scrollX, 0, DURATION);
            postInvalidate();
        }
    }

    public void openMenu() {
        if (scrollX != getMeasuredWidth()) {
            scroller.startScroll(scrollX, 0, getMeasuredWidth() - scrollX, DURATION);
            postInvalidate();
        }
    }

    public void blockSlide(boolean block) {
        this.blockSlide = block;
    }

    public interface ViewSlideListener {
        void onViewSliding(TextSlideListItemWrapper v);
    }
}
