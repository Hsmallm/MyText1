package com.example.administrator.text1.ui.testIndex;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.widget.SectionIndexer;

/**
 * Created by hzhm on 2016/9/13.
 */
public class IndexRcyScroller {

    private float mIndexbarWidth;//索引条的宽度
    private float mIndexbarMargin;//索引条距离右边距
    private float mPreviewPadding;//在中心显示的文本到四周的距离
    private float mDensity;//当前屏幕密度除以160
    private float mScaledDensity;//当前屏幕密度除以160（设置文本的尺寸）
    private float mAlphaRate;//设置透明度
    private int mState = SATTE_HIDDEN;//索引条的当前状态
    private int mListViewWidth;
    private int mListViewHeight;
    private int mCurrentSection = -1;//当前选中的索引
    private boolean mIsIndexing = false;
    private RecyclerView mListView = null;//左边的数据列表
    private SectionIndexer mIndexer = null;//出入的接口
    private String[] mSection = null;//索引文本
    private RectF mIndexbarRect;//索引所在的绘制区域
    private ItemListViewInterceptTouchListener listener;
    private boolean isCanvas;

    //索引所处的几种状态
    private static final int SATTE_HIDDEN = 0;//隐藏
    private static final int SATTE_SHOWING = 1;//正在显示
    private static final int SATTE_SHOW = 2;//显示
    private static final int SATTE_HIDING = 3;//正在隐藏

    public interface ItemListViewInterceptTouchListener {
        public void IsInterceptTouch(boolean isTouch);
    }

    public void setItemListViewTouch(ItemListViewInterceptTouchListener mListener) {
        listener = mListener;
    }


    //索引条件的初始化和尺寸本地化
    public IndexRcyScroller(Context context, RecyclerView lv) {
        //获得屏幕密度比值
        mDensity = context.getResources().getDisplayMetrics().density;
        mScaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
        mListView = lv;
        setAdapter(mListView.getAdapter());
        //根据屏幕密度绘制索引条的宽度
        mIndexbarWidth = 20 * mDensity;
        mIndexbarMargin = 10 * mDensity;
        mPreviewPadding = 5 * mDensity;
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        mIndexer = (SectionIndexer) adapter;
        mSection = (String[]) mIndexer.getSections();
    }

    //绘制索引条和预览文本
    public void draw(Canvas canvas) {

        //1、绘制索引条，包括索引条背景和文本
        //如果当前索引条为隐藏，则不进行绘制
        if (mState == SATTE_HIDDEN) {
            return;
        }

        //设置索引背景的绘制属性
        canvasIndexBag(isCanvas, canvas);

        //绘制Sections
        if (mSection != null && mSection.length > 0) {
            //2、绘制预览文本和背景(当索引项大于等于0时，才进行预览文本的绘制)
            if (mCurrentSection >= 0) {
                //设置预览文本背景绘制属性
                Paint previewPaint = new Paint();
                previewPaint.setColor(Color.BLACK);
                previewPaint.setAlpha(96);

                //设置预览文本绘制属性
                Paint previewTextPaint = new Paint();
                previewTextPaint.setColor(Color.WHITE);
                previewTextPaint.setTextSize(50 * mScaledDensity);
                //预览文本宽度
                float previewTextWidth = previewTextPaint.measureText(mSection[mCurrentSection]);
                //预览文本背景高度
                float previewSize = 2 * mPreviewPadding + previewTextPaint.descent() - previewTextPaint.ascent();

                //预览文本的背景区域
                RectF previewRectF = new RectF((mListViewWidth - previewSize) / 2, (mListViewHeight - previewSize) / 2,
                        (mListViewWidth - previewSize) / 2 + previewSize, (mListViewHeight - previewSize) / 2 + previewSize);
                //绘制预览文本背景
                canvas.drawRoundRect(previewRectF, 5 * mDensity, 5 * mDensity, previewPaint);
                //绘制预览文本
                canvas.drawText(mSection[mCurrentSection], previewRectF.left + (previewSize - previewTextWidth) / 2,
                        previewRectF.top + mPreviewPadding - previewTextPaint.ascent(), previewTextPaint);
            }
        }

        //设置索引文本的绘制属性
        Paint indexPaint = new Paint();
        indexPaint.setColor(Color.parseColor("#525252"));
        indexPaint.setAlpha((int) (255 * mAlphaRate));
        indexPaint.setTextSize(14 * mScaledDensity);
        float sectionHeight = (mIndexbarRect.height() - 2 * mIndexbarMargin) / mSection.length;
        float paddingTop = (sectionHeight - (indexPaint.descent() - indexPaint.ascent())) / 2;
        for (int i = 0; i < mSection.length; i++) {
            float paddingLeft = (mIndexbarWidth - indexPaint.measureText(mSection[i])) / 2;
            //绘制索引条上的文本
            canvas.drawText(mSection[i], mIndexbarRect.left + paddingLeft,
                    mIndexbarRect.top + mIndexbarMargin + sectionHeight * i + paddingTop - indexPaint.ascent(), indexPaint);
        }
    }

