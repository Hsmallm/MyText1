package com.example.administrator.text1.ui.testSetGesture;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.example.administrator.text1.R;
import com.example.administrator.text1.common.base.BaseActivity;

/**
 * 功能测试：测试手势密码的设置与验证
 * Created by hzhm on 2016/6/16.
 */
public class TextSetGesture extends BaseActivity {

    //手势密码至少连接4个点
    public static final int MIN_LOCK_PATTERN_SIZE = 4;
    //解锁密码匹配错误最小限制次数
    public static final int MIN_PATTERN_REGISTER_FAIL = MIN_LOCK_PATTERN_SIZE;
    //解锁密码匹配错误最大限制次数
    public static final int FAILED_ATTEMPTS_BEFORE_TIMEOUT = 5;
    //手势密码KEY
    public static String GESTURE_KEY = "gestureKey";
    //设置手势密码
    public static int GESTURE_SET_CODE = 0x1000;
    //校验手势密码
    public static int GESTURE_VERIFY_CODE = 0x1001;

    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_setgesture);

        type = getIntent().getIntExtra(GESTURE_KEY,0);
        if(type == GESTURE_VERIFY_CODE){
            TextVerifyGestureFargment fargment = new TextVerifyGestureFargment();
            replaceFargment(fargment);
        }else {
            TextSetGestureFragment fragment = new TextSetGestureFragment();
            replaceFargment(fragment);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void replaceFargment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.content,fragment);
        ft.commitAllowingStateLoss();
    }
}
