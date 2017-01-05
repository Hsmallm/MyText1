package com.example.administrator.text1.ui.testHandler;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.widget.TextView;

import com.seaway.android.common.toast.Toast;

/**
 * 功能描述：创建一个与子线程相关的Handler之---HandlerThread（Google官方提供的子线程类）的使用，这里面实现并完成了多线程并发的解决方案
 * Created by hzhm on 2016/7/15.
 */
public class TestHandler3 extends Activity {

    private TextView textView;
    private HandlerThread handlerThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        textView = new TextView(this);
        textView.setText("handler thread");
        setContentView(textView);

        //实例化handlerThread新线程
        handlerThread = new HandlerThread("handlerThread");
        handlerThread.start();

        //创建一个与handlerThread(Looper)相关联的handler对象
        Handler handler = new Handler(handlerThread.getLooper()){
            @Override
            public void handleMessage(Message msg) {
                Toast.showToast(TestHandler3.this,"666666");
                textView.setText("handlerThread222");
            }
        };
        //发送消息
        handler.sendEmptyMessage(1);
    }
}
