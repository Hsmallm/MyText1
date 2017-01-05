package com.example.administrator.text1.ui.testAnimator;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * 功能描述：
 * ValueAnimator、ObjectAnimator属性动画的高级用法：
 * （注：ObjectAnimator内部的工作机制:是通过寻找特定属性的get和set方法，然后通过方法不断地对值进行改变，从而实现动画效果的。）
 * 1、通过变换point点的坐标来实现圆的动态位置变化
 * 2、通过变换Radio半径的大小，来动态实现圆的大小变化
 * Created by HM on 2016/5/25.
 */
public class myView extends View {

    public static final float RADIO= 50f;
    private Paint paint;
    private myPoint currentPoint;
    private float currentRadio;
    private boolean isRunning = true;
    private String color;


    public myView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setColor(Color.parseColor("#27a1e5"));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(currentRadio == 0f){
            currentRadio = 0f;
            //通过当前点的坐标位置来画圆
            drawCircle(canvas);
            //开启动画，不断移动当前点坐标
            startAnimation();
        }else {
            drawCircle(canvas);
        }
    }

    /**
     * 通过当前点的坐标位置来画圆
     * @param canvas
     */
    public void drawCircle(Canvas canvas){
//        float x = currentPoint.getX();
//        float y = currentPoint.getY();
        canvas.drawCircle(getWidth()/2,getHeight()/2,currentRadio,paint);
    }

    /**
     * 开启动画，不断移动当前点坐标
     */
    public void startAnimation(){
        if(!isRunning)
            return;
//        myPoint startPoint = new myPoint(RADIO,RADIO);
//        myPoint endPoint = new myPoint(getWidth()-RADIO,getHeight()-RADIO);
        float startRadio = 0f;
        float endRadio = getWidth()/2;
        ValueAnimator animator = ValueAnimator.ofObject(new text(),startRadio,endRadio);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //获得当前点坐标，并刷新界面
//                currentPoint = (myPoint) animation.getAnimatedValue();
                currentRadio = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        ObjectAnimator animator2 = ObjectAnimator.ofObject(this,"color",new TextCurrentObj(),"#0000FF","#FF0000");
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(animator).with(animator2);
        animatorSet.setDuration(2000);
        animatorSet.start();
    }

    public void endAnimation(){
        float startRadio = getWidth()/2;
        float endRadio = 0f;
        ValueAnimator animator = ValueAnimator.ofObject(new text(),startRadio,endRadio);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentRadio = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        animator.setDuration(500);
        animator.start();
    }

    public void setAnimator(boolean isRunning){
        this.isRunning = isRunning;
        startAnimation();
    }

    /**
     * ObjectAnimator内部的工作机制:是通过寻找特定属性的get和set方法，然后通过方法不断地对值进行改变，从而实现动画效果的。
     */

    public String getColor() {
        return color;
    }

    /**
     * 在设置color属性时，将画笔的颜色设置进入
     * @param color
     */
    public void setColor(String color) {
        this.color = color;
        paint.setColor(Color.parseColor(color));
        invalidate();
    }
}
