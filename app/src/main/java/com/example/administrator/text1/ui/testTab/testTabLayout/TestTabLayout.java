package com.example.administrator.text1.ui.testTab.testTabLayout;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

/**
 * Created by hzhm on 2016/12/29.
 * 功能描述：实现Tab主页与Tab排序页面的切换（注：由Tab排序页面，直接点击相应的item,回到相应的Tab主页的Tab项目）
 */

public class TestTabLayout extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        replaceFragment(new TestTabLayoutFragment());
    }

    /**
     * fragment跳转,不需进栈
     *
     * @param fragment
     */
    protected void replaceFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        if (null != fm.getFragments() && fm.getFragments().contains(fragment)) return;
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(Window.ID_ANDROID_CONTENT, fragment, fragment.getClass().getName());
        ft.commitAllowingStateLoss();
    }
}
