package com.example.administrator.text1.ui.testAnimator.testNewAnima;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.transition.Fade;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.text1.R;

/**
 * Created by hzhm on 2017/2/21.
 */

public class TestNewAnima2 extends Activity {

    private TextView txt;
    private Button btn;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_new_anima);
        //1、分解动画
//        getWindow().setEnterTransition(new Explode().setDuration(2000));
//        getWindow().setExitTransition(new Explode().setDuration(2000));
        //2、滑动进入
//        getWindow().setEnterTransition(new Slide().setDuration(2000));
//        getWindow().setExitTransition(new Slide().setDuration(2000));
        //3、淡入淡出
        getWindow().setEnterTransition(new Fade().setDuration(2000));
        getWindow().setExitTransition(new Fade().setDuration(2000));

        txt = (TextView) findViewById(R.id.anima_txt);
        txt.setText("TestNewAnima2");
        txt.setBackgroundColor(getResources().getColor(R.color.blue));
    }
}
