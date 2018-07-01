package com.example.administrator.text1.advanceAndroid.TestAnimation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.Button;
import android.widget.ImageView;

import com.example.administrator.text1.R;
import com.seaway.android.common.toast.Toast;

/**
 * @author HuangMing on 2018/2/2.
 *         功能描述：属性动画：Android3.0引入，可直接改变View的属性值，而且几乎可以对任何对象执行动画，并不只限于View,某种意义上来说他是增强版的补间动画（只能针对View进行操作）
 *         ValueAnimator：是整个属性动画里面一个最核心一个类，属性动画的运行机制是就是不断对值操作来实现的，初始值和结束值之间的动画过渡就是由ValueAnimator这个类来负责计算的
 *         ObjectAnimator：ObjectAnimator可能才是我们最常接触到的类，因为ValueAnimator只不过是对值进行了一个平滑的动画过渡，但我们实际使用到这种功能的场景好像并不多。
 *         而ObjectAnimator则就不同了，它是可以直接对任意对象的任意属性进行动画操作的
 */

public class TestAnimation3 extends AppCompatActivity {

    private Button startBtn;
    private Button endBtn;
    private ImageView imageView;
    private AnimatorSet animationSet;

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
                testObjectAnimation5();
            }
        });
        endBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animationSet.cancel();
            }
        });
    }

    private void testValueAnimation() {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0f, 1f);
        valueAnimator.setDuration(3000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                Log.e("TestAnimation3", value + "");
            }
        });
        valueAnimator.start();
    }

    /**
     * 属性动画---透明度变化
     */
    private void testObjectAnimation() {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(imageView, "alpha", 0, 1, 0);
        objectAnimator.setDuration(3000);
        //当重复次数设置为-1时候，表示一直重复
        objectAnimator.setRepeatCount(-1);
        objectAnimator.start();
    }

    /**
     * 属性动画---旋转
     */
    private void testObjectAnimation2() {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(imageView, "rotation", 0, 360, 0);
        objectAnimator.setDuration(3000);
        objectAnimator.start();
    }

    /**
     * 属性动画---位移
     */
    private void testObjectAnimation3() {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(imageView, "translationY", 0, 300, 0);
        objectAnimator.setDuration(3000);
        objectAnimator.start();
    }

    /**
     * 属性动画---缩放
     * 沿X轴进行缩放：scaleX；沿Y轴进行缩放：scaleY
     */
    private void testObjectAnimation4() {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(imageView, "scaleX", 1f, 2f, 1f);
        objectAnimator.setDuration(3000);
        objectAnimator.start();
    }

    /**
     * 属性动画---组合动画
     * <p>
     * after(Animator anim) ：将现有动画插入到传入的动画之后执行
     * after(long delay) ：将现有动画延迟指定毫秒后执行(注：只能加入到某一个动画之后，不能放入组合动画之中)
     * before(Animator anim)： 将现有动画插入到传入的动画之前执行
     * with(Animator anim) ：将现有动画和传入的动画同时执行
     */
    private void testObjectAnimation5() {
        ObjectAnimator scaleXAnimation = ObjectAnimator.ofFloat(imageView, "scaleX", 1f, 2f, 1f);
        ObjectAnimator scaleYAnimation = ObjectAnimator.ofFloat(imageView, "scaleY", 1f, 2f, 1f);
        ObjectAnimator translationXAnimation = ObjectAnimator.ofFloat(imageView, "translationX", 0, 200, 0);
        ObjectAnimator alphaAnimation = ObjectAnimator.ofFloat(imageView, "alpha", 1f, 0, 1f);
        animationSet = new AnimatorSet();
        animationSet.play(scaleXAnimation).with(scaleYAnimation).with(alphaAnimation).after(translationXAnimation);
        //只能加入到某一个同时动画操作之后，不能放入组合动画之中
//        animationSet.play(scaleXAnimation).with(scaleYAnimation).with(alphaAnimation).after(2000).after(translationXAnimation);//(无效)
//        animationSet.play(scaleXAnimation).with(scaleYAnimation).with(alphaAnimation).after(2000);//(有效)
        animationSet.setDuration(3000);
        //添加动画监听事件
//        animationSet.addListener(new Animator.AnimatorListener() {
//            @Override
//            public void onAnimationStart(Animator animation) {
//                //动画开始
//                Toast.showToast(TestAnimation3.this, "AnimationStart");
//            }
//
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                //动画结束
//                Toast.showToast(TestAnimation3.this, "AnimationEnd");
//            }
//
//            @Override
//            public void onAnimationCancel(Animator animation) {
//                //动画取消
//                Toast.showToast(TestAnimation3.this, "AnimationCancel");
//            }
//
//            @Override
//            public void onAnimationRepeat(Animator animation) {
//                //动画重复
//                Toast.showToast(TestAnimation3.this, "AnimationRepeat");
//            }
//        });
        //这一种设置监听的方式，里面的监听方法可以选择性重写
        animationSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                Toast.showToast(TestAnimation3.this, "AnimationStart");
            }
        });
        animationSet.start();
    }
}
