package com.example.administrator.text1.advanceAndroid.TestAnimation;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.transition.Slide;
import android.view.Gravity;

import com.example.administrator.text1.R;

/**
 * @author HuangMing on 2018/2/3.
 *         功能描述：前面说了那么多终于到了重头戏了:Activity/Fragment之前的切换效果。
 *         界面切换有两种：
 *         一种是不带共享元素的Content Transition
 *         一种是带有共享元素的Shared Element Transition。
 *         界面切换动画是建立在visibility的改变的基础上的，所以getWindow().setEnterTransition(transition);
 *         中的参数一般传的是Fade,Slide,Explode类的实例（因为这三个类是通过分析visibility不同创建动画的）
 */

public class ContentTransitionsActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_transitions);
        initToolbar();

        Slide slide = new Slide();
        slide.setDuration(500);
        slide.setSlideEdge(Gravity.LEFT);
        getWindow().setEnterTransition(slide);
        getWindow().setReenterTransition(new Explode().setDuration(600));

    }


    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
