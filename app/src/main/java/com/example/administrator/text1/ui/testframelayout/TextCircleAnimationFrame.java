package com.example.administrator.text1.ui.testframelayout;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Region;
import android.util.AttributeSet;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;

/**
 * 功能描述：自定义一个FrameLayout控件，在dispatchDraw（）方法里面绘其子控件
 * Created by HM on 2016/5/24.
 */
public class TextCircleAnimationFrame extends FrameLayout implements ValueAnimator.AnimatorUpdateListener{

    private Path path;
    private int centerX;
    private int centerY;
    private int radius = 0;
    private int maxRadius = 0;
    private ValueAnimator valueAnimator;

    //创建三个不同参数的构造方法，主要是便于实例化这个对象时使用
    public TextCircleAnimationFrame(Context context) {
        super(context);
    }

    public TextCircleAnimationFrame(Context context, AttributeSet attrs) {
        super(context, attrs);
        path = new Path();
        valueAnimator = new ValueAnimator();
        valueAnimator.addUpdateListener(this);
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (radius == 0) {
//                    setVisibility(holdPosition ? View.INVISIBLE : View.GONE);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    public TextCircleAnimationFrame(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * dispatchDraw()方法，主要用于绘制其子视图控件的...
     * (注：但其子类控件的最终形状、大小由父类、子类共同决定，即为：谁最小形状大小归谁)
     * @param canvas
     */
    @Override
    protected void dispatchDraw(Canvas canvas) {
        //如果在编辑模式下（即为第一层运行）
        //Path类,即为路径类（定位一个文件或者指明一个路径。）
        path.addCircle(getMeasuredWidth() / 2, getMeasuredHeight() / 2, getMeasuredHeight() > getMeasuredWidth() ? getMeasuredHeight() / 2 : getMeasuredWidth() / 2, Path.Direction.CCW);
        canvas.clipPath(path);
        canvas.clipRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), Region.Op.INTERSECT);

        super.dispatchDraw(canvas);
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        radius = (int) animation.getAnimatedValue();
        path.reset();
        path.addCircle(centerX, centerY, radius, Path.Direction.CCW);
        invalidate();
    }

    public void computCircleInfo(boolean isLocationInWindow, int xInWindow, int yInWindow) {
        if (isLocationInWindow) {
            int[] loc = new int[2];
            getLocationInWindow(loc);
            centerX = xInWindow - loc[0];
            centerY = yInWindow - loc[1];
        } else {
            centerX = xInWindow;
            centerY = yInWindow;
        }
        int dx1 = Math.abs(centerX);
        int dx2 = Math.abs(centerX - getMeasuredWidth());
        int dx = dx1 > dx2 ? dx1 : dx2;
        int dy1 = Math.abs(centerY);
        int dy2 = Math.abs(centerY - getMeasuredHeight());
        int dy = dy1 > dy2 ? dy1 : dy2;
        maxRadius = (int) (Math.sqrt(dx * dx + dy * dy) + 2);
    }

    public void expand(){
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setIntValues(radius,maxRadius);
        valueAnimator.setDuration(200);
        valueAnimator.start();
    }

    public void close(){
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setIntValues(radius, 0);
        valueAnimator.setDuration(200);
        valueAnimator.start();
    }

}
