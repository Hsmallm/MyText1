package com.example.administrator.text1.ui.testGuideCover;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.text1.R;

/**
 * Created by hzhm on 2016/9/9.
 * <p/>
 * 功能描述：实现引导层功能
 *
 * 关键技术点：
 * 1、txtCover.getLocationWindow(loction):获取相关覆盖对象的当前窗体的绝对坐标
 * 2、计算相关覆盖对象距上的距离：相关组建的绝对Y坐标 - 减去通知栏高度Height
 * 3、添加相应的图层到当前的窗体：getWindow().addContentView(viewGroup, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
 * 4、移除相应的图层(注：移除相应的图层之前必须先获得相应图层的父类控件)：ViewGroup views = (ViewGroup) viewGroup.getParent(); views.removeView(viewGroup);
 * 5、有的时候页面在onCreate()或onResume()里面调用view.getLocationWindow()时，返回的数据为空？？？
 * 注解：这是因为相关对象view还未绑定window成功。
 * 解决方案：就是在相关对象绑定Window成功以后，再来进行坐标Location的获取，即为：在onWindowFocusChanged()方法里面调用getLocationWindow
 */
public class TestGuideCover extends Activity {

    private TextView txtCover;
    private TextView txtCover2;
    private GuideCover guideCover;

    private int[] loctionA = new int[2];;
    private int[] loctionB = new int[2];;
    private boolean isFirst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_test_guidecover);

        txtCover = (TextView) findViewById(R.id.cover);
        txtCover2 = (TextView) findViewById(R.id.cover2);

        guideCover = new GuideCover();
        isFirst = true;
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        guideCover.getLocationWindow();
        if(isFirst){
            guideCover.showCover(loctionA,loctionB);
        }
        isFirst =false;
    }

    class GuideCover {

        void getLocationWindow(){
            //自定义int数组，用来存储坐标点位置
            int[] loction = new int[2];
            int[] loction2 = new int[2];
            //获得相关对象当前窗体的绝对坐标
            txtCover.getLocationInWindow(loction);
            txtCover2.getLocationInWindow(loction2);

            loctionA = loction;
            loctionB = loction2;
        }

        void showCover(int[] loction, int[] loction2) {
            //获得相关对象当前窗体的坐标的Y值
            int centerY = loction[1];
            int centerY2 = loction2[1];

            //初始化引导图层及其相关组建
            final ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(TestGuideCover.this).inflate(R.layout.cover_account_guide, null);
            viewGroup.setBackgroundColor(Color.parseColor("#99000000"));
            FrameLayout frameLayout = (FrameLayout) viewGroup.findViewById(R.id.account_fragme);
            ImageView imageView = (ImageView) viewGroup.findViewById(R.id.account_hx_img);

            LinearLayout linearLayout = (LinearLayout) viewGroup.findViewById(R.id.account_lin);
            ImageView imageBtn = (ImageView) viewGroup.findViewById(R.id.account_c);

            //账户详情引导
            int offset = centerY2;
            //设置距离上间距
            linearLayout.setPadding(0,offset,0,0);

            //连连引导
            int offset2 = centerY;
            //设置距离上间距
            ((FrameLayout.LayoutParams) frameLayout.getLayoutParams()).topMargin = offset2;

            //向当前窗体添加图层
            getWindow().addContentView(viewGroup, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            imageBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //点击按钮移除相应的引导层(注：移除相应的图层之前，必须得到相应图层的父类控件)
                    ViewGroup views = (ViewGroup) viewGroup.getParent();
                    views.removeView(viewGroup);
                }
            });
            Log.e("centerY--------", centerY + "");
            Log.e("centerY2--------", centerY2 + "");
        }
    }
}
