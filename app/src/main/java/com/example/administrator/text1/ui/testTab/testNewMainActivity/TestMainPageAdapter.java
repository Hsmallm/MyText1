package com.example.administrator.text1.ui.testTab.testNewMainActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by hzhm on 2017/1/12.
 */

public class TestMainPageAdapter extends FragmentPagerAdapter {

    private List<Fragment> lists;

    public TestMainPageAdapter(FragmentManager fm, List<Fragment> lists) {
        super(fm);
        this.lists = lists;
    }

    @Override
    public Fragment getItem(int position) {
        return lists == null ? null : lists.get(position);
    }

    @Override
    public int getCount() {
        return lists == null ? null : lists.size();
    }
}
