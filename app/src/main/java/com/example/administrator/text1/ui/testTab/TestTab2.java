package com.example.administrator.text1.ui.testTab;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.LinearLayout;

import com.example.administrator.text1.R;

/**
 * 功能描述：多种方式实现App主菜单功能—Tab
 * 二：使用FrameLayout + “自定义底部主菜单栏”实现Tab主界面的布局显示
 * Created by hzhm on 2016/7/5.
 */
public class TestTab2 extends FragmentActivity implements View.OnClickListener{

    private LinearLayout mTabWeiXin;
    private LinearLayout mTabFriend;
    private LinearLayout mTabAddress;
    private LinearLayout mTabSetting;

    private Fragment mTabWeiXinFrg;
    private Fragment mTabFriendFrg;
    private Fragment mTabAddressFrg;
    private Fragment mTabSettingFrg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_tab_main2);

        initView();
        initEvent();
        setSelected(0);
    }

    private void initView() {
        mTabWeiXin = (LinearLayout) findViewById(R.id.tab_weixin);
        mTabFriend = (LinearLayout) findViewById(R.id.tab_friend);
        mTabAddress = (LinearLayout) findViewById(R.id.tab_address);
        mTabSetting = (LinearLayout) findViewById(R.id.tab_setting);
    }

    private void initEvent() {
        mTabWeiXin.setOnClickListener(this);
        mTabFriend.setOnClickListener(this);
        mTabAddress.setOnClickListener(this);
        mTabSetting.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
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

    /**
     * 设置被选中Tab状态
     * 1、显示相应的fragment
     * 2、设置底部菜单相应的背景色
     * @param i
     */
    private void setSelected(int i){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        hideAllFragment(ft);
        switch (i){
            case 0:
                if(mTabWeiXinFrg == null){
                    mTabWeiXinFrg = new TestWeiXinFragment();
                    ft.add(R.id.id_content,mTabWeiXinFrg);
                }else {
                    ft.show(mTabWeiXinFrg);
                }

                mTabWeiXin.setBackgroundColor(Color.parseColor("#27a1e5"));

                mTabFriend.setBackgroundColor(Color.parseColor("#353535"));
                mTabAddress.setBackgroundColor(Color.parseColor("#353535"));
                mTabSetting.setBackgroundColor(Color.parseColor("#353535"));
                break;

            case 1:
                if(mTabFriendFrg == null){
                    mTabFriendFrg = new TestFriendFragment();
                    ft.add(R.id.id_content,mTabFriendFrg);
                }else {
                    ft.show(mTabFriendFrg);
                }

                mTabFriend.setBackgroundColor(Color.parseColor("#27a1e5"));

                mTabWeiXin.setBackgroundColor(Color.parseColor("#353535"));
                mTabAddress.setBackgroundColor(Color.parseColor("#353535"));
                mTabSetting.setBackgroundColor(Color.parseColor("#353535"));
                break;

            case 2:
                if(mTabAddressFrg == null){
                    mTabAddressFrg = new TestAddressFragment();
                    ft.add(R.id.id_content,mTabAddressFrg);
                }else {
                    ft.show(mTabAddressFrg);
                }

                mTabAddress.setBackgroundColor(Color.parseColor("#27a1e5"));

                mTabWeiXin.setBackgroundColor(Color.parseColor("#353535"));
                mTabFriend.setBackgroundColor(Color.parseColor("#353535"));
                mTabSetting.setBackgroundColor(Color.parseColor("#353535"));
                break;

            case 3:
                if(mTabSettingFrg == null){
                    mTabSettingFrg = new TestSettingFragment();
                    ft.add(R.id.id_content,mTabSettingFrg);
                }else {
                    ft.show(mTabSettingFrg);
                }

                mTabSetting.setBackgroundColor(Color.parseColor("#27a1e5"));

                mTabWeiXin.setBackgroundColor(Color.parseColor("#353535"));
                mTabFriend.setBackgroundColor(Color.parseColor("#353535"));
                mTabAddress.setBackgroundColor(Color.parseColor("#353535"));
                break;
        }
        ft.commit();
    }

    private void hideAllFragment(FragmentTransaction ft) {
        if(mTabWeiXinFrg != null){
            ft.hide(mTabWeiXinFrg);
        }
        if(mTabFriendFrg != null){
            ft.hide(mTabFriendFrg);
        }
        if(mTabAddressFrg != null){
            ft.hide(mTabAddressFrg);
        }
        if(mTabSettingFrg != null){
            ft.hide(mTabSettingFrg);
        }
    }
}
