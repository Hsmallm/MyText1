//package com.example.administrator.text1.ui.textSlide.upDownSlidPage;
//
//import android.animation.ArgbEvaluator;
//import android.os.Bundle;
//import android.support.v4.app.FragmentActivity;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentTransaction;
//import android.view.View;
//import android.widget.FrameLayout;
//import android.widget.TextView;
//
//import com.example.administrator.text1.R;
//import com.example.administrator.text1.ui.testSlide.upDownSlidPage.firstFragment;
//import com.example.administrator.text1.utils.view.JdNestedScrollView;
//
///**
// * 功能描述：实现上下滑动页面切换的效果
// * 主要技术要点：
// * 1、JdNestedScrollView:这个类为上下滑动页面切换帮助类
// * 2、ArgbEvaluator：用于根据一个起始颜色值和一个结束颜色值以及一个偏移量生成一个新的颜色，分分钟实现类似于微信底部栏滑动颜色渐变
// * Created by HM on 2016/5/30.
// */
//public class UpDownSlidePageActivity extends FragmentActivity {
//
//    private int startTitleBgColor;
//    private int endTitleBgColor;
//    private int startTitleTxtColor;
//    private int endTitleTxtColor;
//
//    //用于根据一个起始颜色值和一个结束颜色值以及一个偏移量生成一个新的颜色，分分钟实现类似于微信底部栏滑动颜色渐变
//    private ArgbEvaluator argbEvaluator;
//    //JdNestedScrollView:这个类为上下滑动页面切换帮助类
//    private JdNestedScrollView jdNestedScrollView;
//    private TextView title;
//    private TextView bottom;
//    private FrameLayout frameLayout1;
//    private FrameLayout frameLayout2;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_text_updownslidepage);
//
//        title = (TextView) findViewById(R.id.title);
//        bottom = (TextView) findViewById(R.id.bottom);
//        startTitleBgColor = getResources().getColor(R.color.blue);
//        endTitleBgColor = getResources().getColor(R.color.frgBgColor);
//        startTitleTxtColor = getResources().getColor(R.color.white);
//        endTitleTxtColor  = getResources().getColor(R.color.black_txt);
//
//        argbEvaluator = new ArgbEvaluator();
//
//        jdNestedScrollView = (JdNestedScrollView) findViewById(R.id.jdscrollView);
//        jdNestedScrollView.setOnPageSelectedListener(new JdNestedScrollView.OnPageSelectedListener() {
//            @Override
//            public void onScrollToFirstPage() {
//                setFirstPage();
//            }
//
//            @Override
//            public void onScrollToSecondPage() {
//                setSecondPage();
//            }
//
//            //更新切换页面时相关属性的渐变（即可为颜色的渐变的变化）变化
//            @Override
//            public void onUpdateScrollPosition(float position) {
//                //设置标题文本颜色的渐变色
//                int titleBgColor = (int) argbEvaluator.evaluate(position,startTitleBgColor,endTitleBgColor);
//                //设置标题背景颜色的渐变色
//                int titleTxtColor = (int) argbEvaluator.evaluate(position,startTitleTxtColor,endTitleTxtColor);
//                title.setBackgroundColor(titleBgColor);
//                title.setTextColor(titleTxtColor);
//            }
//        });
//        frameLayout1 = (FrameLayout) findViewById(R.id.frameLayout1);
//        frameLayout2 = (FrameLayout) findViewById(R.id.frameLayout2);
//
//        addFirstFrg();
//        addSecondFrg();
//    }
//
//    /**
//     * 设置第一个Fragment页面
//     */
//    private void addFirstFrg() {
//        FragmentManager fm = getSupportFragmentManager();
//        FragmentTransaction ft = fm.beginTransaction();
//        ft.add(R.id.frameLayout1,new firstFragment());
//        ft.addToBackStack("firstFragment");
//        ft.commitAllowingStateLoss();
//    }
//
//    /**
//     * 设置第二个Fragment页面
//     */
//    private void addSecondFrg() {
//        FragmentManager fm = getSupportFragmentManager();
//        FragmentTransaction ft = fm.beginTransaction();
//        ft.add(R.id.frameLayout2,new SecondFragment());
//        ft.addToBackStack("SecondFragment");
//        ft.commitAllowingStateLoss();
//    }
//
//    /**
//     * 设置切换到第一个页面时，改变相关的属性状态
//     */
//    private void setFirstPage(){
//        bottom.setVisibility(View.VISIBLE);
//    }
//
//    /**
//     * 设置切换到第二个页面时，改变相关的属性状态
//     */
//    private void setSecondPage(){
//        bottom.setVisibility(View.VISIBLE);
//    }
//}
