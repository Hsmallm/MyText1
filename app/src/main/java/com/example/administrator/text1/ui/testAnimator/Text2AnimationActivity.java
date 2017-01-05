package com.example.administrator.text1.ui.testAnimator;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;

import com.example.administrator.text1.R;
import com.example.administrator.text1.ui.testframelayout.TextCircleAnimationFrame;

/**
 * 功能描述：
 * Android UI效果之- Animation/动画，android中提供了4种补间动画：
 * 1.TranslateAnimation 位移动画效果
 * 2.AlphaAnimation 透明度动画效果
 * 3.ScaleAnimation 缩放动画效果
 * 4.RotateAnimation 旋转动画效果
 * <p/>
 * * Created by HM on 2016/3/10.
 */
public class Text2AnimationActivity extends Activity {

    private Button btnStart;
    private Button btnEnd;
    private ImageView image;
    private TranslateAnimation translateAnimation;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_text2);
        btnStart = (Button) findViewById(R.id.start);
        btnEnd = (Button) findViewById(R.id.end);
        image = (ImageView) findViewById(R.id.image);

        //1.TranslateAnimation 位移动画效果
        /*
        float fromXDelta 动画开始的点离当前View X坐标上的差值
        float toXDelta 动画结束的点离当前View X坐标上的差值
        float fromYDelta 动画开始的点离当前View Y坐标上的差值
        float toYDelta 动画开始的点离当前View Y坐标上的差值
         */
        translateAnimation = new TranslateAnimation(0,200,0,0);
        translateAnimation.setDuration(200);
        translateAnimation.setRepeatCount(1);
        translateAnimation.setFillAfter(true);
        translateAnimation.setRepeatMode(Animation.REVERSE);
        image.startAnimation(translateAnimation);

        //2.AlphaAnimation 透明度动画效果
//        final AlphaAnimation alphaAnimation = new AlphaAnimation(1,0);//(0,1)表示从无到有；(1,0)表示从有到无
//        alphaAnimation.setDuration(2000);//动画持续时长
////        alphaAnimation.setRepeatCount(2);//动画的重复次数
//        alphaAnimation.setRepeatMode(Animation.REVERSE);//设置为反方向执行
//        alphaAnimation.setStartOffset(1000);//动画执行前的等待时间
//        alphaAnimation.setFillAfter(true);//动画执行完后是否停留在执行完的状态
//        image.setAnimation(alphaAnimation);

        //3.ScaleAnimation 缩放动画效果
        /*
        float fromX 动画起始时 X坐标上的伸缩尺寸
        float toX 动画结束时 X坐标上的伸缩尺寸
        float fromY 动画起始时Y坐标上的伸缩尺寸
        float toY 动画结束时Y坐标上的伸缩尺寸
        int pivotXType 动画在X轴相对于物件位置类型
        float pivotXValue 动画相对于物件的X坐标的开始位置
        int pivotYType 动画在Y轴相对于物件位置类型
        float pivotYValue 动画相对于物件的Y坐标的开始位置
                                  //（注：0.0f：表示动画开始是的放大大小;1.4f:表示动画结束时的放大大小Animation.RELATIVE_TO_SEL:表示他的动画模式
                                                       // 一般这两个参数配合使用才能达到围绕这图像中心点旋转的效果）*/
//        final ScaleAnimation scaleAnimation = new ScaleAnimation(0.0f, 10f, 0.0f, 10f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
//        scaleAnimation.setDuration(500);
//        scaleAnimation.setFillAfter(true);
//        image.setAnimation(scaleAnimation);

        //4.RotateAnimation 旋转动画效果
         /*
        float fromDegrees：旋转的开始角度。
        float toDegrees：旋转的结束角度。
        int pivotXType：X轴的伸缩模式，可以取值为ABSOLUTE、RELATIVE_TO_SELF、RELATIVE_TO_PARENT。
        float pivotXValue：X坐标的伸缩值。
        int pivotYType：Y轴的伸缩模式，可以取值为ABSOLUTE、RELATIVE_TO_SELF、RELATIVE_TO_PARENT。
        float pivotYValue：Y坐标的伸缩值。
                                                         //(注：0.5f表示：在x,y轴上图形的旋转的中心点即为图像的中心;Animation.RELATIVE_TO_SEL:表示他的动画模式
        *                                                // 一般这两个参数配合使用才能达到围绕这图像中心点缩放的效果)*/
