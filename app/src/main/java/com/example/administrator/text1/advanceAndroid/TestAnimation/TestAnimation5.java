package com.example.administrator.text1.advanceAndroid.TestAnimation;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.administrator.text1.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author HuangMing on 2018/2/3.
 *         功能描述：beginDelayedTransition()：原理则是通过代码改变view的属性，然后通过之前介绍的ChangeBounds等类分析start scene和end Scene不同来创建动画。
 */

public class TestAnimation5 extends AppCompatActivity implements View.OnClickListener {

    private CircleImageView cuteboy, cutegirl, hxy, lly;
    private Button btn;
    private Button btn2;
    private boolean isImageBigger;
    private ViewGroup sceneRoot;
    private int primarySize;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_begindelayed_transition);
        //页面退出时的动画设置
        getWindow().setExitTransition(TransitionInflater.from(this).inflateTransition(R.transition.slide));
        initView();
    }

    private void initView() {
        sceneRoot = (ViewGroup) findViewById(R.id.scene_root);
        cuteboy = (CircleImageView) findViewById(R.id.cuteboy);
        cutegirl = (CircleImageView) findViewById(R.id.cutegirl);
        hxy = (CircleImageView) findViewById(R.id.hxy);
        lly = (CircleImageView) findViewById(R.id.lly);
        btn = (Button) findViewById(R.id.begin);
        btn2 = (Button) findViewById(R.id.begin2);
        btn.setOnClickListener(this);
        btn2.setOnClickListener(this);
        primarySize = cuteboy.getLayoutParams().width;
        cuteboy.setOnClickListener(this);
        cutegirl.setOnClickListener(this);
        hxy.setOnClickListener(this);
        lly.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.begin){
            Intent intent = new Intent(this, ContentTransitionsActivity.class);
            ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this);
            startActivity(intent,activityOptionsCompat.toBundle());
            return;
        }
        if (id == R.id.begin2){
            startActivity(new Intent(this,FragmentTransitionActivity.class));
        }
        TransitionManager.beginDelayedTransition(sceneRoot, TransitionInflater.from(this).inflateTransition(R.transition.explode_and_changebounds));
        changeScene(v);
    }

    private void changeScene(View view) {
        changeSize(view);
        changeVisibility(cuteboy, cutegirl, hxy, lly);
        view.setVisibility(View.VISIBLE);
    }

    /**
     * view的宽高1.5倍和原尺寸大小切换
     * 配合ChangeBounds实现缩放效果
     *
     * @param view
     */
    private void changeSize(View view) {
        isImageBigger = !isImageBigger;
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (isImageBigger) {
            layoutParams.width = (int) (1.5 * primarySize);
            layoutParams.height = (int) (1.5 * primarySize);
        } else {
            layoutParams.width = primarySize;
            layoutParams.height = primarySize;
        }
        view.setLayoutParams(layoutParams);
    }

    /**
     * VISIBLE和INVISIBLE状态切换
     *
     * @param views
     */
    private void changeVisibility(View... views) {
        for (View view : views) {
            view.setVisibility(view.getVisibility() == View.VISIBLE ? View.INVISIBLE : View.VISIBLE);
        }
    }
}
