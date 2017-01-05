package com.example.administrator.text1.ui.testTab;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.text1.R;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Created by hzhm on 2016/7/6.
 */
public class TestTab6 extends FragmentActivity implements View.OnClickListener {
    public static final String[] titles = new String[]{"头条","娱乐","视频","纪实"};

    private Indicator mIndicator;
    private ViewPager mViewPager;
    private FragmentPagerAdapter mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
        @Override
        public Fragment getItem(int position) {
            return new TabFragment(position);
        }

        @Override
        public int getCount() {
            return titles.length;
        }
    };

    private TextView txtTab1;
    private TextView txtTab2;
    private TextView txtTab3;
    private TextView txtTab4;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_tab_main6);

        initView();
    }

    private void initView() {
        mIndicator = (Indicator) findViewById(R.id.indicator);
        mViewPager = (ViewPager) findViewById(R.id.viewpage6);

        txtTab1 = (TextView) findViewById(R.id.tab_one);
        txtTab2 = (TextView) findViewById(R.id.tab_two);
        txtTab3 = (TextView) findViewById(R.id.tab_three);
        txtTab4 = (TextView) findViewById(R.id.tab_four);

        txtTab1.setOnClickListener(this);
        txtTab2.setOnClickListener(this);
        txtTab3.setOnClickListener(this);
        txtTab4.setOnClickListener(this);

        mViewPager.setAdapter(mAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //onPageScrolled：是指mViewPager正在滚动时，position是指当前视图，positionOffset：是指视图由刚开始到显示的一个梯度值（0-1）
                mIndicator.scroll(position,positionOffset);
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tab_one:
                mViewPager.setCurrentItem(0);
                break;

            case R.id.tab_two:
                mViewPager.setCurrentItem(1);
                break;

            case R.id.tab_three:
                mViewPager.setCurrentItem(2);
                break;

            case R.id.tab_four:
                mViewPager.setCurrentItem(3);
                break;
        }
    }

}
