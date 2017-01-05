package com.example.administrator.text1.ui.testframelayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.example.administrator.text1.R;

/**
 * 功能描述：自定义一个Framelayout(层布局控件)，实现整个控件为正方形，其子控件逐层叠加
 * Created by HM on 2016/5/24.
 */
public class TextFrameLayout extends FrameLayout {

    private boolean baseOnWith = true;
    private float ratio = 1;


    public TextFrameLayout(Context context) {
        super(context);
    }

    public TextFrameLayout(Context context, AttributeSet attr) {
        super(context, attr);
    }

    public TextFrameLayout(Context context, AttributeSet attr, int defStyleAttr) {
        super(context, attr, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attr, R.styleable.TextFrameLayout);
        baseOnWith = typedArray.getBoolean(R.styleable.TextFrameLayout_text_baseonwith, true);
        ratio = typedArray.getFloat(R.styleable.TextFrameLayout_text_ratio, 1f);
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int heigth = MeasureSpec.getSize(heightMeasureSpec);
        if (baseOnWith) {
            heigth = (int) (width * ratio);
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(heigth, MeasureSpec.EXACTLY);
        } else {
            width = (int) (heigth * ratio);
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(width,MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);
    }
}
