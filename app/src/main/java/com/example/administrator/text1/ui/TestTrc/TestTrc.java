package com.example.administrator.text1.ui.TestTrc;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.app.hubert.library.NewbieGuide;
import com.example.administrator.text1.R;
import com.example.administrator.text1.utils.view.ImgLoader;
import com.example.administrator.text1.utils.view.Indicator;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by hzhm on 2017/5/20.
 */

public class TestTrc extends AppCompatActivity implements View.OnClickListener{

    private ViewPager viewPager;
    private Indicator indicator;

    private View tvType1;
    private View tvType2;
    private View tvType3;
    private View tvType4;
    private View tvType5;
    private View tvType6;
    private View contentView;

    private String contentPage ="1";
    private TestTrcContentFragment testTrcContentFragment;
    private CircleAdsAdapter adsAdapter = new CircleAdsAdapter();
    private ArrayList<String> adsLists = new ArrayList<>();
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            handler.removeCallbacksAndMessages(null);
            if (adsLists != null && adsLists.size() > 1) {
                handler.postDelayed(this, 5000);
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_trc_main);

        initViewAndSetListener();
    }

    private void initViewAndSetListener(){
        viewPager = (ViewPager) findViewById(R.id.trcBannerViewPager);
        indicator = (Indicator) findViewById(R.id.trcBannerIndicator);
        tvType1 = findViewById(R.id.trcType1);
        tvType2 = findViewById(R.id.trcType2);
        tvType3 = findViewById(R.id.trcType3);
        tvType4 = findViewById(R.id.trcType4);
        tvType5 = findViewById(R.id.trcType5);
        tvType6 = findViewById(R.id.trcType6);
        contentView = findViewById(R.id.trcContent);
        tvType1.setOnClickListener(this);
        tvType2.setOnClickListener(this);
        tvType3.setOnClickListener(this);
        tvType4.setOnClickListener(this);
        tvType5.setOnClickListener(this);
        tvType6.setOnClickListener(this);

        adsLists.add("https://image.baidu.com/search/detail?ct=503316480&z=0&ipn=d&word=%E6%B8%85%E6%96%B0%E7%BE%8E%E5%A5%B3%E5%A3%81%E7%BA%B8&step_word=&hs=0&pn=5&spn=0&di=160163294720&pi=0&rn=1&tn=baiduimagedetail&is=0%2C0&istype=2&ie=utf-8&oe=utf-8&in=&cl=2&lm=-1&st=-1&cs=2642440577%2C1598399770&os=3685539047%2C1737803184&simid=3380811660%2C324314962&adpicid=0&lpn=0&ln=1954&fr=&fmq=1495268580949_R&fm=result&ic=0&s=undefined&se=&sme=&tab=0&width=&height=&face=undefined&ist=&jit=&cg=&bdtype=0&oriquery=&objurl=http%3A%2F%2Fwww.pp3.cn%2Fuploads%2F201602%2F20160201005.jpg&fromurl=ippr_z2C%24qAzdH3FAzdH3Fooo_z%26e3Brrn_z%26e3BvgAzdH3Fgextg2ktzitAzdH3Fn8lnm_d_z%26e3Bip4s&gsm=0&rpstart=0&rpnum=0");
        adsLists.add("https://image.baidu.com/search/detail?ct=503316480&z=0&ipn=d&word=%E6%B8%85%E6%96%B0%E7%BE%8E%E5%A5%B3%E5%A3%81%E7%BA%B8&step_word=&hs=0&pn=6&spn=0&di=18615189230&pi=0&rn=1&tn=baiduimagedetail&is=0%2C0&istype=2&ie=utf-8&oe=utf-8&in=&cl=2&lm=-1&st=-1&cs=429881344%2C645957751&os=1467010545%2C1451918161&simid=3364284187%2C340979602&adpicid=0&lpn=0&ln=1954&fr=&fmq=1495268580949_R&fm=result&ic=0&s=undefined&se=&sme=&tab=0&width=&height=&face=undefined&ist=&jit=&cg=&bdtype=0&oriquery=&objurl=http%3A%2F%2Fd.5857.com%2Fhh_160629%2F002.jpg&fromurl=ippr_z2C%24qAzdH3FAzdH3Fooo_z%26e3Bcbc0_z%26e3Bv54AzdH3F45ktsjAzdH3FowssAzdH3Fcbn80_d_z%26e3Bip4s&gsm=0&rpstart=0&rpnum=0");
        adsLists.add("https://image.baidu.com/search/detail?ct=503316480&z=0&ipn=d&word=%E6%B8%85%E6%96%B0%E7%BE%8E%E5%A5%B3%E5%A3%81%E7%BA%B8&step_word=&hs=0&pn=14&spn=0&di=200979598270&pi=0&rn=1&tn=baiduimagedetail&is=0%2C0&istype=2&ie=utf-8&oe=utf-8&in=&cl=2&lm=-1&st=-1&cs=3344694%2C3618111494&os=231468781%2C2881628293&simid=3406957431%2C187264899&adpicid=0&lpn=0&ln=1954&fr=&fmq=1495268580949_R&fm=result&ic=0&s=undefined&se=&sme=&tab=0&width=&height=&face=undefined&ist=&jit=&cg=&bdtype=0&oriquery=&objurl=http%3A%2F%2F4493bz.1985t.com%2Fuploads%2Fallimg%2F150922%2F1-150922161036.jpg&fromurl=ippr_z2C%24qAzdH3FAzdH3Fktzit_z%26e3B99ln_z%26e3Bv54AzdH3Ft4w2jAzdH3F8nl99_z%26e3Bip4s&gsm=0&rpstart=0&rpnum=0");
        adsLists.add("https://image.baidu.com/search/detail?ct=503316480&z=0&ipn=d&word=%E6%B8%85%E6%96%B0%E7%BE%8E%E5%A5%B3%E5%A3%81%E7%BA%B8&step_word=&hs=0&pn=46&spn=0&di=171677992530&pi=0&rn=1&tn=baiduimagedetail&is=0%2C0&istype=2&ie=utf-8&oe=utf-8&in=&cl=2&lm=-1&st=-1&cs=3627539553%2C3127069317&os=2310971449%2C2160153518&simid=3537459180%2C416584706&adpicid=0&lpn=0&ln=1954&fr=&fmq=1495268580949_R&fm=result&ic=0&s=undefined&se=&sme=&tab=0&width=&height=&face=undefined&ist=&jit=&cg=&bdtype=0&oriquery=&objurl=http%3A%2F%2Fh7.86.cc%2Fwalls%2F20160505%2F1440x900_6e02821f2eba885.jpg&fromurl=ippr_z2C%24qAzdH3FAzdH3Fooo_z%26e3Bxtwzwtzit3tw_z%26e3Bv54AzdH3FktzitAzdH3Fm88d8_z%26e3Bip4s&gsm=0&rpstart=0&rpnum=0");
        adsLists.add("https://image.baidu.com/search/detail?ct=503316480&z=0&ipn=d&word=%E6%B8%85%E6%96%B0%E7%BE%8E%E5%A5%B3%E5%A3%81%E7%BA%B8&step_word=&hs=0&pn=142&spn=0&di=51323377380&pi=0&rn=1&tn=baiduimagedetail&is=0%2C0&istype=2&ie=utf-8&oe=utf-8&in=&cl=2&lm=-1&st=-1&cs=1905010448%2C3400908435&os=3409418292%2C2947526016&simid=3537561023%2C328436049&adpicid=0&lpn=0&ln=1954&fr=&fmq=1495268580949_R&fm=result&ic=0&s=undefined&se=&sme=&tab=0&width=&height=&face=undefined&ist=&jit=&cg=&oriquery=&objurl=http%3A%2F%2Fh.hiphotos.baidu.com%2Fzhidao%2Fpic%2Fitem%2Fb21bb051f8198618b159588e49ed2e738bd4e69a.jpg&gsm=b4&rpstart=0&rpnum=0");
        adsLists.add("https://image.baidu.com/search/detail?ct=503316480&z=0&ipn=d&word=%E6%B8%85%E6%96%B0%E7%BE%8E%E5%A5%B3%E5%A3%81%E7%BA%B8&step_word=&hs=0&pn=160&spn=0&di=31564143160&pi=0&rn=1&tn=baiduimagedetail&is=0%2C0&istype=2&ie=utf-8&oe=utf-8&in=&cl=2&lm=-1&st=-1&cs=617122460%2C1265753772&os=2823017700%2C1133433137&simid=40597056%2C860537195&adpicid=0&lpn=0&ln=1954&fr=&fmq=1495268580949_R&fm=result&ic=0&s=undefined&se=&sme=&tab=0&width=&height=&face=undefined&ist=&jit=&cg=&bdtype=0&oriquery=&objurl=http%3A%2F%2Ftupian.enterdesk.com%2F2015%2Fxu%2F08%2F20%2F1%2F2.jpg&fromurl=ippr_z2C%24qAzdH3FAzdH3F44_z%26e3Bjgpj61jfh_z%26e3Bv54AzdH3FktzitAzdH3F8ccdc_z%26e3Bip4s&gsm=78&rpstart=0&rpnum=0");
        viewPager.setAdapter(adsAdapter);

        handler.postDelayed(runnable, 5000);
        indicator.setUpWithViewPager(viewPager);
        if (adsLists.size() > 1) {
            if (viewPager.getCurrentItem() == 0)
                viewPager.setCurrentItem(adsLists.size() * 1000, false);
            indicator.setVisibility(View.VISIBLE);
            indicator.setCount(adsLists.size());
        } else {
            indicator.setVisibility(View.GONE);
        }
        setContentPage(contentPage);

        NewbieGuide.with(this)
                .setLabel("testTrc")//设置标签，必传！否则报错
                .addHighLight(tvType1)//给相关视图设置高亮
                .setLayoutRes(R.layout.view_guide2)//设置相关引导视图
                .alwaysShow(true)//设置引导视图是否总是打开显示
                .show();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.trcType1){
            onClickChange(tvType1,true);
            onClickChange(tvType2,false);
            onClickChange(tvType3,false);
            onClickChange(tvType4,false);
            onClickChange(tvType5,false);
            onClickChange(tvType6,false);
            if(contentPage.equals("1")){
                return;
            }
            contentPage = "1";
        }else if(id == R.id.trcType2){
            onClickChange(tvType1,false);
            onClickChange(tvType2,true);
            onClickChange(tvType3,false);
            onClickChange(tvType4,false);
            onClickChange(tvType5,false);
            onClickChange(tvType6,false);
            if(contentPage.equals("2")){
                return;
            }
            contentPage = "2";
        }else if(id == R.id.trcType3){
            onClickChange(tvType1,false);
            onClickChange(tvType2,false);
            onClickChange(tvType3,true);
            onClickChange(tvType4,false);
            onClickChange(tvType5,false);
            onClickChange(tvType6,false);
            if(contentPage.equals("3")){
                return;
            }
            contentPage = "3";
        }else if(id == R.id.trcType4){
            onClickChange(tvType1,false);
            onClickChange(tvType2,false);
            onClickChange(tvType3,false);
            onClickChange(tvType4,true);
            onClickChange(tvType5,false);
            onClickChange(tvType6,false);
            if(contentPage.equals("4")){
                return;
            }
            contentPage = "4";
        }else if(id == R.id.trcType5){
            onClickChange(tvType1,false);
            onClickChange(tvType2,false);
            onClickChange(tvType3,false);
            onClickChange(tvType4,false);
            onClickChange(tvType5,true);
            onClickChange(tvType6,false);
            if(contentPage.equals("5")){
                return;
            }
            contentPage = "5";
        }else if(id == R.id.trcType6){
            onClickChange(tvType1,false);
            onClickChange(tvType2,false);
            onClickChange(tvType3,false);
            onClickChange(tvType4,false);
            onClickChange(tvType5,false);
            onClickChange(tvType6,true);
            if(contentPage.equals("6")){
                return;
            }
            contentPage = "6";
        }
        setContentPage(contentPage);
    }

    //按钮背景颜色改变的方法
    private void onClickChange(View rbt, boolean isClick) {
        if (isClick) {
            rbt.setBackgroundColor(Color.parseColor("#cccccc"));
        } else {
            rbt.setBackgroundColor(Color.parseColor("#666666"));
        }
    }

    public void setContentPage(String contentPage) {
        testTrcContentFragment = testTrcContentFragment.newInstance(contentPage);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction tf = fragmentManager.beginTransaction();
        tf.add(R.id.trcContent, testTrcContentFragment);
        tf.commit();
    }

    class CircleAdsAdapter extends PagerAdapter{
        private LinkedList<ImageView> viewCache = new LinkedList<>();

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
            viewCache.add((ImageView) object);
        }

        @Override
        public int getCount() {
            if(adsLists == null || adsLists.isEmpty()){
                return 0;
            }else if(adsLists.size() == 0){
                return 0;
            }else {
                return Integer.MAX_VALUE;
            }
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = viewCache.poll();
            if(imageView == null){
                imageView = new ImageView(getApplicationContext());
                imageView.setBackgroundResource(R.drawable.ad_default_jr);
                FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
                imageView.setLayoutParams(layoutParams);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            }
            int realIndex = position % adsLists.size();
            String imgUrl = adsLists.get(realIndex);
            ImgLoader.load(imgUrl, imageView);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
