package com.example.administrator.text1.ui.testTab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.example.administrator.text1.R;
import com.google.android.gms.common.api.GoogleApiClient;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 功能描述：自定义指示器，实现多Tab项时Tab也跟随ViewPager滚动的效果
 * Created by hzhm on 2016/7/7.
 */
public class TestTab7 extends FragmentActivity{

    private static final List<String> mTitle = Arrays.asList("短信1", "收藏2", "推荐3","短信4", "收藏5", "推荐6","短信7", "收藏8", "推荐9");
    private List<TestFragment> mContent = new ArrayList<TestFragment>();

    private TestIndicator mTestIndicator;
    private ViewPager mViewPager;
    private FragmentPagerAdapter mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
        @Override
        public Fragment getItem(int position) {
            return mContent.get(position);
        }

        @Override
        public int getCount() {
            return mContent.size();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_tab_main7);

        initView();
        initData();

        //动态设置Tab指示器的显示TextView个数及总的TextView数码
        mTestIndicator.setVisibleCount(3);
        mTestIndicator.setTitles(mTitle,mViewPager);
        mTestIndicator.setLightTextColor(0);

        mViewPager.setAdapter(mAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //当切换ViewPager页面时动态绘制三角区域（）
                mTestIndicator.scrollTriangleX(position,positionOffset);
            }

            @Override
            public void onPageSelected(int position) {
                //设置被选中的tab文本颜色
                mTestIndicator.setLightTextColor(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    private void initView() {
        mTestIndicator = (TestIndicator) findViewById(R.id.testindicator);
        mViewPager = (ViewPager) findViewById(R.id.viewpage7);
    }

    /**
     * 通过mTitle动态生成Fragment
     */
    private void initData() {
        for (String title : mTitle) {
            mContent.add(TestFragment.newIntent(title));
        }
    }
}
