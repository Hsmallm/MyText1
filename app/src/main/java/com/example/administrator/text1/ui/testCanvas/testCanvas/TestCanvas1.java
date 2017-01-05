package com.example.administrator.text1.ui.testCanvas.testCanvas;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.example.administrator.text1.R;

/**
 * Created by hzhm on 2016/12/7.
 * 功能描述：测试Canvas画布类的相关绘图方法
 */

public class TestCanvas1 extends View {

    private int width;
    private int height;

    public TestCanvas1(Context context) {
        super(context);
    }

    public TestCanvas1(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(widthMeasureSpec);
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        drawPoint(canvas);
//        drawLine(canvas);
        drawArc(canvas);
    }

    /**
     * 绘制点
     *
     * @param canvas
     */
    private void drawPoint(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
//        paint.setStrokeWidth(20);
//        paint.setAlpha();
        paint.setAntiAlias(true);
        //绘制一个点
        canvas.drawPoint(100, 100, paint);
        //绘制多个点；new float[]里面两个为一组，表示相应点的(x,y)坐标
        canvas.drawPoints(new float[]{100, 100, 150, 100}, paint);
    }

    /**
     * 绘制线
     *
     * @param canvas
     */
    private void drawLine(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
        paint.setAlpha(100);
        paint.setAntiAlias(false);
        //绘制一条线
        canvas.drawLine(100, 100, 500, 500, paint);
        //绘制多条线；new float[]里面四个为一组，表示相应一条线的起始点坐标
        canvas.drawLines(new float[]{100, 200, 500, 600, 200, 200, 500, 700}, paint);
    }

    /**
     * 绘制多边形
     *
     * @param canvas
     */
    private void drawPath(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);

        Path path = new Path();
        path.moveTo(100, 100);
        path.lineTo(100, 500);
        path.lineTo(500, 500);
        path.close();
        canvas.drawPath(path, paint);

        Path path2 = new Path();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
        path2.moveTo(100, 600);
        path2.lineTo(100, 650);
        path2.lineTo(150, 650);
        path2.lineTo(200, 600);
        path2.lineTo(220, 600);
        path2.close();
        canvas.drawPath(path2, paint);
    }

    /**
     * 绘制矩形（正方形、长方形）
     *
     * @param canvas
     */
    private void drawRect(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);

        Rect rect = new Rect();
        rect.left = 100;
        rect.top = 100;
        rect.right = 500;
        rect.bottom = 500;
        canvas.drawRect(rect, paint);

        RectF rectF = new RectF(100, 100, 500, 200);
        canvas.drawRect(rectF, paint);
    }

    /**
     * 绘制圆形及其椭圆形
     *
     * @param canvas
     */
    private void drawOval(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL);
//        paint.setStrokeWidth(5);

        RectF rectF = new RectF(100, 100, 200, 200);
        canvas.drawOval(rectF, paint);

        RectF rectF2 = new RectF(300, 300, 500, 400);
        canvas.drawOval(rectF2, paint);
    }

    /**
     * 绘制弧形、扇形
     *
     * @param canvass
     */
    private void drawArc(Canvas canvass) {
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
        paint.setStrokeCap(Paint.Cap.ROUND);

        RectF rectF = new RectF(100, 100, 200, 200);
        RectF rectF2 = new RectF(400, 100, 500, 200);
        RectF rectF3 = new RectF(150, 200, 450, 400);
        //参数分别含义为：指定圆弧的外轮廓矩形区域、指定起始角度、指定绘制的角度（注：坐标0度在圆的右边，且为顺时针方向绘制）、设置是否连接圆心
        canvass.drawArc(rectF, 150, 240, false, paint);
        canvass.drawArc(rectF2, 150, 240, false, paint);
        canvass.drawArc(rectF3, 30, 120, false, paint);
    }

    /**
     * 绘制图片
     *
     * @param canvas
     */
    private void drawBitmap(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.hm);
        //---1、绘制图片的左上角起始点坐标
//        canvas.drawBitmap(bitmap,100,100,paint);

        //---2、这两个参数的含义分别为：图片显示的区域大小、图片显示位置
        //注：Rect src: 是对图片进行裁截，若是空null则显示整个图片
        //    RectF dst：是图片在Canvas画布中显示的区域，
        //    大于src则把src的裁截区放大，
        //    小于src则把src的裁截区缩小。
        Rect rect = new Rect(100, 100, 300, 300);
        RectF rectF = new RectF(200, 200, 400, 600);
//        canvas.drawBitmap(bitmap,null,rectF,paint);

        //---3、Matrix类：对图片进行缩放、旋转工具类
        Matrix matrix = new Matrix();
        //对图片进行缩放
        matrix.postScale(0.2f, 0.2f);
        //对图片进行旋转
        matrix.postRotate(45);
        //重新构建图片
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        //绘制画布所在区域的颜色
        canvas.drawColor(Color.BLACK);
        //对整个画布进行旋转
        canvas.drawBitmap(bitmap, 100, 100, null);
    }

    /**
     * 绘制图片:
     *
     * @param canvas
     */
    private void drawBitmap2(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.GRAY);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(0, 0, width, height, paint);
        //---canvas.save()：用来保存Canvas的状态。save之后，可以调用Canvas的平移、放缩、旋转、错切、裁剪等操作。
        //注：a、save和restore要配对使用（restore可以比save少，但不能多），如果restore调用次数比save多，会引发Error。save和restore之间，往往夹杂的是对Canvas的特殊操作
        //    b、在调用canvas.save()方法之后，接下来就是对调用Canvas的平移、放缩、旋转、错切、裁剪等操作；对其他非Canvas的操作，则是无法进行保存...
        canvas.save();

        //对画布进行旋转，并以画布中心点为旋转中心
        canvas.rotate(90, width / 2, height / 2);

        Paint paintLine = new Paint();
        paintLine.setColor(Color.BLUE);
        paintLine.setStyle(Paint.Style.STROKE);
        paintLine.setStrokeWidth(5);
        canvas.drawLine(width / 2, 0, 0, height / 2, paintLine);
        canvas.drawLine(width / 2, 0, width, height / 2, paintLine);
        canvas.drawLine(width / 2, 0, width / 2, height, paintLine);

        //---canvas.restore():来恢复Canvas之前保存的状态。防止save后对Canvas执行的操作对后续的绘制有影响。
        //注：canvas.restore()加在哪个位置，就会对下面的绘图产生影响
        canvas.restore();
        canvas.drawCircle(width - 10, height - 10, 10, paintLine);
    }
}
