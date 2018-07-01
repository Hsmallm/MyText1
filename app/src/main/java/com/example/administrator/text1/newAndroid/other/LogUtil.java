package com.example.administrator.text1.newAndroid.other;

import android.util.Log;

/**
 * @author HuangMing on 2017/11/27.
 *         功能描述：LogUtil:通过静态变量level来控制打印级别，从而来控制打印；例如：将lever设置为VERROSE就是在设置为全局打印，
 *         将lever设置为NOTHING，则是屏蔽所有的日志打印
 */

public class LogUtil {

    private static final int VERBOSE = 1;
    private static final int DEBUG = 2;
    private static final int INFO = 3;
    private static final int WARE = 4;
    private static final int ERROR = 5;
    private static final int NOTHING = 6;

    private static int level = VERBOSE;

    public static void v(int level) {
        LogUtil.level = level;
    }

    public static void v(String tag, String msg) {
        if (level <= VERBOSE) {
            Log.v(tag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (level <= DEBUG) {
            Log.d(tag, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (level <= INFO) {
            Log.i(tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (level <= WARE) {
            Log.w(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (level <= ERROR) {
            Log.e(tag, msg);
        }
    }
}
