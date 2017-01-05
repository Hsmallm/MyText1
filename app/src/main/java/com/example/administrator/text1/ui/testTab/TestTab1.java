package com.example.administrator.text1.ui.testTab;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.administrator.text1.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能描述：多种方式实现App主菜单功能—Tab
 * 一：使用ViewPager + “自定义底部主菜单栏”实现Tab主界面的布局显示
 * （注：刚开始点击ImageButton时，页面并没有跳转；当设置相应的ImageButton的clickable=false时，点击有效果）
 * Created by hzhm on 2016/7/4.
 */
public class TestTab1 extends Activity implements View.OnClickListener{

    private ViewPager viewPager;
    private PagerAdapter mAdapter = new PagerAdapter() {
        @Override
        public int getCount() {
            return views.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = views.get(position);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(views.get(position));
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    };
    private List<View> views = new ArrayList<View>();

    private LinearLayout mTabWeiXin;
    private LinearLayout mTabFriend;
    private LinearLayout mTabAddress;
    private LinearLayout mTabSetting;

    private ImageButton mTabWeiXinImg;
    private ImageButton mTabFriendImg;
    private ImageButton mTabAddressImg;
    private ImageButton mTabSettingImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_tab_main);

        initViewAndSetListener();

        initImageBtn();
    }

    private void initViewAndSetListener() {
        viewPager = (ViewPager) findViewById(R.id.viewpage);
        //tab
        mTabWeiXin = (LinearLayout) findViewById(R.id.tab_weixin);
        mTabFriend = (LinearLayout) findViewById(R.id.tab_friend);
        mTabAddress = (LinearLayout) findViewById(R.id.tab_address);
        mTabSetting = (LinearLayout) findViewById(R.id.tab_setting);

        mTabWeiXin.setOnClickListener(this);
        mTabFriend.setOnClickListener(this);
        mTabAddress.setOnClickListener(this);
        mTabSetting.setOnClickListener(this);

        //imageBtn
        mTabWeiXinImg = (ImageButton) findViewById(R.id.img_weixin);
        mTabFriendImg = (ImageButton) findViewById(R.id.img_friend);
        mTabAddressImg = (ImageButton) findViewById(R.id.img_address);
        mTabSettingImg = (ImageButton) findViewById(R.id.img_setting);


        LayoutInflater mInflater = LayoutInflater.from(this);
        final View view1 = mInflater.inflate(R.layout.activity_tab_view1,null);
        View view2 = mInflater.inflate(R.layout.activity_tab_view2,null);
        View view3 = mInflater.inflate(R.layout.activity_tab_view3,null);
        View view4 = mInflater.inflate(R.layout.activity_tab_view4,null);
        views.add(view1);
        views.add(view2);
        views.add(view3);
        views.add(view4);

        viewPager.setAdapter(mAdapter);
        //设置viewPager页面改变监听
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int current = viewPager.getCurrentItem();

                switch (current){
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
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 初始化底部菜单栏背景色
     */
    private void initImageBtn() {
        mTabWeiXin.setBackgroundColor(Color.parseColor("#27a1e5"));
        mTabFriend.setBackgroundColor(Color.parseColor("#353535"));
        mTabAddress.setBackgroundColor(Color.parseColor("#353535"));
        mTabSetting.setBackgroundColor(Color.parseColor("#353535"));
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.tab_weixin:
                viewPager.setCurrentItem(0);
                mTabWeiXin.setBackgroundColor(Color.parseColor("#27a1e5"));

                mTabFriend.setBackgroundColor(Color.parseColor("#353535"));
                mTabAddress.setBackgroundColor(Color.parseColor("#353535"));
                mTabSetting.setBackgroundColor(Color.parseColor("#353535"));
                break;
            case R.id.tab_friend:
                viewPager.setCurrentItem(1);
                mTabFriend.setBackgroundColor(Color.parseColor("#27a1e5"));

                mTabWeiXin.setBackgroundColor(Color.parseColor("#353535"));
                mTabAddress.setBackgroundColor(Color.parseColor("#353535"));
                mTabSetting.setBackgroundColor(Color.parseColor("#353535"));
                break;

            case R.id.tab_address:
                viewPager.setCurrentItem(2);
                mTabAddress.setBackgroundColor(Color.parseColor("#27a1e5"));

                mTabWeiXin.setBackgroundColor(Color.parseColor("#353535"));
                mTabFriend.setBackgroundColor(Color.parseColor("#353535"));
                mTabSetting.setBackgroundColor(Color.parseColor("#353535"));
                break;

            case R.id.tab_setting:
                viewPager.setCurrentItem(3);
                mTabSetting.setBackgroundColor(Color.parseColor("#27a1e5"));

                mTabWeiXin.setBackgroundColor(Color.parseColor("#353535"));
                mTabFriend.setBackgroundColor(Color.parseColor("#353535"));
                mTabAddress.setBackgroundColor(Color.parseColor("#353535"));
                break;
        }
    }
}
