package com.example.administrator.text1.ui.testTab.testNewMainActivity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.example.administrator.text1.R;
import com.example.administrator.text1.ui.testTab.testTabLayout.TestChildFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hzhm on 2017/1/12.
 * <p>
 * 功能描述：主Tab页面新的实现方式:ViewPager+LinearLayout
 */
public class TestNewMainActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewPager viewPager;
    private LinearLayout tabs;
    private List<Fragment> lists;
    private View selectedTab;//（注：setDisSelectedTab：表示上一次选中Tab;setSelectedTab：表示这次选中Tab）

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trc_main);

        viewPager = (ViewPager) findViewById(R.id.main_viewpager);
        tabs = (LinearLayout) findViewById(R.id.tab_container);

        lists = new ArrayList<>();
        lists.add(TestChildFragment.newFragment(0));
        lists.add(TestChildFragment.newFragment(1));
        lists.add(TestChildFragment.newFragment(2));
        lists.add(TestChildFragment.newFragment(3));
        lists.add(TestChildFragment.newFragment(4));
        TestMainPageAdapter mainPageAdapter = new TestMainPageAdapter(getSupportFragmentManager(), lists);
        viewPager.setAdapter(mainPageAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }

            @Override
            public void onPageSelected(int position) {//页面选中后结果回调监听...
                //注：这里先得重新设置上一次选中得Tab为未选中状态；然后再设置当前选中Tab为选中状态...
                setDisSelectedTab();
                setSelectedTab(position);
            }
        });
        setSelectedTab(0);
    }

    /**
     * 设置选中Tab样式...(注：selectedTab代表这一次选中Tab)
     *
     * @param index
     */
    private void setSelectedTab(int index) {
        selectedTab = tabs.getChildAt(index);
        selectedTab.setSelected(true);
    }

    /**
     * 设置未选中Tab样式...（注：selectedTab代表上一次选中Tab）
     */
    private void setDisSelectedTab() {
        if (selectedTab != null) {
            selectedTab.setSelected(false);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getParent() == tabs) {
            int index = tabs.indexOfChild(v);
            viewPager.setCurrentItem(index, Math.abs(viewPager.getCurrentItem() - index) < 2);
        }
    }
}
