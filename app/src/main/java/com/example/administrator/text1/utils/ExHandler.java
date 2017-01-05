package com.example.administrator.text1.utils;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

/**
 * TODO
 * Created by hzhm on 2016/8/22.
 */
public class ExHandler extends Handler {

    public ExHandler(){
    }
    public ExHandler(Looper looper){
        super(looper);
    }

    @Override
    public void dispatchMessage(Message msg) {
        try {
            super.dispatchMessage(msg);
        } catch (Exception e) {
            LogUtil.e(ExHandler.class.getName(), Log.getStackTraceString(e));
        } catch (Error e) {
            LogUtil.e(ExHandler.class.getName(), Log.getStackTraceString(e));
        }
    }
}
