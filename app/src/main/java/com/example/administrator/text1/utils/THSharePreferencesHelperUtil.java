package com.example.administrator.text1.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 功能描述：存储应用程序里面的相关对象信息
 * SharedPreferences是Android平台上一个轻量级的存储类，用来保存应用的一些常用配置（其生命周期与整个App的生命周期同步）
 * Created by hzhm on 2016/6/13.
 */
public class THSharePreferencesHelperUtil {

    private static final String TH_SHARED_PREFERENCES = "";
    private static final String USER_PHOTO = "user_photo";
    private static final String LOCK_PATTERN= "lock_pattern";
    private static final String IS_SELECTED= "is_selected";
    private static Context context = ThApplication.getInstance();


    public THSharePreferencesHelperUtil(Context context){
        this.context = context;
    }

    public static void setUserPhotoUrl(String url){
        SharedPreferences sharedPreferences = context.getSharedPreferences(TH_SHARED_PREFERENCES,Context.MODE_APPEND);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_PHOTO,url);
        editor.commit();
    }

    public static String getUserPhotoUrl(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(TH_SHARED_PREFERENCES,Context.MODE_APPEND);
        return sharedPreferences.getString(USER_PHOTO,"");
    }

    public static void setLockPattern(String txt){
        SharedPreferences sharedPreferences = context.getSharedPreferences(TH_SHARED_PREFERENCES,Context.MODE_APPEND);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(LOCK_PATTERN,txt);
        editor.commit();
    }

    public static String getLockPattern(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(TH_SHARED_PREFERENCES,Context.MODE_APPEND);
        return sharedPreferences.getString(LOCK_PATTERN,"");
    }

    public static void setIsSelected(boolean isSelected){
        SharedPreferences sharedPreferences = context.getSharedPreferences(TH_SHARED_PREFERENCES,Context.MODE_APPEND);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(IS_SELECTED,isSelected);
        editor.commit();
    }

    public static boolean getIsSelected(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(TH_SHARED_PREFERENCES,Context.MODE_APPEND);
        return sharedPreferences.getBoolean(IS_SELECTED,false);
    }
}
