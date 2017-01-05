package com.example.administrator.text1.ui.testIndex.IndexByRecycleView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

/**
 * Created by hzhm on 2016/9/14.
 * 功能描述：自定义一个索引控件
 * 相关技术点：
 */
public class ListSideBar extends View {

    public int textSize;
    public static float RATIO;
    private Context mcontext;
    // 触摸事件
    private OnTouchingLetterChangedListener onTouchingLetterChangedListener;
    // 26个字母
    public static String[] b = {"!", "&", "1", "@", "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z", "#"};
    private int choose = -1;// 选中
    private Paint paint = new Paint();

    private TextView mTextDialog;

    public void setTextView(TextView mTextDialog) {
        this.mTextDialog = mTextDialog;
    }

    public ListSideBar(Context context) {
        super(context);
    }

    public ListSideBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ListSideBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 绘制视图
     */
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 获取焦点改变背景颜色.
        int height = getHeight();// 获取对应高度
        int width = getWidth(); // 获取对应宽度
        int singleHeight = height / b.length;// 获取每一个字母的高度

        for (int i = 0; i < b.length; i++) {
            paint.setColor(Color.parseColor("#525252"));
            paint.setAntiAlias(true);
            // 设置字体大小，使字体根据分辨率变换；
            paint.setTextSize(14);
            // 选中的状态
            if (i == choose) {
                paint.setColor(Color.parseColor("#525252"));
                paint.setFakeBoldText(true);
            }
            //*** x坐标等于中间-字符串宽度的一半(measureText(txt)：获得相应文本的宽度)
            float xPos = width / 2 - paint.measureText(b[i]) / 2;
            // 当i=0时，也得有相应的Y轴坐标
            float yPos = singleHeight * i + singleHeight;
            canvas.drawText(b[i], xPos, yPos, paint);
            paint.reset();// 重置画笔
        }
    }

    /**
     * 触屏事件（分发触屏事件）
     * @param event
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final int action = event.getAction();
        final float y = event.getY();// 点击y坐标
        final int oldChoose = choose;
        final OnTouchingLetterChangedListener listener = onTouchingLetterChangedListener;
        final int c = (int) (y / getHeight() * b.length);// 点击y坐标所占总高度的比例*b数组的长度就等于点击b中的个数.

        switch (action) {
            // 1、当松开触屏时：设置整个右边索引背景为白色，choose状态为为未选中，将预览文本设置为隐藏
            case MotionEvent.ACTION_UP:
                setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                choose = -1;
                invalidate();
                if (mTextDialog != null) {
                    mTextDialog.setVisibility(View.INVISIBLE);
                }
                break;
            // 2、其他情况：即按下触屏、及其滑动(注：c表示，点击b中的某个.)，设置整个右边索引控件背景为灰色，
                 //设置显示相应的预览文本，给相应的选中文本设置监听
            default:
                setBackgroundColor(Color.parseColor("#C9C9C9"));
                //当滑到下一个字母时候...
                if (oldChoose != c) {
                    if (c >= 0 && c < b.length) {
                        if (listener != null) {
                            listener.onTouchingLetterChanged(b[c]);
                            Log.e("onTouch",b[c]);
                        }
                        if (mTextDialog != null) {
                            mTextDialog.setText(b[c]);
                            mTextDialog.setVisibility(View.VISIBLE);
                        }
                        choose = c;
                        //重新绘制，即重新调用onDrow()方法...
                        invalidate();
                    }
                }
                break;
        }
        return true;
    }

    /**
     * 向外公开的方法
     *
     * @param onTouchingLetterChangedListener
     */
    public void setOnTouchingLetterChangedListener(
            OnTouchingLetterChangedListener onTouchingLetterChangedListener) {
        this.onTouchingLetterChangedListener = onTouchingLetterChangedListener;
    }

    /**
     * 接口
     *
     * @author coder
     */
    public interface OnTouchingLetterChangedListener {
        public void onTouchingLetterChanged(String s);
    }

}
