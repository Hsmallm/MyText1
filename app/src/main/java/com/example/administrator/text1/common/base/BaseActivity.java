package com.example.administrator.text1.common.base;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.example.administrator.text1.ui.testSetGesture.TextSetGesture;
import com.example.administrator.text1.utils.THSharePreferencesHelperUtil;

/**
 * 功能描述：基类，用于定义项目中Activity共有属性、及成员方法
 * Created by hzhm on 2016/6/17.
 */
public class BaseActivity extends FragmentActivity {
    public static final String SP_KEY_TIME = "time";
    private SharedPreferences sharedPreferences;
    private Long latestPauseTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences("", MODE_PRIVATE);
    }


    /**
     * 重新开始（当activity由后台切回当前页时，就处于“重新开始”）
     */
    @Override
    protected void onResume() {
        super.onResume();
        if (!THSharePreferencesHelperUtil.getLockPattern().isEmpty()) {
            latestPauseTime = sharedPreferences.getLong(SP_KEY_TIME, System.currentTimeMillis());
            if (Math.abs(latestPauseTime - System.currentTimeMillis()) > 10 * 1000) {
                Intent intent = new Intent(this, TextSetGesture.class);
                intent.putExtra(TextSetGesture.GESTURE_KEY,TextSetGesture.GESTURE_VERIFY_CODE);
                startActivity(intent);
            }
        }
    }

    /**
     * 暂停（当activity切换到后台时，此时就处于“暂停状态”）
     */
    @Override
    protected void onPause() {
        super.onPause();
        if (!THSharePreferencesHelperUtil.getLockPattern().isEmpty()) {
            latestPauseTime = System.currentTimeMillis();
            sharedPreferences.edit().putLong(SP_KEY_TIME, latestPauseTime).commit();
        }
    }
}
