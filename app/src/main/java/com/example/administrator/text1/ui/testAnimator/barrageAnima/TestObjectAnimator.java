package com.example.administrator.text1.ui.testAnimator.barrageAnima;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.text1.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hzhm on 2016/12/16.
 *
 * 功能描述：实现简单的文字循环滚动效果,即，弹幕滚动的动画效果...
 */

public class TestObjectAnimator extends Activity {

    private Button btn;
    private TextView txt;
    private LinearLayout rollView;
    private TextView rollTxt;

    private List<String> list;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_valueanimator);

        list = new ArrayList<>();
        list.add("13秒前，浙江 王先生1 ￥888888");
        list.add("13秒前，浙江 王先生2 ￥888888");
        list.add("13秒前，浙江 王先生3 ￥888888");
        list.add("13秒前，浙江 王先生4 ￥888888");
        list.add("13秒前，浙江 王先生5 ￥888888");
        list.add("13秒前，浙江 王先生6 ￥888888");

        txt = (TextView) findViewById(R.id.txt);
        rollView = (LinearLayout) findViewById(R.id.roll_view);
        rollTxt = (TextView) findViewById(R.id.roll_txt);
        rollTxt.setText("13秒前，浙江 王先生1 ￥888888");
        btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playAnimation();
            }
        });
    }

    /**
     * 播放动画
     */
    private void playAnimation() {
        //---动画一：实现向上移动并逐渐显示
        rollView.setVisibility(View.VISIBLE);
        final ObjectAnimator move = ObjectAnimator.ofFloat(rollView, "translationY", rollView.getTranslationY() + 50, rollView.getTranslationY());
        final ObjectAnimator animatInOut = ObjectAnimator.ofFloat(rollView, "alpha", 0f, 1f);

        AnimatorSet animationSet = new AnimatorSet();
        animationSet.play(move).with(animatInOut);
        animationSet.setDuration(500);
        animationSet.start();

        //---动画二：停顿一段时间，实现向上移动并逐渐消失
        //---(注：)
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ObjectAnimator move2 = ObjectAnimator.ofFloat(rollView, "translationY", rollView.getTranslationY(), rollView.getTranslationY() - 50, rollView.getTranslationY());
                ObjectAnimator animatInOut2 = ObjectAnimator.ofFloat(rollView, "alpha", 1f, 0f, 0f);

                AnimatorSet animationSet2 = new AnimatorSet();
                animationSet2.play(move2).with(animatInOut2);
                animationSet2.setDuration(1000);
                animationSet2.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    /**
                     * 实现循环滚动...
                     * @param animation
                     */
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        i++;
                        //如果滚动到最后一条，则重新由第一条开始滚动...
                        if (i >= list.size()) {
                            i = 0;
                        }
                        rollTxt.setText(list.get(i));
                        playAnimation();
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                animationSet2.start();
            }
        }, 2000);
    }
}
