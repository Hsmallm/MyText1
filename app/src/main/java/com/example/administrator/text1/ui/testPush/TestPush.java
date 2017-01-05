package com.example.administrator.text1.ui.testPush;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.example.administrator.text1.R;
import com.umeng.message.PushAgent;
import com.umeng.message.UmengRegistrar;

/**
 * Created by hzhm on 2016/8/23.
 */
public class TestPush extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //开启推送服务
        PushAgent pushAgent = PushAgent.getInstance(this);
        pushAgent.enable();
        //统计应用启动数据
        pushAgent.onAppStart();
        //获取设备的device_token（可选）
        String token = UmengRegistrar.getRegistrationId(this);
        Log.e("device_tiken",token);

//        pushAgent.enable(new IUmengRegisterCallback() {
//            @Override
//            public void onRegistered(String s) {
//                LogUtil.e("device_tiken",s);
//            }
//        });
    }
}
