package com.example.administrator.text1.ui.testViewPager;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;

import com.example.administrator.text1.R;

/**
 * Created by hzhm on 2016/12/1.
 * 功能描述：TabLayout+viewPager实现主界面布局的相关技术坑点？？？
 * 坑点1：在给viewPager设置Adapter时，
 *        A、如果当前类是Activity时，该类必须为AppCompatActivity，且在new FragmentPagerAdapter()时，
 *           里面应该给getSupportFragmentManager参数(这里面是Activity里面嵌套Fragment)；
 *        B、如果当前类是Fragment时，该类必须为v4.app.Fragment，且在new FragmentPagerAdapter()时，
 *           里面应该给getChildFragmentManager参数(这里面是Fragment里面嵌套Fragment)；
 * 坑点2：在给tabLayout设置与viewPager相关联时setupWithViewPager(viewPager)与下面三个方法效果一样，查看setupWithViewPager()源码，里面正好也是这三个方法
 */

public class TestViewPager extends AppCompatActivity {

    private ManageViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_viewpager);

        viewPager = (ManageViewPager) findViewById(R.id.fragment_view_pager);
        viewPager.setNoScroll(true);//禁止侧滑
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                if(position == 0){
                    return new TestFrg1();
                }else {
                    return new TestFrg1();
                }
            }

            @Override
            public int getCount() {
                return 2;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                if(position == 0){
                    return "Tab1";
                }else {
                    return "Tab2";
                }
            }
        });
        tabLayout = (TabLayout) findViewById(R.id.memberInfoTabLayout);
        //---在给tabLayout设置与viewPager相关联时setupWithViewPager(viewPager)与下面三个方法效果一样，查看setupWithViewPager()源码，里面正好也是这三个方法
        tabLayout.setupWithViewPager(viewPager);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager){

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition(), Math.abs(tab.getPosition() - viewPager.getCurrentItem()) == 1);
            }
        });
        tabLayout.setTabsFromPagerAdapter(viewPager.getAdapter());

    }
}
