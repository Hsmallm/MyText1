package com.example.administrator.text1.ui.testCourse.testAIDL;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.administrator.text1.IRemoteBankService;
import com.example.administrator.text1.RemoteClientCallBack;
import com.example.administrator.text1.User;

/**
 * @author HuangMing on 2018/11/28.
 */

public class RemoteBankService extends Service {

    private static final String TAG = "RemoteBankService";

    private RemoteClientCallBack remoteClientCallBack;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return iRemoteBankService;
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate pid = " + android.os.Process.myPid());
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy pid = " + android.os.Process.myPid());
        super.onDestroy();
        //当Service销毁时杀掉当前进程
        android.os.Process.killProcess(android.os.Process.myPid());

    }

    /**
     * 绑定服务
     *
     * @param context
     * @param connection
     */
    public static void bindService(Context context, ServiceConnection connection) {
        Log.d(TAG, "bindService pid = " + android.os.Process.myPid());
        Intent intent = new Intent(context, RemoteBankService.class);
        context.bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    /**
     * 停止服务
     *
     * @param context
     * @param connection
     */
    public static void unBindService(Context context, ServiceConnection connection) {
        Log.d(TAG, "doUnbindService pid = " + android.os.Process.myPid());
        context.unbindService(connection);
        context.stopService(new Intent(context, RemoteBankService.class));
    }

    private IRemoteBankService.Stub iRemoteBankService = new IRemoteBankService.Stub() {
        @Override
        public boolean despoistMoney(int money) throws RemoteException {
            Log.d(TAG, "despoistMoney pid = " + android.os.Process.myPid());
            if (money > 0) {
                return true;
            }
            return false;
        }

        @Override
        public int drawMoney(int money) throws RemoteException {
            Log.d(TAG, "drawMoney pid = " + android.os.Process.myPid());
            remoteClientCallBack.transferToClientByServer("当前用户存钱成功" +
                    "余额 ：" + money + "当前进程Id = " + android.os.Process.myPid());
            return money;
        }

        @Override
        public User getUser() throws RemoteException {
            Log.d(TAG, "getUser pid = " + android.os.Process.myPid());
            return new User("张三", "" + System.currentTimeMillis(),
                    "" + android.os.Process.myPid());
        }

        /**
         * 注册客户端的观察者，用以服务端更新后主动通知其更新
         * @param callBack
         * @throws RemoteException
         */
        @Override
        public void registerClientOberser(RemoteClientCallBack callBack) throws RemoteException {
            remoteClientCallBack = callBack;
            Message message = new Message();
            message.obj = callBack;
            timeHandler.sendMessageDelayed(message, 10000);
        }
    };

    private RemoteClientCallBack clientCallBackInstance;

    private TimeHander timeHandler = new TimeHander();

    static class TimeHander extends Handler {
        public TimeHander() {
            Looper looper = Looper.myLooper();
            if (null == looper) {
                Looper.prepare();
                Looper.loop();
            }
        }

        @Override
        public void handleMessage(Message msg) {
            if (null != msg.obj) {
                RemoteClientCallBack clientCallBackInstance
                        = (RemoteClientCallBack) msg.obj;
                try {
                    clientCallBackInstance.transferToClientByServer(
                            "已延期10s后发送 当前进程Id = " + android.os.Process.myPid());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
