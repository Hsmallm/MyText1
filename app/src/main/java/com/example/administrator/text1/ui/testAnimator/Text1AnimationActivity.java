package com.example.administrator.text1.ui.testAnimator;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.administrator.text1.R;

/**
 * Created by HM on 2016/3/10.
 * 功能描述：ValueAnimator的高级用法，
 * 1、通过变换point点的坐标来实现圆的动态位置变化
 * 2、通过变换Radio半径的大小，来动态实现圆的大小变化
 * Android UI效果之- XML/animation-list小动画
 */
public class Text1AnimationActivity extends Activity{

    private ImageView img;
    private ImageView img2;

    private Button btn;
    private myView myView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_text1);

        myView = (myView) findViewById(R.id.myView);
        myView.postDelayed(new Runnable() {
            @Override
            public void run() {
                myView.endAnimation();
            }
        },3000);
        btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myView.setAnimator(true);
//                myView.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        myView.endAnimation();
//                    }
//                },3000);
            }
        });



//        img = (ImageView) findViewById(R.id.img);
//        img2 = (ImageView) findViewById(R.id.img2);
//
//
//        AnimationDrawable drawable = (AnimationDrawable) img.getDrawable();
//        drawable.start();
//
//        AnimationDrawable drawable2 = (AnimationDrawable) img2.getBackground();
//        drawable2.start();
    }
}
