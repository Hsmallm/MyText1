package com.example.administrator.text1.common.base.config;

import android.content.SharedPreferences;

import com.example.administrator.text1.utils.AESUtil;
import com.example.administrator.text1.utils.ThApplication;

/**
 * Created by hzhm on 2016/8/22.
 */
public class UserConfig {

    private static final String TH_SHARED_PREFERENCES = "TH_SHARED_PREFERENCES_V3_0";
    private static final String USER_TOKEN = "userToken";

    /**
     * 保存用户Token
     *
     * @param token
     */
    public static void setUserToken(String token) {
        SharedPreferences prefs = ThApplication.getInstance().getSharedPreferences(TH_SHARED_PREFERENCES, 0);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString(USER_TOKEN, AESUtil.encrypt(token));
        prefsEditor.commit();
    }

    /**
     * 获取用户Token
     *
     * @return
     */
    public static String getUserToken() {
        SharedPreferences prefs = ThApplication.getInstance().getSharedPreferences(TH_SHARED_PREFERENCES, 0);
        return AESUtil.decrypt(prefs.getString(USER_TOKEN, ""));
    }
}
