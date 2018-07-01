package com.example.administrator.text1.advanceAndroid.TestAnimation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;

import com.example.administrator.text1.R;

/**
 * @author HuangMing on 2018/2/1.
 *         功能描述：补间动画：即表示不用控制动画的每一帧，只用控制动画开始和结束的两帧，设置其相应的变化时间和方式
 *         Interpolator：插值器，就是Android系统在补间动画开始和结束关键帧之间插入的渐变值，主要用于控制动画的变换速度
 *         补间动画的四种类型：透明度动画AlphaAnimation、大小动画ScaleAnimation、位移动画TranslationAnimation、旋转动画RotateAnimation
 */

public class TestAnimation2 extends AppCompatActivity {

    private Button startBtn;
    private Button endBtn;
    private ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_animation);

        startBtn = (Button) findViewById(R.id.startBtn);
        endBtn = (Button) findViewById(R.id.endBtn);
        imageView = (ImageView) findViewById(R.id.imageView);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rotate();
            }
        });
        endBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    /**
     * 补间动画之---透明度动画...
     */
    private void alpha() {
        AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0);
        alphaAnimation.setDuration(2000);
        //设置动画结束之后的状态是否是动画的最终状态，true，表示是保持动画结束时的最终状态
        alphaAnimation.setFillAfter(false);
        //设置动画结束之后的状态是否是动画开始时的状态，true，表示是保持动画开始时的状态
//        alphaAnimation.setFillBefore(true);
        //设置动画的重复模式：反转REVERSE和重新开始RESTART
        alphaAnimation.setRepeatMode(Animation.REVERSE);
        alphaAnimation.setRepeatCount(1);
        //动画播放延迟时长，就是调用start之后延迟多少时间播放动画
        alphaAnimation.setStartOffset(2000);
        imageView.startAnimation(alphaAnimation);
        //清除动画
//        imageView.clearAnimation();
        //同样也是清除动画
//        alphaAnimation.cancel();
    }

    /**
     * 补间动画----缩放动画...
     * 以view中心为缩放点，由初始状态放大两倍
     * fromX：开始时候X坐标轴的伸缩尺寸
     * fromY：开始时候Y坐标轴的伸缩尺寸
     * toX：结束时候X坐标轴的伸缩尺寸
     * toY：结束时候Y坐标轴的伸缩尺寸
     * pivotX：缩放动画的中心点X
     * pivotY：缩放动画的中心点Y
     * pivotXType：缩放动画X轴的伸缩模式
     * pivotYType：缩放动画Y轴的伸缩模式
     */
    private void scale() {
        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 2.0f, 1.0f, 2.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(2000);
        scaleAnimation.setFillAfter(true);
        imageView.startAnimation(scaleAnimation);
    }

    /**
     * 以view左上角，X轴增加100px，Y轴增加200px，为缩放点，由初始状态放大两倍
     */
    private void scale2() {
        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 2.0f, 1.0f, 2.0f, 100.0f, 200.0f);
        scaleAnimation.setDuration(2000);
        scaleAnimation.setFillAfter(true);
        imageView.startAnimation(scaleAnimation);
    }

    /**
     * 以view左上角为缩放点，由初始状态放大两倍
     */
    private void scale3() {
        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 2.0f, 1.0f, 2.0f);
        scaleAnimation.setDuration(2000);
        scaleAnimation.setFillAfter(true);
        imageView.startAnimation(scaleAnimation);
    }

    /**
     * 补间动画---位移动画
     * fromXType：动画开始时候X轴的位移模式
     * fromXValue：动画开始前的View的X坐标
     * toXType：动画结束时候X轴的位移模式
     * toXValue：动画结束时候View的X坐标
     * Y....
     */
    private void translate() {
        TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 2f,
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 2f);
        translateAnimation.setDuration(2000);
        translateAnimation.setFillAfter(true);
        imageView.startAnimation(translateAnimation);
    }

    /**
     * 补间动画---旋转动画
     * fromDegrees：动画开始时的旋转角度
     * toDegrees：动画结束时的旋转角度
     * pivotXType：动画在X轴的旋转模式
     * pivotXValue：动画在X轴的坐标开始位置
     * pivotYType：动画在Y轴的旋转模式
     * pivotYValue：动画在X轴的坐标开始位置
     * 角度为正值：表示View将自西向东顺时针旋转；角度为负值：表示View将自东向西逆时针旋转
     */
    private void rotate() {
        RotateAnimation rotateAnimation = new RotateAnimation(0, 720, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(2000);
        rotateAnimation.setFillAfter(true);
        imageView.startAnimation(rotateAnimation);
    }
}
