package com.example.administrator.text1.utils.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.example.administrator.text1.R;

/**
 * Created by hzhm on 2016/12/5.
 * 功能描述：自定义View,实现一个等比例显示图片的ImageView控件
 * 1、super(context, attrs,0)与this(context, attrs,0);这两个函数的区别
 *    super(context, attrs,0)：表示调用父类的三参构造函数
 *    与this(context, attrs,0)：表示调用此类的三参构造函数
 * 2、android 自己实现的代码写在 super()之前和 super()之后的区别？？
 *    * a、(放在前)放在前后有啥区别就看父类这个super()的执行代码对你的代码有没有影响了。
           有时候有些子类实现父类方法，必须第一句话就调用super.XXX，是因为需要依赖父类的这个方法进行一些初始化，如果不放在前面，
           会导致后面的代码执行出错，那么这个时候就需要放到前面。
 *      b、(放在后)如果是对onMesure()这个方法进行重构，这时我的一些初始化宽、高的方法并没有对super（）这个方法进行依赖，
 *         相反我初始化的宽、高正好是为了改变super()里面的参数...
 */

public class FixRatioImageView extends ImageView {

    private float raito = 1f;//宽/高的比例；高/宽的比例
    private boolean baseOnWidth = true;//是否是基于宽度的比例
    private boolean matchDrawableRatio = false;

    public FixRatioImageView(Context context) {
        super(context);
    }

    public FixRatioImageView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public FixRatioImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.FixRatio);
        raito = typedArray.getFloat(R.styleable.FixRatio_ratio, 1f);
        baseOnWidth = typedArray.getBoolean(R.styleable.FixRatio_bathOnWidth, true);
        matchDrawableRatio = typedArray.getBoolean(R.styleable.FixRatio_matchDrawableRatio, false);
        //（注：在执行完之后，一定要确保调用recycle()函数，用于检索从这个结构对应于给定的属性位置到obtainStyledAttributes中的值。）
        typedArray.recycle();
        Drawable drawable = getDrawable();
        setRatioByDrawableRatio(drawable);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        if (baseOnWidth) {
            height = (int) (width * raito);
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY);
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        } else {
            width = (int) (height * raito);
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY);
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    private void setRatioByDrawableRatio(Drawable drawable) {
        if (matchDrawableRatio) {
            if (null != drawable && matchDrawableRatio) {
                if (baseOnWidth) {
                    raito = drawable.getIntrinsicHeight() * 1f / drawable.getIntrinsicWidth();
                } else {
                    raito = drawable.getIntrinsicWidth() * 1f / drawable.getIntrinsicHeight();
                }
            }
        }
        requestLayout();
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        setRatioByDrawableRatio(drawable);
    }
}