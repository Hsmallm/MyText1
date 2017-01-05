package com.example.administrator.text1.ui.testQQSlide;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.widget.ViewDragHelper;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.example.administrator.text1.R;
import com.nineoldandroids.view.ViewHelper;
import com.seaway.android.common.toast.Toast;

/**
 * 功能描述：自定义HorizontalScrollView水平滚动控件，
 * 1、实现普通侧滑菜单功能
 *（注：scrollTo（x,y）、smoothScrollTo(x,y)也可以表示为：移动到（x,y）当前坐标点，scrollTo：瞬间移动；smoothScrollTo：平滑移动）
 * Created by hzhm on 2016/6/24.
 */
public class SlideMenu extends HorizontalScrollView {

    private LinearLayout mWapper;
    private ViewGroup mMenu;
    private ViewGroup mContent;
    //屏幕宽度
    private int mScreenWidth;
    //菜单的宽度
    private int mMneuWidth;
    //菜单页正常显示距右距离
    private int mMenuRightPadding;
    //是否打开菜单栏
    private boolean isOpening = false;

    public SlideMenu(Context context) {
        this(context, null);
    }

    /**
     * 不带自定义属性布局时调用
     *
     * @param context
     * @param attrs
     */
    public SlideMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * 调用自定义属性时，调用的方法
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public SlideMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //获取屏幕宽度
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        mScreenWidth = outMetrics.widthPixels;

        //将dp转化为px
//        mMenuRightPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, context.getResources().getDisplayMetrics());

        //获取自定义属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SlideMenu, defStyleAttr, 0);
        //获取多少个属性对象
        int count = typedArray.getIndexCount();
        for (int i = 0; i < count; i++) {
            //获取每个属性兑现风格
            int attr = typedArray.getIndex(i);
            switch (attr) {
                case R.styleable.SlideMenu_slide_menu_padding:
                    //获得自定属性SlideMenu_slide_menu_padding的值
                    mMenuRightPadding = typedArray.getDimensionPixelSize(attr,
                            (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, context.getResources().getDisplayMetrics()));
                    break;
            }
        }
        typedArray.recycle();
    }

    /**
     * onMeasure里面完成该控件所有子视图宽、高的计算，及其自身宽、高的计算
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mWapper = (LinearLayout) getChildAt(0);
        mMenu = (ViewGroup) mWapper.getChildAt(0);
        mContent = (ViewGroup) mWapper.getChildAt(1);

        mMneuWidth = mMenu.getLayoutParams().width = mScreenWidth - mMenuRightPadding;
        mContent.getLayoutParams().width = mScreenWidth;
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * 完成所有子视图的布局显示
     *
     * @param changed
     * @param l
     * @param t
     * @param r
     * @param b
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        //一开始就隐藏Menu视图
        //注：scrollTo（x,y）,x、y表示滚动条滚动的距离，当x为正值时，滚动条向右移动，视图向左偏移,即表示视图由原来的（0，0）向左移动（mMneuWidth,0）
        this.scrollTo(mMneuWidth, 0);
    }

    /**
     * 监听手势滑动
     *
     * @param ev
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_UP:
                //getScrollX()为隐藏距离
                int scrollX = getScrollX();
                //如果隐藏距离大于菜单屏的一般，则继续隐藏菜单栏
                if (scrollX > mMneuWidth / 2) {
                    //smoothScrollTo(x,y)平滑滚动，自带动画效果
                    this.smoothScrollTo(mMneuWidth, 0);
                    isOpening = false;
                } else {
                    //如果小于...，则回到原点
                    this.smoothScrollTo(0, 0);
                    isOpening = true;
                }
                return true;
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 切换菜单栏
     */
    public void isOpenMenu(){
        if(!isOpening){
            this.smoothScrollTo(0,0);
            isOpening = true;
        }else {
            this.smoothScrollTo(mMneuWidth,0);
            isOpening = false;
        }
    }

    /**
     * 滚动发生时
     * @param l
     * @param t
     * @param oldl
     * @param oldt
     */
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        //(注：L = getScrollX,Scroll的为X轴上隐藏距离)
        float scale = l * 1.0f / mMneuWidth;//从1-0

        /**
         * 功能描述：2、实现抽屉式侧滑菜单
         * 区别：在于菜单栏相当与之前的位置实现了一个平移，实现类似于在内容页面底部的功能
         */
        //当Scroll滚动时，设置其水平平移量(即相当于一开始就将mMenu视图平移到了mContent下方)
        ViewHelper.setTranslationX(mMenu, l*0.8f);
        Toast.showToast(getContext(),l+"");

        /**
         * 功能描述：3、实现QQ5.0侧滑菜单
         * 区别一：内容区域的视图会有从1.0--0.7的一个缩放变化0.7+0.3*scale
         * 区别二：菜单区域会有一个偏移量的变化
         * 区别三：菜单区域会有一个缩放变化：0.7--1.0，即：1.0-scale*0.3，和一个透明度的变化：0.6-1.0，即：0.6+0.4（1-scale）
         */

        float rightScale = 0.7f+0.3f*scale;
        float lefrtScale = 1.0f-scale*0.3f;
        float leftAlpha = 0.1f+0.9f *(1-scale);

        //设置内容页缩放的中心点
        ViewHelper.setPivotX(mContent,0);
        ViewHelper.setPivotY(mContent,mContent.getHeight()/2);
        //设置内容页缩放
        ViewHelper.setScaleX(mContent,rightScale);
        ViewHelper.setScaleY(mContent,rightScale);

        //设置菜单页面的X、Y的缩放；透明度
        ViewHelper.setScaleX(mMenu,lefrtScale);
        ViewHelper.setScaleY(mMenu,lefrtScale);
        ViewHelper.setAlpha(mMenu,leftAlpha);
    }
}
