package com.example.administrator.text1.newAndroid.other;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;

import com.seaway.android.common.toast.Toast;

/**
 * @author HuangMing on 2017/11/28.
 *         功能描述：Alarm机制实现定时操作，避免了Timer进行长时间定时操作时，无法解决手机的休眠问题，而Alarm机制具有唤醒CPU功能，完美的解决了长时间定时操作...
 *         (注：Android4.4之后，Alarm任务的除非时间将不是那么准确，这个并不是bug,而是系统在耗电性能上的一个优化，系统将自动检查相近的几个Alarm任务一起执行，避免了CPU的多次唤醒，从而也就
 *         避免的电量的损耗；如果你要求Alarm任务执行时必须准确无误，则使用setExact()方法替代set()方法)
 */

public class LongTimeService extends Service {

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Toast.showToast(MyApplication.getContext(), "666666");
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                handler.sendEmptyMessage(0);
                Log.e("LongTimeService", "666666");
            }
        }).start();
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        int time = 10 * 1000;
        //SystemClock.elapsedRealtime()：获取系统开机至今所经历的毫秒数
        long triggerAtTime = SystemClock.elapsedRealtime() + time;
        Intent intent1 = new Intent(this, LongTimeService.class);
        PendingIntent pendingIntent = PendingIntent.getService(this, 0, intent1, 0);
        //设置定时操作：AlarmManager.ELAPSED_REALTIME_WAKEUP：Alarm工作类型，表示让定时任务从系统开机时开始算；triggerAtTime：定时任务的触发时间；
        // pendingIntent：执行服务,如在服务的onStartCommand方法里、onReceive方法里
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pendingIntent);
        return super.onStartCommand(intent, flags, startId);
    }
}
