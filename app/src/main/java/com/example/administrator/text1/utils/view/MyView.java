package com.example.administrator.text1.utils.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;

import com.example.administrator.text1.R;

/**
 * 功能描述：自定义View
 * Created by HM on 2016/4/8.
 */
public class MyView extends View {

    public Paint paint;

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MyView);
        int textColor = a.getColor(R.styleable.MyView_myColor, 0);
        float textSize = a.getDimension(R.styleable.MyView_myTextSize, 0);
        paint.setTextSize(textSize);
        paint.setColor(textColor);
        a.recycle();
    }

    public MyView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);
        paint.setStyle(Style.FILL);
        canvas.drawText("aaaaaaa", 100, 200, paint);
    }

}
