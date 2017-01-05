package com.example.administrator.text1.ui.testCanvas.testCanvas;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.example.administrator.text1.R;

/**
 * Created by hzhm on 2016/12/8.
 * 功能描述：实现带进度滚动的圆弧...
 * 相关技术的：1、drawArc画圆弧
 * 2、计算滚动到对应角度，亮点对应坐标
 * 3、ValueAnimator使用属性动画实现亮点的滚动
 */

public class TestCanvasToChart extends View {

    private int width;
    private int height;

    private float lastProgress = 150;
    private int r1;//外圆半径
    private double sin;//对应y轴
    private double cos;//对应x轴
    private float a;//r1 * sin，主要用于y轴的计算
    private float b;//r1 * cos，主要用于x轴的计算
    private float x;//滚动亮点的x轴坐标
    private float y;//滚动亮点的y轴坐标

    private ValueAnimator animator;
    private Bitmap bitmap;

    public TestCanvasToChart(Context context) {
        super(context);
    }

    public TestCanvasToChart(Context context, AttributeSet attrs) {
        super(context, attrs);

        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.dot_light);
        Matrix matrix = new Matrix();
        matrix.postScale(0.1f, 0.1f);
        bitmap = bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

        animator = new ValueAnimator();
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
        r1 = (width - 100) / 2;

        widthMeasureSpec = MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(heightMeasureSpec, MeasureSpec.EXACTLY);
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawCanvasArc(canvas);
        drawOval(canvas);
    }

    /**
     * 绘制圆弧形
     *
     * @param canvas
     */
    private void drawCanvasArc(Canvas canvas) {
        //实例化这个矩形类的参数分别是：左x轴、上y轴、右x轴、下y轴
        RectF rectF = new RectF(50, 50, width - 50, width - 50);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(8);
        //设置未封闭式圆形最后的盖帽样式
        paint.setStrokeCap(Paint.Cap.ROUND);

        RectF rectF2 = new RectF(80, 80, width - 80, width - 80);
        //------注：Paint.ANTI_ALIAS_FLAG：设置画笔为无锯齿状态...
        Paint paint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint2.setStyle(Paint.Style.STROKE);
        paint2.setColor(Color.BLUE);
        paint2.setStrokeWidth(20);
        paint2.setStrokeCap(Paint.Cap.ROUND);

        //参数分别是：自定义圆、开始弧度、圆弧扫过的角度，顺时针方向，单位为度、是否连接圆心；且是以顺时针方向进行绘制...
        canvas.drawArc(rectF, 150, 240, false, paint);
        canvas.drawArc(rectF2, 150, 240, false, paint2);
    }

    /**
     * 画图片...
     *
     * @param canvas
     */
    private void drawOval(Canvas canvas) {
//        RectF rectF = new RectF(45, height / 2, 55, height / 2 + 10);
//        Paint paint = new Paint();
//        paint.setStyle(Paint.Style.FILL);
//        paint.setColor(Color.RED);
//
//        canvas.drawOval(rectF, paint);
        caculatePoint();
        canvas.drawBitmap(bitmap, x, y, null);
    }

    /**
     * 计算滚动到对应角度，亮点对应坐标(注：这是一套统一的计算坐标点公式，尽管sin、cos肯能为负值...)
     */
    private void caculatePoint() {
        sin = Math.sin(Math.toRadians(lastProgress));
        cos = Math.cos(Math.toRadians(lastProgress));
        a = (float) (r1 * sin);
        b = (float) (r1 * cos);
        //x轴计算：屏幕宽度的一半 + 半径对象的cos值 - 光标的一半
        //y轴计算：半径对象的sos值 + 半径 + 距上间距 - 光标的一半
        x = width / 2 + b - bitmap.getHeight() / 2;
        y = a + r1 + 50 - bitmap.getHeight() / 2;
    }

    /**
     * 设置动画的滚动进度
     *
     * @param progress
     */
    public void setProgress(int progress) {
        if (animator.isRunning()) {
            animator.cancel();
        }
        float totalProgress = 0;
        lastProgress = 150;
        totalProgress = lastProgress + progress;
        //设置动画的起止进度
        animator.setFloatValues(lastProgress, totalProgress);
        //设置动画的运行事件
        animator.setDuration(2000);
        //设置动画的运行监听
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //------获取亮点滚动到的进度值...
                float progress2 = (float) animation.getAnimatedValue();
                lastProgress = progress2;
                //重绘（即表示会重新调用onDraw方法）
                invalidate();
            }
        });
        animator.start();
    }
}
