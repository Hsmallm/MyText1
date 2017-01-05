package com.example.administrator.text1.ui.testStatusBar;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.example.administrator.text1.R;

/**
 * Created by hzhm on 2016/9/11.
 * 功能描述：如何设置状态栏为沉浸式状态
 * 实现方式：方式一：在onCreate()方法里面，在setContentView之前设置当前窗体的状态栏
 *             方式二：在values_v19资源文件中配置相应的状态栏样式：
 *              <!--设置沉浸状态栏的效果-->
                <style name="AppStatusBarTheme" parent="android:Theme.Holo.Light.DarkActionBar">
                     <item name="android:windowTranslucentStatus">true</item>
                     <item name="android:windowTranslucentNavigation">true</item>
                </style>
 （注：1、方式一，只能针对的是当前Activity的状态栏；而方式二，则是适应于整个应用
       2、测试时候，需要测试手机Android系统版本为4.4以上，才具有这种效果。
       3、方式二要想适应于整个应用，必须要在AndroidManifest里面的application中完成主题的配置）
 */
public class TestStatusBar extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ///------方式一：在onCreate()方法里面，在setContentView之前设置当前窗体的状态栏
        //判断当前手机系统版本是否大于19
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT){
            //设置当前窗体状态栏为透明状态
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //设置导航栏透明
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_test_guidecover);
    }
}
