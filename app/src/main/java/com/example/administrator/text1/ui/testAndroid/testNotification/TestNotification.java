package com.example.administrator.text1.ui.testAndroid.testNotification;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;

import com.example.administrator.text1.R;
import com.example.administrator.text1.ui.testPageAnimation.TestPageAnimation2;

/**
 * Created by hzhm on 2017/2/9.
 * 功能描述：Notification通知栏的几种用法
 * 1、启动多次通知但只显示一条： notificationManager.notify(number, notification);
 * 通知通知管理器发送通知...（注：number，表示的为给每个通知设置相应的Id;如果id不变，那么无论发送多少个通知则都会显示一个）
 * 2、启动多次通知显示多条：同上，（注：number，表示的为给每个通知设置相应的Id;如果id为变量，那么发送几条就显示几条）
 * 2.1、启动多次通知，并且显示多条，但只能用最新的一条跳转至活动（注：点击第5，6条通知均不会跳转，只有点击第7条才可跳转。
 * 而这是由于PendingIntent的RequestCode相同而导致的(即，设置为常量)，还s得设置flag为PendingIntent.FLAG_CANCEL_CURRENT）
 * 2.2、启动多次通知，并且显示多条，每一条通知都能跳转至对应的活动（注：而这是由于PendingIntent的RequestCode不同而导致的)，
 * 还得设置flag为PendingIntent.FLAG_CANCEL_CURRENT）
 * 3、自定义Notification：关键就是自定义RemoteViews通知栏视图，然后对相关组件实现setOnClickPendingIntent监听...
 * (注：setLatestEventInfo方法在Android3.0后被替换为Notification.Builder来初始化相关属性...)
 */

public class TestNotification extends Activity implements View.OnClickListener {

    private static final int CODE_NOTIFICATION = 200; //构建PaddingIntent的请求码,可用于关闭通知
    private static final int NOTIFICATION_PRE = 1; //上一首
    private static final int NOTIFICATION_NEXT = 2; //下一首
    private static final int NOTIFICATION_OPEN = 3; //打开歌曲

    private Button notifiBtn1;
    private Button notifiBtn2;
    private Button notifiBtn3;
    private Button notifiBtn4;

    private int count = 0;
    private NotificationManager mNotificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_toast);
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        notifiBtn1 = (Button) findViewById(R.id.btn_toast1);
        notifiBtn1.setText("启动多次通知但只显示一条");
        notifiBtn2 = (Button) findViewById(R.id.btn_toast2);
        notifiBtn2.setText("启动多次通知显示多条...");
        notifiBtn3 = (Button) findViewById(R.id.btn_toast3);
        notifiBtn3.setText("自定义Notification");
        notifiBtn4 = (Button) findViewById(R.id.btn_toast4);
        notifiBtn4.setText("取消自定义Notification");

        notifiBtn1.setOnClickListener(this);
        notifiBtn2.setOnClickListener(this);
        notifiBtn3.setOnClickListener(this);
        notifiBtn4.setOnClickListener(this);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_toast1:
                count++;
                setAndSendNotification(1);
                break;
            case R.id.btn_toast2:
                count++;
                setAndSendNotification(count);
                break;
            case R.id.btn_toast3:
                setAndSentCustomNotification();
                break;
            case R.id.btn_toast4:
                mNotificationManager.cancel(CODE_NOTIFICATION);
                break;
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void setAndSendNotification(int number) {
        //拿到系统的通知管理器服务对象
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        //实例化Notification.Builder对象,设置相关参数
        Notification.Builder builder = new Notification.Builder(this);
        builder.setSmallIcon(R.drawable.ic_launcher);
        builder.setTicker("显示第二通知");//在头部弹出第二通知
        builder.setContentTitle("标题");
        builder.setContentText("点击查看详情");
        builder.setWhen(System.currentTimeMillis());
        builder.setDefaults(Notification.DEFAULT_ALL);//设置默认提示声音、振动方式、灯光
        builder.setAutoCancel(true);//设置点击通知后自动清除
        builder.setNumber(count);

        //pendingIntent是一种特殊的Intent。主要的区别在于Intent的执行立刻的，而pendingIntent的执行不是立刻的。
        //（注：但是使用pendingIntent的目的在于它所包含的Intent的操作的执行是需要满足某些条件的。），就是表示点击通知后再执行跳转操作...
        Intent intent = new Intent(this, TestPageAnimation2.class);
        intent.putExtra("name", "name" + count);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 3, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        builder.setContentIntent(pendingIntent);
        //实例化Notification对象...
        Notification notification = builder.build();
        //通知通知管理器发送通知...（注：number，表示的为给每个通知设置相应的Id;如果id不变，那么无论发送多少个通知则都会显示一个）
        notificationManager.notify(number, notification);
    }

    /**
     * 自定义Notification
     * (注：自定义Notification点击后无法清除...)
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void setAndSentCustomNotification() {
        Notification.Builder builder = new Notification.Builder(this);
        builder.setSmallIcon(R.drawable.ic_launcher);
        builder.setTicker("当前正在播放..");
        builder.setWhen(System.currentTimeMillis());
        builder.setDefaults(Notification.DEFAULT_ALL);
        builder.setContentTitle("歌曲名");
        builder.setContentText("歌手");
        builder.setContent(getRemoteView());

        Notification notification = builder.build();
        mNotificationManager.notify(CODE_NOTIFICATION, notification);
    }

    /**
     * 实例化自定义视图、及其相关组件并设置监听...
     * @return
     */
    private RemoteViews getRemoteView() {
        //实例化自定义视图、及其相关组件
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.layout_custom_notification);
        remoteViews.setTextViewText(R.id.tv_content_title, "歌曲名2");
        remoteViews.setTextViewText(R.id.tv_content_text, "歌手2");
        //设置相关点击时间监听
        remoteViews.setOnClickPendingIntent(R.id.btn_pre, getPendingIntent(NOTIFICATION_PRE));
        remoteViews.setOnClickPendingIntent(R.id.btn_next, getPendingIntent(NOTIFICATION_NEXT));
        remoteViews.setOnClickPendingIntent(R.id.ll_root, getPendingIntent(NOTIFICATION_OPEN));
        return remoteViews;
    }

    /**
     * 获取点击自定义通知栏上面的按钮或者布局时的延迟意图
     * (注：PendingIntent，就是延迟意图)
     * @param what
     * @return
     */
    private PendingIntent getPendingIntent(int what) {
        Intent intent = new Intent(this, TestPageAnimation2.class);
        int falg = PendingIntent.FLAG_UPDATE_CURRENT;
        intent.putExtra("name",what+"");
        PendingIntent pendingIntent = PendingIntent.getActivity(this, what, intent, falg);
        return pendingIntent;
    }
}
