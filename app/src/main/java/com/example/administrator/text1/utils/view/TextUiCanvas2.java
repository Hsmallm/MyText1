package com.example.administrator.text1.utils.view;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;

/**
 * Created by Administrator on 2016/3/3.
 */
public class TextUiCanvas2 extends PorterDuffXfermode {

    private Paint paint;
    private Paint paint2;

    /**
     * Create an xfermode that uses the specified porter-duff mode.
     *
     * @param mode The porter-duff mode that is applied
     */
    public TextUiCanvas2(PorterDuff.Mode mode) {
        super(mode);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.parseColor("#68f25a2b"));
        paint2 = new Paint();
        paint.setColor(Color.parseColor("#27a1e5"));
    }

    private void canvasXx(Canvas canvas){
        //绘制圆形
        canvas.drawCircle(100,100,50,paint);
        //绘制矩形
        canvas.drawRect(100,100,200,200,paint2);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
    }

}
