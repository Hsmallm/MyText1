package com.example.administrator.text1.ui.testAnimator;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.text1.R;

/**
 * 功能描述：属性动画ValueAnimator、ObjectAnimator详解
 * Created by HM on 2016/5/25.
 */
public class TextValueAnimatorActivity extends Activity {

    private Button btn;
    private TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_valueanimator);

        btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //一、ObjectAnimator.ofFloat()方法的第二个参数传入的属性值：alpha、rotation、translationX和scaleY这几个值，分别可以完成淡入淡出、旋转、水平移动、垂直缩放这几种动画
                /*ObjectAnimator animator = ObjectAnimator.ofFloat(txt,"alpha",1f,0f,1f);
                ObjectAnimator animator2 = ObjectAnimator.ofFloat(txt,"rotation",0f,360f);
                ObjectAnimator animator3 = ObjectAnimator.ofFloat(txt,"translationX",txt.getTranslationX(),-500f,txt.getTranslationX());
                ObjectAnimator animator4 = ObjectAnimator.ofFloat(txt,"scaleY",1f,3f,1f);
                animator4.setDuration(2000);
                animator4.start();*/

                //二、组合动画：AnimatorSet
                /*ObjectAnimator moveIn = ObjectAnimator.ofFloat(txt,"translationX",-500f,txt.getTranslationX());
                ObjectAnimator rotate = ObjectAnimator.ofFloat(txt,"rotation",0f,360f);
                ObjectAnimator animatInOut = ObjectAnimator.ofFloat(txt,"alpha",1f,0f,1f);
//                AnimatorSet animatorSet = new AnimatorSet();
                //这三个动画对象进行播放排序：让旋转和淡入淡出动画同时进行，并把它们插入到了平移动画的后面
                animatorSet.play(rotate).with(animatInOut).after(moveIn);*/


                //三、Animator监听器：1、实例化一个AnimatorListener对象,分别在动画开始、结束、取消、重复时进行监听
               /* animatorSet.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        Toast.showToast(TextValueAnimatorActivity.this,"start");
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        Toast.showToast(TextValueAnimatorActivity.this,"End");
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        Toast.showToast(TextValueAnimatorActivity.this,"Cancel");
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {
                        Toast.showToast(TextValueAnimatorActivity.this,"Repeat");
                    }
                });*/

                //2、实例化一个AnimatorListenerAdapter对象，并只对其里面的onAnimationEnd（）方法进行监听
                /*animatorSet.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        Toast.showToast(TextValueAnimatorActivity.this,"Ending");
                    }
                });
                animatorSet.setDuration(5000);
                animatorSet.start();*/

                //四、XML动画的编写:<animator>:对应代码中的ValueAnimator,<objectAnimator>:对应代码中的ObjectAnimator,<set>:对应代码中的AnimatorSet
                Animator animator = AnimatorInflater.loadAnimator(TextValueAnimatorActivity.this,R.animator.anim_file2);
                animator.setTarget(txt);
                animator.start();

            }
        });
        txt = (TextView) findViewById(R.id.txt);

        //五、ValueAnimator
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0,100);
        valueAnimator.setDuration(300);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int currentAnima = (int) animation.getAnimatedValue();
                Log.e("currentAnima",currentAnima+"");
            }
        });
        valueAnimator.start();
    }
}
