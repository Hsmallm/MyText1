package com.example.administrator.text1.ui.testHandler;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.example.administrator.text1.R;
import com.seaway.android.common.toast.Toast;

/**
 * 功能描述：主线程和子线程之间的信息交互
 * 实现原理：通过创建一个与主线程相关联的handler和一个与子线程线关联的handler,然后在handlerMessage里面分别向对方发送消息
 * Created by hzhm on 2016/7/15.
 */
public class TestHandler4 extends Activity implements View.OnClickListener {

    private Button startBtn;
    private Button stopBtn;

    //1、创建并实例化一个与主线程相关联的handle
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Message message = new Message();
            System.out.print("main handler");
//            Toast.showToast(TestHandler4.this,"main handler");
            Log.e("main handler","main handler");
            threadHandler.sendMessageDelayed(message,1000);
        }
    };

    private Handler threadHandler;
    private HandlerThread handlerThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_handler);

        startBtn = (Button) findViewById(R.id.btn1);
        stopBtn = (Button) findViewById(R.id.btn2);

        startBtn.setOnClickListener(this);
        stopBtn.setOnClickListener(this);

        //2、创建并实例化一个与子线程相关联的handle
        handlerThread = new HandlerThread("handlerThread");
        handlerThread.start();
        threadHandler = new Handler(handlerThread.getLooper()){
            @Override
            public void handleMessage(Message msg) {
                Message message = new Message();
                System.out.print("thread handler");
//                Toast.showToast(TestHandler4.this,"thread handler");
                Log.e("thread handler","thread handler");
                //向主线程发送消息
                handler.sendMessageDelayed(message,1000);
            }
        };
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn1:
                handler.sendEmptyMessage(1);
                break;

            case R.id.btn2:
                handler.removeMessages(1);
                break;
        }
    }
}
