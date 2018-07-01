package com.example.administrator.text1.newAndroid.other.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import com.example.administrator.text1.R;
import com.example.administrator.text1.newAndroid.other.LogUtil;

/**
 * @author HuangMing on 2017/12/5.
 *         功能描述：Service，Android的四大组件之一，onCreate()、onStartCommand、onDestroy为最重要的3个方法
 *         onBind()：只有当Activity调用bindService时候，这个方法才会被回调
 */

public class TestService extends Service {

    private DownLoadBinder downLoadBinder = new DownLoadBinder();

    /**
     * 将DownLoadBinder工具类实例返回给Activity自行处理（注：只有当Activity调用bindService时候，这个方法才会被回调）
     */
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return downLoadBinder;
    }

    /**
     * 服务创建时调用（注：服务在第一次创建时调用）
     */
    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.d("TestService", "onCreate executed");
        Intent intent = new Intent(this, TestServiceActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        Notification notification = new NotificationCompat.Builder(this)
                .setContentText("")
                .setContentText("")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.trqq)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.image4))
                .build();
        startForeground(1,notification);
    }

    /**
     * 服务启动时调用(注：每次启动服务的时候调用，即为：服务还未销毁，再次调用startService时候会被再次调用)
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtil.d("TestService", "onStartCommand executed");
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 服务销毁时调用
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.d("TestService", "onDestroy executed");
    }

    /**
     * 下载工具类
     */
    class DownLoadBinder extends Binder {

        public void startDownload() {
            LogUtil.d("TestService", "startDownload executed");
        }

        public int getProgress() {
            LogUtil.d("TestService", "getProgress executed");
            return 0;
        }
    }
}
