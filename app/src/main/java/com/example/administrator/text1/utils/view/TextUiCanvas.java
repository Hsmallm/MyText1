package com.example.administrator.text1.utils.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposeShader;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;

import com.example.administrator.text1.R;

/**
 * Created by HM on 2016/3/1.
 * 功能描述：
 * Android UI效果之-Canvas绘图
 */
public class TextUiCanvas extends View{

    private Bitmap bitmap;
    private Paint paint;
    private Paint paint2;
    private RectF rectF;
    private RectF rectF2;
    private RectF rectF3;

    public TextUiCanvas(Context context) {
        super(context);
        init();
    }

    public TextUiCanvas(Context context, AttributeSet attrs){
        super(context, attrs);
        init();
    }

    public TextUiCanvas(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        //使用bitmap初始化一个图片对象
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.share_logo);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint2 = new Paint();
//        paint.setStyle(Paint.Style.STROKE);
//        paint.setColor(Color.parseColor("#68f25a2b"));
        paint.setStrokeWidth(12);
        paint2.setColor(Color.parseColor("#27a1e5"));
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float r = getWidth() / 4;
        rectF = new RectF(r, 20, 3 * r, 2 * r + 20);
        rectF2 = new RectF(0,120,50,270);
        rectF3 = new RectF(100,120,170,200);
        //画弧形
//        canvas.drawArc(rectF, 0, 120, true, paint);
        //画圆
//        canvas.drawCircle(100,100,50,paint);
        //画圆环
//        canvasRing(canvas);
        //画矩形
//        canvas.drawRect(rectF,paint);
        //画椭圆
//        canvas.drawOval(rectF2,paint);
        //画圆角矩形
//        canvas.drawRoundRect(rectF3,50,50,paint);
        //画线条
//        canvas.drawLine(0, 0, 300, 300,paint);
        //绘制路径
//        canvasPath(canvas);
        //绘制文字路径
//        canvasWordPath(canvas);
        //绘制时钟
//        canvasClock(canvas);
        //绘制靶子
        canvasMark(canvas);
//        canvasClampBig(canvas);
//        canvasClampBigSm(canvas);
//        canvasRepeatBig(canvas);
//        canvasBitMirror(canvas);
//        canvasLineClamp(canvas);
//        canvasRadial(canvas);
//        canvasSg(canvas);
//        canvasCs(canvas);
//        canvasXx(canvas);
    }

    /**
     * 画圆环
     * @param canvas
     */
    private void canvasRing(Canvas canvas){
        //画笔的样式：即为定义画的是否是环形
        paint.setStyle(Paint.Style.STROKE);
        //画笔的宽度：即为圆环的环形宽度
        paint.setStrokeWidth(8);
        canvas.drawCircle(100,100,50,paint);
    }

    /**
     * 绘制路径
     * @param canvas
     */
    private void canvasPath(Canvas canvas){
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(6);
        Path path = new Path();
        //起始点坐标
        path.moveTo(20,20);
        //移动到下一个点的坐标
        path.lineTo(120,30);
        path.lineTo(44,66);
        path.lineTo(77,231);
        canvas.drawPath(path,paint);
    }

    /**
     * 绘制文字路径
     * @param canvas
     */
    private void canvasWordPath(Canvas canvas){
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(6);
        Path path = new Path();
        //起始点坐标
        path.moveTo(50, 50);
        //移动到下一个点的坐标
        path.lineTo(200, 250);
        path.lineTo(300, 450);
        path.lineTo(300, 650);
        paint.setTextSize(46);
        canvas.drawTextOnPath("jhwhckjhijkdkwhmnxkhuhndkhwujj",path,0,0,paint);
    }

    /**
     * 绘制时钟
     * @param canvas
     */
    private void canvasClock(Canvas canvas){
        //1.第一步：开始画环
        paint.setColor(0xff00ccff);
        paint.setStrokeWidth(2);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        //将位置移动画纸的坐标点,不用每次都从坐标原点计算
        canvas.translate(canvas.getWidth() / 2, 500);
        canvas.drawCircle(0, 0, 200, paint);

        //2.第二部：绘制刻度（此时是以圆的中心为坐标点）
        Paint tmpPaint = new Paint(paint);
        tmpPaint.setStrokeWidth(1);
        float y = 200;//即为圆的半径
        int count = 60; // 总刻度数
        for (int i = 0; i < count; i++) {
            if (i % 5 == 0) {
                //大刻度，例如：1、2、....12
                canvas.drawLine(0f, y, 0, y + 12f, paint);
                canvas.drawText(String.valueOf(i / 5 + 1), -4f, y + 25f,
                        tmpPaint);
            } else {
                //小刻度
                canvas.drawLine(0f, y, 0f, y + 5f, tmpPaint);
            }
            //旋转画纸（以坐标（0，0）为参照对象，旋转360/count角度）
            canvas.rotate(360 / count, 0f, 0f);
        }
        //3.第二部：绘制指针
        paint.setStrokeWidth(2);
        canvas.drawLine(0, 0, 0, -65, paint);
        paint.setStrokeWidth(4);
        canvas.drawLine(0, 0, 20, -35, paint);
    }

    /**
     * 绘制靶子
     * @param canvas
     */
    private void canvasMark(Canvas canvas){
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.parseColor("#000000"));
        paint.setStrokeWidth(25);

        canvas.translate(canvas.getWidth()/2,500);
        canvas.drawCircle(0,0,300,paint);
        canvas.drawCircle(0,0,250,paint);
        canvas.drawCircle(0,0,200,paint);
        canvas.drawCircle(0,0,150,paint);
        canvas.drawCircle(0,0,100,paint);
        canvas.drawCircle(0,0,50,paint);

        Paint mPaint = new Paint();
        mPaint.setColor(Color.parseColor("#ff0000"));
        canvas.drawCircle(0,0,20,mPaint);

        Paint mPaint2 = new Paint();
        mPaint2.setColor(Color.parseColor("#000000"));
        mPaint2.setStrokeWidth(30);
        canvas.drawText("9",0,-25,mPaint2);

        Paint mPaint3 = new Paint();
        mPaint3.setColor(Color.parseColor("#ffffff"));
        mPaint3.setStrokeWidth(30);
        canvas.drawText("8",0,-45,mPaint3);
        canvas.drawText("7",0,-70,mPaint2);
        canvas.drawText("7",0,-70,mPaint2);
        canvas.drawText("6",0,-90,mPaint3);
        canvas.drawText("5",0,-120,mPaint2);
        canvas.drawText("4",0,-145,mPaint3);
        canvas.drawText("3",0,-170,mPaint2);
        canvas.drawText("2",0,-200,mPaint3);
        canvas.drawText("1",0,-220,mPaint2);
        canvas.drawText("-1",0,-250,mPaint3);
        canvas.drawText("-2",0,-270,mPaint2);
        canvas.drawText("-3",0,-300,mPaint3);
    }

    private void canvasXx(Canvas canvas){
        //绘制圆形
        canvas.drawCircle(100,100,50,paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        //绘制矩形
        canvas.drawRect(100,100,200,200,paint2);

    }

    // * 1、BitmapShader：位图渲染，顾名思义，使用BitmapShader可以对位图进行一些着色渲染操作
    /**
     *  a.CLAMP：当bitmap比要绘制的图形小时拉伸位图的最后一个像素；
     * @param canvas
     */
    private void canvasBitClampBig(Canvas canvas){
        BitmapShader bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        paint2.setShader(bitmapShader);
        canvas.drawRect(0, 0, bitmap.getWidth() * 2, bitmap.getHeight() * 2, paint2);
    }

    /**
     * 当bitmap比要绘制的图形大时，根据绘制图形剪裁bitmap
     * @param canvas
     */
    private void canvasBitClampSm(Canvas canvas){
        BitmapShader bitmapShader = new BitmapShader(bitmap,Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        paint.setShader(bitmapShader);
        RectF rectF = new RectF(100,100,300,300);
        canvas.drawRoundRect(rectF,30,30,paint2);
    }

    /**
     * b.REPEAT ：当bitmap比要绘制的图形小时横向纵向不断重复bitmap；当bitmap比要绘制的图形大时，根据绘制图形剪裁bitmap
     * @param canvas
     */
    private void canvasBitRepeatBig(Canvas canvas){
        BitmapShader bitmapShader = new BitmapShader(bitmap,Shader.TileMode.REPEAT,Shader.TileMode.REPEAT);
        paint.setShader(bitmapShader);
//        canvas.drawRect(0,0,bitmap.getWidth()*2,bitmap.getHeight()*2,paint);
        RectF rectF = new RectF(100,100,300,300);
        canvas.drawRoundRect(rectF,30,30,paint2);
    }


    /**
     * c.MIRROR ：和REPEAT 类似，当bitmap比要绘制的图形小时横向纵向不断重复bitmap，不同的是相邻的两个bitmap互为镜像
     * @param canvas
     */
    private void canvasBitMirror(Canvas canvas){
        BitmapShader bitmapShader = new BitmapShader(bitmap,Shader.TileMode.MIRROR,Shader.TileMode.REPEAT);
        paint.setShader(bitmapShader);
        canvas.drawRect(0,0,bitmap.getWidth()*2,bitmap.getHeight()*2,paint);
    }

    //2.LinearGradient 线性渐变
    /**
     * 创建一个LinearGradient
     * x0           起始X坐标
     * y0           起始Y坐标
     * x1           结束X坐标
     * y1           结束Y坐标(坐标的定义即表示每绘制一次的区域大小)
     * color0      起始颜色值
     * color1      结束颜色值
     * tile        shader的mode
     例如：1.public LinearGradient(float x0, float y0, float x1, float y1, int color0, int color1,TileMode tile)

           2.LinearGradient(float x0, float y0, float x1, float y1, int[] colors, float[] positions, Shader.TileMode tile)
     两个构造方法类似，第二个只是可以添加更多颜色，把颜色值封装到了数组colors中，其后的positions是与之colors对于的颜色的比例，如果为null，颜色值则平均分布。
     LinearGradient和BitmapShader一样有三个TileMode可选CLAMP、REPEAT、MIRROR。实现的效果如下:
     */
    private void canvasLineClamp(Canvas canvas){
        LinearGradient lg = new LinearGradient(0,0,100,100,0xFF0000FF,0xFFFF0000 ,Shader.TileMode.REPEAT);
        paint.setShader(lg);
        canvas.drawRect(0,0,canvas.getWidth(),canvas.getHeight(),paint);
    }

    //3.RadialGradient圆形渐变
    /**
     * @param canvas
     */
    private void canvasRadial(Canvas canvas){
        RadialGradient rg = new RadialGradient(canvas.getWidth()/2,canvas.getHeight()/2,200,0xffff0000, 0xff0000ff,Shader.TileMode.REPEAT);
        paint.setShader(rg);
        canvas.drawRect(0,0,canvas.getWidth(),canvas.getHeight(),paint);
    }

    //4.SweepGradient

    /**
     * colors颜色数组，主要是positions，positions中每个item的取值范围在0f-1f之间，对于colors中相应颜色在图形中的位置
     * @param canvas
     */
    private void canvasSg(Canvas canvas){
        SweepGradient sg = new SweepGradient(canvas.getWidth()/2,canvas.getHeight()/2,0xffff0000, 0xff0000ff);
        //定义一个颜色数组
        int[] colors = {0xffff0000, 0xff00ff00, 0xffffff00, 0xffffffff,0xff000000};
        //定义一个位置数组
        float [] positions = {0f,0.25f, 0.5f, 0.75f, 1f};
        SweepGradient sg2 = new SweepGradient(canvas.getWidth()/2,canvas.getHeight()/2,colors,positions);
        paint.setShader(sg2);
        canvas.drawRect(0,0,canvas.getWidth(),canvas.getHeight(),paint);
    }

    //5.ComposeShader

    /**
     * ComposeShader，混合Shader，其实就是对两个shader进行取并集交集操作
     * @param canvas
     */
    private void canvasCs(Canvas canvas){
        PorterDuffXfermode model = new PorterDuffXfermode(PorterDuff.Mode.SRC);
        Shader shaderA = new RadialGradient(canvas.getWidth()/2,canvas.getHeight()/2,200,0xffff0000, 0xff0000ff,Shader.TileMode.REPEAT);
        Shader shaderB = new SweepGradient(canvas.getWidth()/2,canvas.getHeight()/2,0xffff0000, 0xff0000ff);
//        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
        ComposeShader cs = new ComposeShader(shaderA,shaderB,model);
        paint.setShader(cs);
        canvas.drawRect(0,0,canvas.getWidth(),canvas.getHeight(),paint);
    }
}
