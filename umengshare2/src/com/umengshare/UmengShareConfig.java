package com.umengshare;

import android.app.Application;

import com.umeng.socialize.PlatformConfig;

/**
 * 功能描述：设置各大分享平台的Appid、Appkey
 * Created by hzhm on 2016/6/15.
 */
public class UmengShareConfig {

    public static void init(Application application){
        //微信Appid、AppKey
        PlatformConfig.setWeixin("1105397005", "6P6O9Akt23LEghBY");
        //新浪微博Appid、AppKey
        PlatformConfig.setSinaWeibo("2317112044", "49f2f987bf452112ed4b681d4e23a701");
        //QQ Appid、AppKey
        PlatformConfig.setQQZone("1105397405", "ed2EixTjMPNZIbNt");
    }
}
