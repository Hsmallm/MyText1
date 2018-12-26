package com.example.administrator.text1.ui.testLog;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author HuangMing on 2018/11/26.
 */

public class MyLog {

    private static Boolean MYLOG_SWITCH = true; // 日志文件总开关
    private static Boolean MYLOG_WRITE_TO_FILE = true;// 日志写入文件开关
    private static char MYLOG_TYPE = 'v';// 输入日志类型，w代表只输出告警信息等，v代表输出所有信息
    private static String MYLOG_PATH_SDCARD_DIR = "/sdcard/";// 日志文件在sdcard中的路径
    private static int SDCARD_LOG_FILE_SAVE_DAYS = 0;// sd卡中日志文件的最多保存天数
    private static String MYLOGFILEName = "Log.txt";// 本类输出的日志文件名称
    private static SimpleDateFormat myLogSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 日志的输出格式
    private static SimpleDateFormat logfile = new SimpleDateFormat("yyyy-MM-dd");// 日志文件格式
    public Context context;

    public static void v(String tag, Object msg) {
        log(tag, msg.toString(), "v");
    }

    public static void d(String tag, Object msg) {
        log(tag, msg.toString(), "d");
    }

    public static void i(String tag, Object msg) {
        log(tag, msg.toString(), "i");
    }

    public static void w(String tag, Object msg) {
        log(tag, msg.toString(), "w");
    }

    public static void e(String tag, Object msg) {
        log(tag, msg.toString(), "e");
    }

    public static void v(String tag, String text) {
        log(tag, text, "v");
    }

    public static void d(String tag, String text) {
        log(tag, text, "d");
    }

    public static void i(String tag, String text) {
        log(tag, text, "i");
    }

    public static void w(String tag, String text) {
        log(tag, text, "w");
    }

    public static void e(String tag, String text) {
        log(tag, text, "e");
    }

    private static void log(String tag, String msg, String level) {
        if ("e".equals(level) && ("e".equals(MYLOG_TYPE) || "v".equals(MYLOG_TYPE))) {
            Log.e(tag, msg);
        } else if ("w".equals(level) && ("w".equals(MYLOG_TYPE) || "v".equals(MYLOG_TYPE))) {
            Log.w(tag, msg);
        } else if ("i".equals(level) && ("i".equals(MYLOG_TYPE) || "v".equals(MYLOG_TYPE))) {
            Log.i(tag, msg);
        } else if ("d".equals(level) && ("d".equals(MYLOG_TYPE) || "v".equals(MYLOG_TYPE))) {
            Log.d(tag, msg);
        } else {
            Log.v(tag, msg);
        }
        if (MYLOG_WRITE_TO_FILE) {
            writeLogToFile(level, tag, msg);
        }
    }

    /**
     * 将文件写入SD卡
     *
     * @param mylogtype
     * @param tag
     * @param text
     */
    private static void writeLogToFile(String mylogtype, String tag, String text) {
        Date date = new Date();
        String needWriteFileName = logfile.format(date);
        String needWriteMessage = myLogSdf.format(date) + "    " + mylogtype +
                "    " + tag + "    " + text;
        File dirPath = Environment.getExternalStorageDirectory();
        File file = new File(dirPath.toString(), needWriteFileName + MYLOGFILEName);
        try {
            FileWriter fileWriter = new FileWriter(file, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(needWriteMessage);
            bufferedWriter.newLine();
            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除文件
     */
    public static void delFile() {
        String needDelFileName = logfile.format(getDateBefore());
        File dirPath = Environment.getExternalStorageDirectory();
        File file = new File(dirPath, needDelFileName + MYLOGFILEName);
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * 得到现在时间前的几天日期，用来得到需要删除的日志文件名
     *
     * @return
     */
    private static Date getDateBefore() {
        Date date = new Date();
        Calendar now = Calendar.getInstance();
        //设置总时间
        now.setTime(date);
        //设置当前月份第几天
        now.set(Calendar.DATE, now.get(Calendar.DATE)
                - SDCARD_LOG_FILE_SAVE_DAYS);
        return now.getTime();
    }
}
