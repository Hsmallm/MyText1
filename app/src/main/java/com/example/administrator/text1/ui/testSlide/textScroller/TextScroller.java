package com.example.administrator.text1.ui.testSlide.textScroller;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.Scroller;

/**
 * 功能描述：测试Scroller平滑滚动的一个Helper类使用
 * Created by HM on 2016/5/30.
 */
public class TextScroller extends LinearLayout{

    //平滑滚动的一个Helper类使用
    private Scroller scroller;

    public TextScroller(Context context, AttributeSet attrs) {
        super(context, attrs);
        scroller  = new Scroller(context);
    }

    //这个方法computeScroll()：当我们执行ontouch或invalidate(）或postInvalidate()都会导致这个方法的执行
    @Override
    public void computeScroll() {
        super.computeScroll();
        if(scroller.computeScrollOffset()){
            scrollTo(scroller.getCurrX(),0);
            postInvalidate();
        }
    }

    public void beginScroll(){
        //第一个参数是起始移动的x坐标值，第二个是起始移动的y坐标值，第三个第四个参数都是移到某点的坐标值，而duration 当然就是执行移动的时间
        scroller.startScroll(0,0,-500,0,2000);
        invalidate();
    }
}
