package com.example.administrator.text1.ui.testOther;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.example.administrator.text1.R;
import com.example.administrator.text1.adapter.CircleAdsAdapter;
import com.seaway.android.common.widget.circlevp.CircleIndicator;
import com.seaway.android.common.widget.circlevp.CircleViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HM on 2016/1/13.
 * 功能简介：
 * 实现图片轮播功能...
 */

public class TextCircleViewPager extends Activity {

    private CircleViewPager pager;
    private CircleIndicator indicator;
    private List<Drawable> list;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text3);
        pager = (CircleViewPager) findViewById(R.id.text3_page);
        indicator = (CircleIndicator) findViewById(R.id.text3_indicator);

        //初始化list
        list = new ArrayList<>();
        list.add(this.getDrawable(R.drawable.untitled));
        list.add(this.getDrawable(R.drawable.untitled2));
        list.add(this.getDrawable(R.drawable.ads));
        list.add(this.getDrawable(R.drawable.ad_default));
        //初始化adaper
        CircleAdsAdapter adapter = new CircleAdsAdapter(TextCircleViewPager.this,list);
        pager.setAdapter(adapter);
        if(list.size() > 1){
            //给图片设置图标
            indicator.setCircleViewPager(pager);
            //实现图片轮播改变监听
            pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    if(list != null && list.size() != 0){
                        //设置光标随着图片的改变而改变
                        indicator.setRealCurrentItem(position % list.size());
                        indicator.invalidate();
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });

            indicator.setVisibility(View.VISIBLE);
            //设置图片的自动轮播
            pager.startAutoScroll();
        }else {
            indicator.setVisibility(View.GONE);
        }
    }
}