    //管理触摸索引条的触摸事件的处理方法
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 当当前状态不为隐藏时，且点击区域在右边索引范围内时
                //(注：1、当前状态不为隐藏时，返回为false，listview里面的OnTouchEvent()进行处理，执行show(),改变mState当前状态为不隐藏；
                // 2、当前状态不为隐藏时，返回true，listview里面的OnTouchEvent()return掉了)
                if (mState != SATTE_HIDDEN && contains(ev.getX(), ev.getY())) {
                    isCanvas = true;
                    //则设置显示状态为一直显示（一直显示条件：当索引条可见，即透明度不为空）
                    setState(SATTE_SHOW);

                    // 是否在索引条
                    mIsIndexing = true;
                    // 获取当前索引项
                    mCurrentSection = getSectionByPoint(ev.getY());
                    //根据当前右边索引查询ListView关联项
                    mListView.scrollToPosition(mIndexer.getPositionForSection(mCurrentSection));
                    return true;
                }else {
                    return false;
                }
            case MotionEvent.ACTION_MOVE:
                if (mIsIndexing) {
                    // If this event moves inside index bar
                    if (contains(ev.getX(), ev.getY())) {
                        // Determine which section the point is in, and move the list to that section
                        mCurrentSection = getSectionByPoint(ev.getY());
                        //根据当前右边索引查询ListView关联项
                        mListView.scrollToPosition(mIndexer.getPositionForSection(mCurrentSection));
                    }
                    return true;
                }
                break;

            case MotionEvent.ACTION_UP:
                isCanvas = false;
                if (mIsIndexing) {
                    mIsIndexing = false;
                    mCurrentSection = -1;
                }
                if (mState == SATTE_SHOW)
                    setState(SATTE_HIDING);
                break;

        }

        return false;
    }

    //获取点击区域范围
    public boolean contains(float x, float y) {
        return (x > mIndexbarRect.left && y > mIndexbarRect.top && y <= mIndexbarRect.top + mIndexbarRect.height());
    }

    //获取当前选中索引项
    private int getSectionByPoint(float y) {
        if (mSection == null || mSection.length == 0)
            return 0;
        if (y < mIndexbarRect.top + mIndexbarMargin)
            return 0;
        if (y >= mIndexbarRect.top + mIndexbarRect.height() - mIndexbarMargin)
            return mSection.length - 1;
        return (int) ((y - mIndexbarRect.top - mIndexbarMargin) / ((mIndexbarRect.height() - 2 * mIndexbarMargin) / mSection.length));
    }

    //给IndexScroller（自定义索引条）设置完成屏幕大小改变自适应
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        mListViewWidth = w;
        mListViewHeight = h;
        mIndexbarRect = new RectF(w - mIndexbarMargin - mIndexbarWidth,
                mIndexbarMargin, w - mIndexbarMargin, h - mIndexbarMargin);
    }

    //显示索引条
    public void show() {
        if (mState == SATTE_HIDDEN)
            setState(SATTE_SHOWING);
        else if (mState == SATTE_HIDING)
            setState(SATTE_HIDING);
    }

    //隐藏索引条
    public void hide() {
        if (mState == SATTE_SHOW)
            setState(SATTE_HIDDEN);
    }

    private void canvasIndexBag(boolean isCanvas,Canvas canvas) {
        //设置索引背景的绘制属性
        Paint indexbarPaint = new Paint();
        if (isCanvas) {
            indexbarPaint.setColor(Color.BLACK);
        } else {
            indexbarPaint.setColor(Color.WHITE);
        }

        indexbarPaint.setAlpha((int) (64 * mAlphaRate));

        //绘制索引条背景（4个角都为圆角区域）
        canvas.drawRoundRect(mIndexbarRect, 5 * mDensity, 5 * mDensity, indexbarPaint);
    }

    //设置右边索引条相应的显示状态
    private void setState(int state) {
        if (state < SATTE_HIDDEN || state > SATTE_HIDING) {
            return;
        }
        mState = state;
        switch (mState) {
            case SATTE_HIDDEN:
                mHandler.removeMessages(0);
                break;

            case SATTE_SHOWING:
                mAlphaRate = 0;
                fade(0);
                break;

            case SATTE_SHOW:
                mHandler.removeMessages(0);
                break;

            case SATTE_HIDING:
                mAlphaRate = 1;
                fade(3000);
                break;
        }
    }

    private void fade(long i) {
        //移除之前所有的消息队列
        mHandler.removeMessages(0);
        //发送消息队列给相应的mHandler处理
        mHandler.sendEmptyMessageAtTime(0, SystemClock.uptimeMillis() + i);
    }

    //处理相应的消息队列
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (mState) {
                case SATTE_SHOWING:
                    mAlphaRate += (1 - mAlphaRate) * 0.2;
                    if (mAlphaRate > 0.9) {
                        mAlphaRate = 1;
                        setState(SATTE_SHOW);
                    }
                    mListView.invalidate();
                    fade(100);
                    break;

                case SATTE_SHOW:
                    setState(SATTE_HIDING);
                    break;

                case SATTE_HIDING:
                    mAlphaRate -= mAlphaRate * 0.2;
                    if (mAlphaRate < 0.1) {
                        mAlphaRate = 0;
                        setState(SATTE_HIDDEN);
                    }
                    mListView.invalidate();
                    fade(100);
                    break;

                //死循环
//                case SATTE_HIDDEN:
//                    setState(SATTE_HIDING);
//                    break;
            }
        }
    };
}
