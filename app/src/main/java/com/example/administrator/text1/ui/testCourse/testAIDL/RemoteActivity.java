package com.example.administrator.text1.ui.testCourse.testAIDL;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.text1.IGetUser;
import com.example.administrator.text1.IRemoteBankService;
import com.example.administrator.text1.Person;
import com.example.administrator.text1.R;
import com.example.administrator.text1.RemoteClientCallBack;
import com.example.administrator.text1.User;

import java.util.List;

/**
 * @author HuangMing on 2018/11/28.
 *         Des：AIDL跨进程通信
 *         例：RemoteActivity与RemoteBankService处于不同的进程，但是使用AIDL方式实现的话一样可以正常通信
 *         AIDL使用简述
 *         1、创建client/service的aidl文件（如IRemoteService.aidl），client/service使用的是同一份，分别放置在各自的src目录下
 *         2、创建完后，重新build项目，会在app\build\generated\source\aidl目录下自动生成相应的java文件，
 *         想深究的可以对着原理扒扒里边的源码，后边会简述
 *         3、创建service，并实现IRemoteService.Stub,通过onBind()返回
 *         4、创建client，并实现ServiceConnection，然后通过以下方式启动进行绑定，并建立进程间的通道
 *         <p>
 *         Intent intent = new Intent();
 *         intent.setClassName("包名", "包名+service的类名");
 *         bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
 *         5、最后在ServiceConnection的onServiceConnected中通过IRemoteBankService.Stub.asInterface(service)获取client的代理，通过这个代理就可以和service相互通信了
 */

public class RemoteActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "RemoteActivity";

    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private Button btn5;

    private IGetUser iGetUser;
    private TextView tvContent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_course);

        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);
        btn5 = (Button) findViewById(R.id.btn5);
        tvContent = (TextView) findViewById(R.id.content);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        Log.d(TAG, "initView pid = " + android.os.Process.myPid());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                RemoteBankService.bindService(this, connection);
                break;
            case R.id.btn2:
                try {
                    boolean isDesMoney = iRemoteBankService.despoistMoney(5);
                    Log.d(TAG, "isDesMoney1 = " + isDesMoney);
                    isDesMoney = iRemoteBankService.despoistMoney(-5);
                    Log.d(TAG, "isDesMoney2 = " + isDesMoney);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn3:
                try {
                    int drawMoney = iRemoteBankService.drawMoney(5);
                    Log.d(TAG, "drawMoney = " + drawMoney);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn4:
                try {
                    User user = iRemoteBankService.getUser();
                    Log.d(TAG, "user = " + user.toString());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn5:
                //AIDL传递自定义Bean结构(Person 必须实现parcelable接口),即为MyService与RemoteActivity处于不同进程，属于跨进程传参
                Intent intent = new Intent(this, MyService.class);
                bindService(intent, new ServiceConnection() {
                    @Override
                    public void onServiceConnected(ComponentName name, IBinder service) {
                        iGetUser = IGetUser.Stub.asInterface(service);
                        try {
                            List<Person> list = iGetUser.getPersonInfo("HM");
                            for (Person person : list) {
                                tvContent.append(person.toString() + "\n");
                            }
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onServiceDisconnected(ComponentName name) {

                    }
                }, Context.BIND_AUTO_CREATE);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //当Activity销毁时，同时也记得停止Service服务
        RemoteBankService.unBindService(this, connection);
    }

    private IRemoteBankService iRemoteBankService;
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //---建立通讯连接成功
            Log.d(TAG, "onServiceConnected pid = " + android.os.Process.myPid());
            //***获取client的AIDL的代理对象（通过这个代理就可以和service相互通信了）（先是在onBind里面实例化相关对象）
            iRemoteBankService = IRemoteBankService.Stub.asInterface(service);
            try {
                iRemoteBankService.registerClientOberser(remoteClientCallBack);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            //建立通讯连接失败
            Log.d(TAG, "onServiceDisconnected pid = " + android.os.Process.myPid());
        }
    };

    private RemoteClientCallBack.Stub remoteClientCallBack = new RemoteClientCallBack.Stub() {
        @Override
        public void transferToClientByServer(final String transferData) throws RemoteException {
            //如果是service通过handler调用的这个的，由于service的进程调用，所以这个回调不是
            //在主线  程而是工作线程中，直接更新或toast会抛出如下异常，所以定要在主线程中更新
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    com.seaway.android.common.toast.Toast.showToast(com.example.administrator.text1.newAndroid.other.MyApplication.getContext(), transferData);
                }
            });
        }
    };
}