//        final RotateAnimation rotateAnimation = new RotateAnimation(0f,180f, Animation.RELATIVE_TO_SELF, 0.5f,Animation.RELATIVE_TO_SELF,0.5f);
//        rotateAnimation.setDuration(2000);
//        rotateAnimation.setFillAfter(true);
//        image.setAnimation(rotateAnimation);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image.setVisibility(View.VISIBLE);
                final ScaleAnimation scaleAnimation = new ScaleAnimation(0.0f, 10f, 0.0f, 10f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                scaleAnimation.setDuration(500);
                scaleAnimation.setFillAfter(true);
                image.setAnimation(scaleAnimation);
                image.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        image.setVisibility(View.GONE);
                        final ScaleAnimation scaleAnimation2 = new ScaleAnimation(10f, 0.0f, 10f, 0.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                        scaleAnimation2.setDuration(500);
                        scaleAnimation2.setFillAfter(true);
                        image.setAnimation(scaleAnimation2);
                    }
                },1000);
            }
        });

        btnEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //实例化一个TextCircleAnimationFrame对象（通过LayoutInflater）
                final TextCircleAnimationFrame textCircleAnimationFrame = (TextCircleAnimationFrame) LayoutInflater.from(Text2AnimationActivity.this).inflate(R.layout.activity_text_framelayout,null);
                //再将实例化的view视图对象添加到当前视图的父类容器中
                Text2AnimationActivity.this.getWindow().addContentView(textCircleAnimationFrame, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

                //初始化View对象，并给他设置旋转动画
                View view = textCircleAnimationFrame.findViewById(R.id.rotate);
                final RotateAnimation rotateAnimation = new RotateAnimation(0,360,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
                rotateAnimation.setDuration(10000);
                //setInterpolator():设置动画的速率
                rotateAnimation.setInterpolator(new LinearInterpolator());
                //setRepeatCount()：设置重复次数
                rotateAnimation.setRepeatCount(Animation.ABSOLUTE);
                view.startAnimation(rotateAnimation);

                //再给整个TextCircleAnimationFrame对象设置一个缩放动画
                final ScaleAnimation scaleAnimation2 = new ScaleAnimation(0.0f, 1f, 0.0f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                scaleAnimation2.setDuration(300);
                //假定你有一个移动的动画紧跟一个淡出的动画，如果你不把移动的动画的setFillAfter置为true，那么移动动画结束后，View会回到原来的位置淡出，如果setFillAfter置为true， 就会在移动动画结束的位置淡出
                scaleAnimation2.setFillAfter(true);
                textCircleAnimationFrame.setAnimation(scaleAnimation2);
//                final int[] location = new int[2];
//                textCircleAnimationFrame.getLocationInWindow(location);
//                final int locationX = location[0] + textCircleAnimationFrame.getMeasuredWidth() / 2;
//                final int locationY = location[1] + textCircleAnimationFrame.getMeasuredHeight() / 2;
//                textCircleAnimationFrame.computCircleInfo(true,locationX,locationY);

                //然后在给textCircleAnimationFrame设置一个延时消失
                textCircleAnimationFrame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        textCircleAnimationFrame.setVisibility(View.GONE);
                        final ScaleAnimation scaleAnimation3 = new ScaleAnimation(1f, 0.0f, 1f, 0.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                        scaleAnimation3.setDuration(500);
                        scaleAnimation3.setFillAfter(true);
                        textCircleAnimationFrame.setAnimation(scaleAnimation3);
                    }
                },1000);
            }
        });
    }
}
