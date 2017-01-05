package com.example.administrator.text1.ui.testAnimator;

import android.animation.TypeEvaluator;

/**
 * 功能描述：自定义一个实现TypeEvaluator接口对象text，在动画进行过程中不断的获取当前点坐标
 * Created by HM on 2016/5/25.
 */
public class text implements TypeEvaluator {
    @Override
    public Object evaluate(float fraction, Object startValue, Object endValue) {
//        myPoint startPoint = (myPoint) startValue;
//        myPoint endPoint = (myPoint) endValue;
        //获取并返回当前点坐标
//        float x = startPoint.getX() + fraction * (endPoint.getX() - startPoint.getX());
//        float y = startPoint.getY() + fraction * (endPoint.getY() - startPoint.getY());
//        myPoint point = new myPoint(x, y);
//        return point;
        float startRadio = (float) startValue;
        float endRadio = (float) endValue;
        float currentRadio = startRadio + fraction * (endRadio - startRadio);
        return currentRadio;
    }
}
