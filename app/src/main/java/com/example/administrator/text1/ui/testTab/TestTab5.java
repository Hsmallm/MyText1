package com.example.administrator.text1.ui.testTab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.example.administrator.text1.R;

/**
 * 功能描述：多种方式实现App主菜单功能—Tab
 * 五：使用ViewPager + TabLayout实现(注：这里使用的TabLayout需要引用第三方开源库)
 * Created by hzhm on 2016/7/6.
 */
public class TestTab5 extends FragmentActivity {

    public static final String[] titles = new String[]{"头条","娱乐","视频","纪实","其他","电视","电影","短片","美女"};

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private FragmentPagerAdapter mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
        @Override
        public Fragment getItem(int position) {
            Fragment fragment = new TabFragment(position);
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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_tab_main5);

        mTabLayout = (TabLayout) findViewById(R.id.tablayout);
        mViewPager = (ViewPager) findViewById(R.id.viewpage5);

        mViewPager.setAdapter(mAdapter);
        //设置Viewpager页面改变监听，将其与TabLayout进行关联
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        //设置TabLayout选择改变监听,将其与ViewPager进行关联
        mTabLayout.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager){
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //当切换相间的tab数小于等于1时，才给它切换效果，否则不要设置切换效果
                mViewPager.setCurrentItem(tab.getPosition(), Math.abs(tab.getPosition() - mViewPager.getCurrentItem()) == 1);
            }
        });

        //给TabLayout控件设置适配
        mTabLayout.setTabsFromPagerAdapter(mViewPager.getAdapter());
    }
}
