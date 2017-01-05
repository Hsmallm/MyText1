package com.example.administrator.text1.ui.testTab.testTabLayout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.example.administrator.text1.R;
import com.example.administrator.text1.utils.ObjCacheUtil;

import java.util.ArrayList;

/**
 * Created by hzhm on 2016/12/29.
 * 功能描述：TabLayout实现主页面切换的相关使用技巧
 * 1、tabLayout.setupWithViewPager(viewPager)：在给tabLayout设置与viewPager相关联时setupWithViewPager(viewPager)与下面三个方法效果一样
 * 2、如果两个切换的Tab之间正好相邻，则返回true显示切换动画；否则返回false,不显示切换动画...
 * viewPager.setCurrentItem(tab.getPosition(), Math.abs(tab.getPosition() - viewPager.getCurrentItem()) == 1);
 */

public class TestTabLayoutFragment extends Fragment {

    private View img;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ArrayList<String> mList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_test_tablayout, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mList = new ArrayList<>();
        ObjCacheUtil.getAsync(new ObjCacheUtil.Callback<ArrayList<String>>() {
            @Override
            public void onResult(ArrayList<String> strings) {
                if(strings != null){
                    mList = strings;
                }
            }
        },"mList");
        if(mList == null || mList.size() == 0){
            for (int i= 0; i< 10; i++){
                mList.add("泰然金融" + i);
            };
        }
        img = getActivity().findViewById(R.id.test_img);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TestRankFragment fragment = new TestRankFragment();
                fragment.setOnResultListener(new TestRankFragment.OnResultListener() {
                    @Override
                    public void onResult(int position, ArrayList<String> Lists) {
                        mList = Lists;
                        //获取相应的mList数据，重新设置viewPager
                        setUpView();
                        //然后再跳转到指定的Tab界面...
                        viewPager.setCurrentItem(position);
                    }
                });
                addFragmentNeedToStack(fragment);
            }
        });
        tabLayout = (TabLayout) getActivity().findViewById(R.id.test_tablayout);
        viewPager = (ViewPager) getActivity().findViewById(R.id.test_viewpager);
        setUpView();
    }

    private void setUpView(){
        viewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return TestChildFragment.newFragment(position + 1);
            }

            @Override
            public int getCount() {
                return mList.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mList.get(position);
            }
        });
        //注：在给tabLayout设置与viewPager相关联时setupWithViewPager(viewPager)与下面三个方法效果一样
//        tabLayout.setupWithViewPager(viewPager);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager) {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //*** 如果两个切换的Tab之间正好相邻，则返回true显示切换动画；否则返回false,不显示切换动画...
                viewPager.setCurrentItem(tab.getPosition(), Math.abs(tab.getPosition() - viewPager.getCurrentItem()) == 1);
            }
        });
        tabLayout.setTabsFromPagerAdapter(viewPager.getAdapter());
    }

    /**
     * fragment跳转,需要进栈
     *
     * @param frg
     */
    protected void addFragmentNeedToStack(Fragment frg) {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction tfm = fm.beginTransaction();
        tfm.add(Window.ID_ANDROID_CONTENT, frg, frg.getClass().getName());
        tfm.addToBackStack(frg.getClass().getName());
        tfm.commitAllowingStateLoss();
    }
}
