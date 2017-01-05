package com.example.administrator.text1.ui.testQQSlide;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.HorizontalScrollView;

import com.example.administrator.text1.R;

/**
 * 功能描述：自定义HorizontalScrollView水平滚动控件，实现QQ侧滑菜单功能
 * (注：布局页面，使用到了HorizontalScrollView即为水平滚动控件，所以里面一样有且只有一个子控件，所以一般在其子控件中布局那两个页面)
 * Created by hzhm on 2016/6/24.
 */
public class TestQQSlideActivity extends Activity{

    private SlideMenu llSlideMenu;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main_menu);

        llSlideMenu = (SlideMenu) findViewById(R.id.menu);
        btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llSlideMenu.isOpenMenu();
            }
        });
    }
}
