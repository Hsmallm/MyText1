package com.example.administrator.text1.utils;

import android.content.Context;
import android.content.res.Resources;

/**
 * Created by hzhm on 2016/9/10.
 * 功能描述：获取状态栏高度
 * 相关技术点：context.getResources().getIdentifier()：返回的一个int类型的资源标识符，如果返回值为0，则表示没有相关资源
 * context.getResources().getDimensionPixelOffset(resourse)：获取到相关资源的尺寸大小
 */
public class StatusBarUtil {

    public static int getStatusBarHeight(Context context){
        try {
            int result = 0;
            //（注：getIdentifier()返回的一个int类型的资源标识符，如果返回值为0，则表示没有相关资源）
            // 通过资源名称、大小、资源类型来获取相关资源
            int resourse = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
            if(resourse > 0){
                //获取到相关资源的尺寸大小
                result = context.getResources().getDimensionPixelOffset(resourse);
                return result;
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
        return 75;
    }
}
