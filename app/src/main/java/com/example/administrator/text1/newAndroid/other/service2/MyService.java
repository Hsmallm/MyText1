package com.example.administrator.text1.newAndroid.other.service2;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.administrator.text1.R;
import com.example.administrator.text1.newAndroid.other.LogUtil;
import com.example.administrator.text1.newAndroid.other.MyApplication;
import com.example.administrator.text1.newAndroid.other.TestNotification;
import com.seaway.android.common.toast.Toast;

/**
 * @author HuangMing on 2017/12/27.
 *         功能描述：Service：Android四大组件之一
 *         1、服务中的代码默认一般都是运行在主线程中
 *         2、onBind()方法中返回IBinder对象作用？？？
 *         调用bindService()来获取一个服务的持久化连接，这时候就会回调onBind()方法，如果这个服务之前没能被创建，则是会先调用onCreat(),再调用onBind()
 *         此时活动中的ServiceConnection拿到这个回调的IBinder对象后，就可以自由的控制服务了...
 *         3、前台服务：会有一个一直处于正在运行的图标在系统的状态栏中显示，也不会因为系统的优先级别比较低在内存不足的时候被回收...
 */

public class MyService extends Service {

    private DownloadBinder downloadBinder = new DownloadBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return downloadBinder;
    }

    /**
     * 服务第一次创建时调用
     */
    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.d("MyService", "onCreate executed!");

        Intent intent = new Intent(this, TestMyServiceActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle("title")
                .setContentText("content")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.image4))
                .setContentIntent(pendingIntent)
                .build();
        //让当前服务变为前台服务，并显示到状态栏中
        startForeground(1, notification);
    }

    /**
     * 每次启动服务时候调用
     *
     * @param intent
     * @param flags
     * @param startId
     * @return
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtil.d("MyService", "onStartCommand executed!");
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 停止服务时候调用
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.d("MyService", "onDestroy executed!");
    }

    /**
     * DownloadBinder：继承Binder，专门控制开始下载、下载进度；以便外面的活动控制当前服务
     */
    class DownloadBinder extends Binder {

        public void startDownload() {
            LogUtil.d("MyService", "startDownload executed!");
        }

        public void getProgress() {
            LogUtil.d("MyService", "getProgress executed!");
        }
    }
}
