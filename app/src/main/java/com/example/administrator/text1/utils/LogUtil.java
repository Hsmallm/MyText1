package com.example.administrator.text1.utils;

import android.content.Context;
import android.util.Log;

import com.example.administrator.text1.BuildConfig;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by hzhm on 2016/8/22.
 */
public class LogUtil {

    private static boolean showLog = BuildConfig.DEBUG;
    private static String prefix = "";
    private static boolean isInvoked;

    /**
     * @param showDebugLog
     * @param tagPrefix    appName is recommended
     */
    public static void config(boolean showDebugLog, String tagPrefix) {
        if (isInvoked) {
            throw new RuntimeException("This method can only be invoked once");
        } else {
            isInvoked = true;
        }
        showLog = BuildConfig.DEBUG && showDebugLog;
        prefix = tagPrefix;
    }

    /**
     *
     * @param str "%s"作为占位符
     * @param objects
     */
    public static void sv(String str, Object... objects){
        for(Object o:objects){
            str.replaceFirst("%s", String.valueOf(o));
        }
        v(str);
    }

    public static void v(String str) {
        v(prefix, str);
    }

    public static void v(String tag, String str) {
        if (showLog)
            Log.v(prefix + tag, str);
    }

    public static void v(Throwable e) {
        v(prefix, e);
    }

    public static void v(String tag, Throwable e) {
        if (showLog)
            Log.v(prefix + tag, Log.getStackTraceString(e));
    }

    //---------------------------------------------------------------------------------------------------------------------

    /**
     *
     * @param str "%s"作为占位符
     * @param objects
     */
    public static void sd(String str, Object... objects){
        for(Object o:objects){
            str.replaceFirst("%s", String.valueOf(o));
        }
        d(str);
    }

    public static void d(String str) {
        d(prefix, str);
    }

    public static void d(String tag, String str) {
        if (showLog)
            Log.d(prefix + tag, str);
    }

    public static void d(Throwable e) {
        d(prefix, e);
    }

    public static void d(String tag, Throwable e) {
        if (showLog)
            Log.d(prefix + tag, Log.getStackTraceString(e));
    }

    //---------------------------------------------------------------------------------------------------------------------

    /**
     *
     * @param str "%s"作为占位符
     * @param objects
     */
    public static void si(String str, Object... objects){
        for(Object o:objects){
            str.replaceFirst("%s", String.valueOf(o));
        }
        i(str);
    }

    public static void i(String str) {
        i(prefix, str);
    }

    public static void i(String tag, String str) {
        if (showLog)
            Log.i(prefix + tag, str);
    }

    public static void i(Throwable e) {
        i(prefix, e);
    }

    public static void i(String tag, Throwable e) {
        if (showLog)
            Log.i(prefix + tag, Log.getStackTraceString(e));
    }

    //---------------------------------------------------------------------------------------------------------------------

    /**
     *
     * @param str "%s"作为占位符
     * @param objects
     */
    public static void sw(String str, Object... objects){
        for(Object o:objects){
            str.replaceFirst("%s", String.valueOf(o));
        }
        w(str);
    }

    public static void w(String str) {
        w(prefix, str);
    }

    public static void w(String tag, String str) {
        if (showLog)
            Log.w(prefix + tag, str);
    }

    public static void w(Throwable e) {
        w(prefix, e);
    }

    public static void w(String tag, Throwable e) {
        e.printStackTrace();
        if (showLog)
            Log.w(prefix + tag, Log.getStackTraceString(e));
    }

    //---------------------------------------------------------------------------------------------------------------------

    /**
     *
     * @param str "%s"作为占位符
     * @param objects
     */
    public static void se(String str, Object... objects){
        for(Object o:objects){
            str.replaceFirst("%s", String.valueOf(o));
        }
        e(str);
    }

    public static void e(String str) {
        e(prefix, str);
    }

    public static void e(String tag, String str) {
        if (showLog)
            Log.e(prefix + tag, str);
    }

    public static void e(Throwable e) {
        e(prefix, e);
    }

    public static void e(String tag, Throwable e) {
        e.printStackTrace();
        if (showLog)
            Log.e(prefix + tag, Log.getStackTraceString(e));
    }

    /**
     * 输入日志到文件
     * @param str 输出的内容
     * @param logFileName 输出的
     * @param context
     */
    public static void f(String str, String logFileName, Context context){
        File file = new File(context.getExternalFilesDir("Log"), logFileName);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file, true);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-DD hh:mm:ss");
            str = simpleDateFormat.format(new Date(System.currentTimeMillis()))+"\n"+str+"\n\n";
            byte[] bytes = str.getBytes();
            fileOutputStream.write(bytes);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 输入日志到文件
     * @param e 输出的内容
     * @param logFileName 输出的
     * @param context
     */
    public static void f(Throwable e, String logFileName, Context context){
        f(Log.getStackTraceString(e), logFileName, context);
    }
}
