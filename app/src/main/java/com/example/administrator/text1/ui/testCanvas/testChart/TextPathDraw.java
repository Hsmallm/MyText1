package com.example.administrator.text1.ui.testCanvas.testChart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposePathEffect;
import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.DiscretePathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.RectF;
import android.graphics.SumPathEffect;
import android.util.AttributeSet;
import android.view.View;

/**
 * 功能描述：1、测试Path类画图，即为根据path所给路径画图
 * 2、PathEffect绘制效果类的使用
 * Created by hzhm on 2016/6/7.
 */
public class TextPathDraw extends View {

    private Paint mPaint;
    private RectF rectF;
    private PathEffect effect;
    private PathEffect effect3;

    private float r;
    private float w;
    private float h;

    public TextPathDraw(Context context) {
        super(context);
    }

    public TextPathDraw(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        rectF = new RectF();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        r = (width - 100) / 2;
        w = width;
        h = height / 2;
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.BLUE);

        //设置画笔类型（为填充类型）
//        mPaint.setStyle(Paint.Style.FILL);
        //设置画笔类型（为线条）
        mPaint.setStyle(Paint.Style.STROKE);
        //设置画笔宽度
        mPaint.setStrokeWidth(3);


        drawCanvasArc(canvas);
//        canvas.save();
//        canvas.translate(200, 200);
    }

    /**
     * 绘制圆形
     * @param canvas
     */
    private void drawCanvasArc(Canvas canvas) {
        //实例化这个矩形类的参数分别是：左x轴、上y轴、右x轴、下y轴
        rectF = new RectF(50, 50, w - 50, r * 2 + 50);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(10);
        //设置未封闭式圆形最后的盖帽样式
        paint.setStrokeCap(Paint.Cap.ROUND);
//        paint.setTextAlign(Paint.Align.CENTER);

        //参数分别是：自定义圆、开始弧度、圆弧扫过的角度，顺时针方向，单位为度、是否连接圆心；且是以顺时针方向进行绘制...
        canvas.drawArc(rectF, 150, 90, false, paint);
    }

    /**
     * 绘制三角形
     *
     * @param canvas
     */
    private void drawThree_Side(Canvas canvas) {
        //------实例化路径path类
        Path path1 = new Path();
        //设置图形的开始点
        path1.moveTo(10, 340);
        //设置下一点坐标，并将其于起始点相互连接
        path1.lineTo(70, 340);
        path1.lineTo(40, 290);
        //设置为闭合状态
        path1.close();

        //------实例化Paint画笔类
        Paint paint = new Paint();
        //设置画笔颜色
        paint.setColor(Color.GREEN);
        //设置画笔样式
        paint.setStyle(Paint.Style.STROKE);
        //设置画笔宽度
        paint.setStrokeWidth(3);
        //DashPathEffect将Path的线段虚线化。
        effect = new DashPathEffect(new float[]{5, 5}, 0);
        paint.setPathEffect(effect);

        canvas.drawPath(path1, paint);
    }

    /**
     * 绘制一个平行四边型
     *
     * @param canvas
     */
    private void drawFour_Side(Canvas canvas) {
        //绘制一个平行四边型
        Path path3 = new Path();
        Paint paint3 = new Paint();
        paint3.setColor(Color.BLUE);
        paint3.setStyle(Paint.Style.STROKE);
        paint3.setStrokeWidth(3);
        //CornerPathEffect（绘制效果类）设置连接点夹角为圆角
        effect3 = new CornerPathEffect(20);
        paint3.setPathEffect(effect3);
        path3.moveTo(100, 100);
        path3.lineTo(200, 150);
        path3.lineTo(300, 150);
        path3.lineTo(200, 100);
        path3.close();
        canvas.drawPath(path3, paint3);
    }

    /**
     * 绘制五角星
     *
     * @param canvas
     */
    private void drawFive_Side(Canvas canvas) {
        //绘制五角星
        Path path2 = new Path();
        Paint paint2 = new Paint();
        paint2.setColor(Color.RED);
        paint2.setStyle(Paint.Style.STROKE);
        paint2.setStrokeWidth(3);
//        PathEffect effect2 = new PathDashPathEffect(path2,0,0, PathDashPathEffect.Style.ROTATE);
//        paint2.setPathEffect(effect2);
        //组合效果，ComposePathEffect (PathEffect outerpe,PathEffect innerpe)，表现时，会首先将innerpe表现出来，然后再在innerpe的基础上去增加outerpe的效果。
        PathEffect effect2 = new ComposePathEffect(effect, effect3);
        paint2.setPathEffect(effect2);
        path2.moveTo(27, 360);
        path2.lineTo(54, 360);
        path2.lineTo(70, 392);
        path2.lineTo(40, 420);
        path2.lineTo(10, 392);
        path2.close();
        canvas.drawPath(path2, paint2);
    }

    /**
     * 绘制一个梯形
     *
     * @param canvas
     */
    private void drawTrapezoid(Canvas canvas) {
        //绘制一个梯形
        Path path4 = new Path();
        Paint paint4 = new Paint();
        paint4.setColor(Color.YELLOW);
        paint4.setStyle(Paint.Style.STROKE);
        paint4.setStrokeWidth(3);
        //DiscretePathEffect这个类的作用是打散Path的线段，使得在原来路径的基础上发生打散效果。
        PathEffect effect4 = new DiscretePathEffect(20, 0);
        paint4.setPathEffect(effect4);
        path4.moveTo(200, 200);
        path4.lineTo(300, 200);
        path4.lineTo(400, 400);
        path4.lineTo(100, 400);
        path4.close();
        canvas.drawPath(path4, paint4);
    }

    /**
     * 绘制棱形
     *
     * @param canvas
     */
    private void drawRhombus(Canvas canvas) {
        //绘制棱形
        Path path5 = new Path();
        Paint paint5 = new Paint();
        paint5.setColor(Color.BLACK);
        paint5.setStyle(Paint.Style.STROKE);
        paint5.setStrokeWidth(3);
        //SumPathEffect叠加效果，两个效果简单的重叠在一起显示出来
        PathEffect effect5 = new SumPathEffect(effect, effect3);
        paint5.setPathEffect(effect5);
        path5.moveTo(200, 500);
        path5.lineTo(300, 600);
        path5.lineTo(200, 700);
        path5.lineTo(100, 600);
        path5.close();
        canvas.drawPath(path5, paint5);
    }
}
