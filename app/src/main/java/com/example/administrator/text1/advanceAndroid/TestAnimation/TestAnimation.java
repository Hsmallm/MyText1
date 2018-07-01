package com.example.administrator.text1.advanceAndroid.TestAnimation;

import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.administrator.text1.R;

/**
 * @author HuangMing on 2018/2/1.
 *         功能描述：Android 动画机制
 *         相关简介：
 *         1、Android3.0之前：逐帧动画、补间动画
 *         2、Android3.0：属性动画
 *         3、Android4.0：android.transition框架
 *
 *         逐帧动画：Frame Animation也叫做Drawable Animations,就是开发人员设置每一帧对应的图片和持续时间，然后设置动画开始
 */

public class TestAnimation extends AppCompatActivity {

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
        //1.1：补间动画实现方式之：自定义Xml文件设置到ImageView的src属性中
        final AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getDrawable();
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animationDrawable.start();
            }
        });
        endBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animationDrawable.stop();
            }
        });
        createAnimation();
    }

    //1.2：补间动画实现方式之：代码方式实习
    private void createAnimation() {
        AnimationDrawable animationDrawable = new AnimationDrawable();
        Drawable drawable = null;
        for (int i = 0; i < 6; i++) {
            if (i % 2 == 0) {
                drawable = getResources().getDrawable(R.drawable.active_arrow2);
            } else {
                drawable = getResources().getDrawable(R.drawable.active_arrow);
            }
            animationDrawable.addFrame(drawable, 500);
        }
        imageView.setBackgroundDrawable(animationDrawable);
        animationDrawable.setOneShot(false);
        animationDrawable.start();
    }
}
