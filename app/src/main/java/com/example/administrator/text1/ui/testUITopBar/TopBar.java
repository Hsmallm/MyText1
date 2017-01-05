package com.example.administrator.text1.ui.testUITopBar;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.text1.R;

/**
 * 功能描述：自定义UI模版，即自定义一个自带自定义属性的新控件
 * 功能实现：新控件集成有TopBar顶部的左右Button、及中间TextView文本
 * Created by hzhm on 2016/7/9.
 */
public class TopBar extends RelativeLayout {

    private Button leftBtn, rightBtn;
    private TextView title;

    private String leftText;
    private int leftTextColor;
    private Drawable leftBackground;
    private float leftTextSize;

    private String rightText;
    private int rightTextColor;
    private Drawable rightBackground;
    private float rightTextSize;

    private String titleText;
    private int titleTextColor;
    private float titleTextSize;

    private LayoutParams leftParams, rightParams, titleParams;
    private topbarClickListener mListener;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public TopBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        //实例化相关属性对象
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TopBar);
        leftText = ta.getString(R.styleable.TopBar_leftText);
        leftTextColor = ta.getInt(R.styleable.TopBar_leftTextColor,0);
        leftBackground = ta.getDrawable(R.styleable.TopBar_leftBackground);
        leftTextSize = ta.getDimension(R.styleable.TopBar_leftTextSize,0);

        rightText = ta.getString(R.styleable.TopBar_rightText);
        rightTextColor = ta.getInt(R.styleable.TopBar_rightTextColor,0);
        rightBackground = ta.getDrawable(R.styleable.TopBar_rightBackground);
        rightTextSize = ta.getDimension(R.styleable.TopBar_rightTextSize,0);

        titleText = ta.getString(R.styleable.TopBar_titleText);
        titleTextColor = ta.getInt(R.styleable.TopBar_titleTxtColor,0);
        titleTextSize = ta.getDimension(R.styleable.TopBar_titleTextSize,0);

        ta.recycle();//回收

        //给相关对象设置相应的属性
        leftBtn = new Button(context);
        rightBtn = new Button(context);
        title = new TextView(context);

        leftBtn.setText(leftText);
        leftBtn.setTextColor(leftTextColor);
        leftBtn.setBackground(leftBackground);
        leftBtn.setTextSize(leftTextSize);

        rightBtn.setText(rightText);
        rightBtn.setTextColor(rightTextColor);
        rightBtn.setBackground(rightBackground);
        rightBtn.setTextSize(rightTextSize);

        title.setText(titleText);
        title.setTextColor(titleTextColor);
        title.setTextSize(titleTextSize);
        title.setGravity(Gravity.CENTER);

        setBackgroundColor(Color.parseColor("#0086d1"));

        //布局对象控件,添加到ViewGroup
        leftParams  = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        leftParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT,TRUE);
        addView(leftBtn,leftParams);

        rightParams  = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rightParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,TRUE);
        addView(rightBtn,rightParams);

        titleParams  = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        titleParams.addRule(RelativeLayout.CENTER_IN_PARENT,TRUE);
        addView(title,titleParams);

        leftBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.leftClick();
            }
        });

        rightBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.rightClick();
            }
        });
    }

    public interface topbarClickListener{
        public void leftClick();
        public void rightClick();
    }

    /**
     * 创建一个回调函数，用于处理主界面的点击事件的处理
     * @param listener
     */
    public void setOnTopBarClickListener(topbarClickListener listener){
        this.mListener = listener;
    }

    /**
     * 设置坐标按钮是否显示
     * @param flag
     */
    public void setLeftBtnIsVisible(boolean flag){
        if(flag){
            leftBtn.setVisibility(VISIBLE);
        }else {
            leftBtn.setVisibility(GONE);
        }
    }

    /**
     * 设置右边按钮是否显示
     * @param flag
     */
    public void setRightBtnIsVisible(boolean flag){
        if(flag){
            rightBtn.setVisibility(VISIBLE);
        }else {
            rightBtn.setVisibility(GONE);
        }
    }
}
