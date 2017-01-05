package com.example.administrator.text1.ui.testActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.text1.R;

/**
 * 功能描述：测试Activity类的整个生命周期
 * 场景描述：1、第一次运行程序， Log打印：onCreate---onStart----onResume
 *           2、再按一下Back键，Log打印：onPause---onStop----onDestroy
 *
 *           3、再一次打开程序， Log打印：onCreate---onStart----onResume
 *           4、再按一下home键，Log打印：onPause---onStop
 *           5、再一次打开程序， Log打印：onCreate---onStart----onResume
 *
 *           6、点击Button进行跳转，Log打印：onPause---SecondActivity onCreate ----SecondActivity onStart ------SecondActivity onResume ------onStop
 *           7、再按一下Back键，Log打印：SecondActivity-onPause---onRestart ----onStart ------onResume ------SecondActivity-onStop------SecondActivity-onDestroy
 *
 * 总结：1、而当程序刚进入的时候都会依次执行onCreate、onStart 、onResume;
 *       2、按下home键相当于重新启动一个Activity，因为当前显示界面都会执行onPause---onStop这两个方法
 *       3、按BACK键时候，每次都会进入onPause、onStop、onDestroy方法，说明每次都会注销当前Activity；
 *       4、如果当前Activity没有执行onDestroy方法的话，当他再次进入的时候则会执行onRestart 、onStart 、onResume
 * Created by hzhm on 2016/6/20.
 */
public class TestActivity extends Activity {

    private TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.footer_view);
        txt = (TextView) findViewById(R.id.footer_button);
        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TestActivity.this,SecondActivity.class);
                startActivity(intent);
            }
        });
        Log.e("onCreate","--------onCreate--------");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("onStart","--------onStart--------");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("onRestart","--------onRestart--------");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("onResume","--------onResume--------");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("onPause","--------onPause--------");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("onStop","--------onStop--------");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("onDestroy","--------onDestroy--------");
    }
}
