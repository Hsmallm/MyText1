package com.example.administrator.text1.ui.testTab;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;

import com.example.administrator.text1.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能描述：多种方式实现App主菜单功能—Tab
 * 三：使用ViewPager(FragmentPagerAdapter) + “自定义底部主菜单栏”实现Tab主界面的布局显示
 * Created by hzhm on 2016/7/5.
 */
public class TestTab3 extends FragmentActivity implements View.OnClickListener {

    private ViewPager mViewPager;
    private List<Fragment> mFragments;
    private FragmentPagerAdapter mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }
    };

    private LinearLayout mTabWeiXin;
    private LinearLayout mTabFriend;
    private LinearLayout mTabAddress;
    private LinearLayout mTabSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_tab_main3);

        initView();
        initEvent();
        setSelected(0);
    }

    private void initView() {
        mTabWeiXin = (LinearLayout) findViewById(R.id.tab_weixin);
        mTabFriend = (LinearLayout) findViewById(R.id.tab_friend);
        mTabAddress = (LinearLayout) findViewById(R.id.tab_address);
        mTabSetting = (LinearLayout) findViewById(R.id.tab_setting);

        mViewPager = (ViewPager) findViewById(R.id.id_viewpage);
        mFragments = new ArrayList<Fragment>();
        Fragment mTab1 = new TestWeiXinFragment();
        Fragment mTab2 = new TestFriendFragment();
        Fragment mTab3 = new TestAddressFragment();
        Fragment mTab4 = new TestSettingFragment();
        mFragments.add(mTab1);
        mFragments.add(mTab2);
        mFragments.add(mTab3);
        mFragments.add(mTab4);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int current = mViewPager.getCurrentItem();
                setTab(current);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initEvent() {
        mTabWeiXin.setOnClickListener(this);
        mTabFriend.setOnClickListener(this);
        mTabAddress.setOnClickListener(this);
        mTabSetting.setOnClickListener(this);
    }

    /**
     * 1、设置切换的相应界面
     * 2、设置切换界面时，改变相应的菜单栏底部背景色
     * @param i
     */
    private void setSelected(int i) {
        setTab(i);
        mViewPager.setCurrentItem(i);
    }

    private void setTab(int i){
        switch (i) {
            case 0:
                mTabWeiXin.setBackgroundColor(Color.parseColor("#27a1e5"));

                mTabFriend.setBackgroundColor(Color.parseColor("#353535"));
                mTabAddress.setBackgroundColor(Color.parseColor("#353535"));
                mTabSetting.setBackgroundColor(Color.parseColor("#353535"));
                break;

            case 1:
                mTabFriend.setBackgroundColor(Color.parseColor("#27a1e5"));

                mTabWeiXin.setBackgroundColor(Color.parseColor("#353535"));
                mTabAddress.setBackgroundColor(Color.parseColor("#353535"));
                mTabSetting.setBackgroundColor(Color.parseColor("#353535"));
                break;

            case 2:
                mTabAddress.setBackgroundColor(Color.parseColor("#27a1e5"));

                mTabWeiXin.setBackgroundColor(Color.parseColor("#353535"));
                mTabFriend.setBackgroundColor(Color.parseColor("#353535"));
                mTabSetting.setBackgroundColor(Color.parseColor("#353535"));
                break;

            case 3:
                mTabSetting.setBackgroundColor(Color.parseColor("#27a1e5"));

                mTabWeiXin.setBackgroundColor(Color.parseColor("#353535"));
                mTabFriend.setBackgroundColor(Color.parseColor("#353535"));
                mTabAddress.setBackgroundColor(Color.parseColor("#353535"));
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tab_weixin:
                setSelected(0);
                break;

            case R.id.tab_friend:
                setSelected(1);
                break;

            case R.id.tab_address:
                setSelected(2);
                break;

            case R.id.tab_setting:
                setSelected(3);
                break;
        }
    }
}
