package com.example.administrator.text1.advanceAndroid.TestAnimation;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;

import com.example.administrator.text1.R;

/**
 * @author HuangMing on 2018/2/3.
 *         功能描述：Shared Element Transition：
 *         是分析A B界面共享view的尺寸，位置，样式的不同创建动画化的。
 *         所以前者通常设置Fade等Transition后者通常设置ChangeBounds等Transition.
 */

public class FragmentTransitionActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_transition);
        initToolbar();
        SmallIconFragment smallIconFragment = new SmallIconFragment();
        smallIconFragment.setExitTransition(new Slide());
        getSupportFragmentManager().beginTransaction().add(R.id.container_fragment, smallIconFragment).commit();
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
