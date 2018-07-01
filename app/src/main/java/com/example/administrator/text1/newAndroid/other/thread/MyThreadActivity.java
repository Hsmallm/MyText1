package com.example.administrator.text1.newAndroid.other.thread;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.text1.R;

/**
 * @author HuangMing on 2017/12/27.
 *         功能描述：异步消息处理机制，主要包括4个部分：Message、handler、MessageQueue、Looper
 *         Message：线程之间的消息传递
 *         handler：处理者，主要用于消息的发送和chuli
 *         MessageQueue：消息队列，主要用于handler发生过来的Message(注：每个线程只有一个消息队列MessageQueue)
 *         Looper：消息队列MessageQueue的管家，会调用Looper.loop(),就会无限循环读取MessageQueue中的消息，发现一个就会取出，
 *         传递到Handler的handleMessahe()方法中（注：每个线程也是只有一个Looper）
 *
 */

public class MyThreadActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn;
    private TextView textView;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                textView.setText("Nice to meet you!");
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_thread);

        btn = (Button) findViewById(R.id.btn);
        textView = (TextView) findViewById(R.id.txt);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn) {
            openNewThread3();
        }
    }

    /**
     * 开启子线程方式一：创建一个子线程类
     */
    private void openNewThread1() {
        new MyThread().start();
    }

    /**
     * 开启子线程方式二：创建一个Runnable类
     */
    private void openNewThread2() {
        new Thread(new MyRunnable()).start();
    }

    /**
     * 开启子线程方式三：直接实现一个匿名内部类Runnable
     */
    private void openNewThread3() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //在这里面执行相关的耗时操作
                Message msg = new Message();
                msg.what = 1;
                handler.sendMessage(msg);
            }
        }).start();
    }
}
