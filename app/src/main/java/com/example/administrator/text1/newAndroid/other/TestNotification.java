package com.example.administrator.text1.newAndroid.other;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.administrator.text1.R;
import com.example.administrator.text1.ui.testAndroid.testActivity.SecondActivity;

import java.io.File;

/**
 * @author HuangMing on 2017/11/29.
 *         功能描述：Notification通知为Android系统一个比较有特色的功能，即为某个应用程序想向用户发送信息，但是应用又不在前台...
 *         （注：在实例化Notification时候，这里我们使用support-4包中提供的NotificationCompat类来构建，这样更兼容不同的Android系统）
 *         pendingIntent：与Intent类似都是指明某个意图，启动活动、服务或是发送广播，不同在于Intent更加倾向于立即执行某个动作
 *         ，而PendingIntent则是更加倾向于某个时机在执行；简单的理解为就是延时执行Intent
 */

public class TestNotification extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_sharedpreferences);
        Button btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                Intent intent = new Intent(TestNotification.this, SecondActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(TestNotification.this, 0, intent, 0);
                Notification notification = new NotificationCompat.Builder(TestNotification.this)
                        .setContentTitle("this is a title")
                        .setContentText("this is a Content this is a Content this is a Content this is a Content this is a Content this is a Content this is a Content")
                        //设置长文本显示样式
                        .setStyle(new NotificationCompat.BigTextStyle().bigText("this is a Content this is a Content this is a Content this is a Content this is a Content this is a Content this is a Content"))
                        //设置文本大图显示样式
                        .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(BitmapFactory.decodeResource(getResources(), R.drawable.image4)))
                        .setWhen(System.currentTimeMillis())
                        //设置小图标
                        .setSmallIcon(R.drawable.messenger_bubble_small_blue)
                        //设置大图标
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.trqq))
                        .setContentIntent(pendingIntent)
                        //设置点击后自动消失
                        .setAutoCancel(true)
//                      //设置声音(注：路径一定得拼全,查看文件详情获取最全路径...)
                        .setSound(Uri.fromFile(new File("/storage/emulated/0/system/media/audio/ringtones/2015959315938631.ogg")))
//                        //设置振动，new一个长整形数组：静止时长、振动时长、静止时长、振动时长.....
//                        .setVibrate(new long[]{0, 1000, 1000, 1000, 1000, 1000})
                        //设置提示LED灯光（注：LED并未闪烁？？？）
                        .setLights(Color.RED, 2000, 1000)
                        //设置默认效果
                        .setDefaults(NotificationCompat.DEFAULT_ALL)
                        //设置通知的重要程度(PRIORITY_MAX:表示最高重视度，这类消息会让用户立即看到...)
                        .setPriority(NotificationCompat.PRIORITY_MAX)
                        .build();
                manager.notify(1, notification);
            }
        });
    }
}
