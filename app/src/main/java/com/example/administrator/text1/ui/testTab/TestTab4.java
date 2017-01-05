package com.example.administrator.text1.ui.testTab;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.example.administrator.text1.R;
import com.viewpagerindicator.TabPageIndicator;

import java.util.List;

/**
 * 功能描述：多种方式实现App主菜单功能—Tab
 * 四：使用ViewPager + TabPageIndicator实现App主菜单功能—Tab
 * Created by hzhm on 2016/7/5.
 */
public class TestTab4 extends FragmentActivity {

    public static final String[] titles = new String[]{"头条","娱乐","视频","纪实","其他"};

    private ViewPager mViewPager;
    private TabPageIndicator mTabPageIndicator;
    private List<Fragment> mFragments;
    private FragmentPagerAdapter mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {


        @Override
        public Fragment getItem(int position) {
            TabFragment fragment = new TabFragment(position);
            return fragment;
        }

        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_tab_main4);

        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mTabPageIndicator = (TabPageIndicator) findViewById(R.id.indicator);
        mViewPager.setAdapter(mAdapter);

        //设置TabPageIndicator将其与ViewPager相关联
        mTabPageIndicator.setViewPager(mViewPager,0);
    }
}
