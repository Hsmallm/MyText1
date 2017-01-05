package com.example.administrator.text1.ui.testHandler;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.TextView;

import com.seaway.android.common.toast.Toast;

/**
 * 功能描述：创建一个与子线程相关的Handler,及Handler的原理
 * Created by hzhm on 2016/7/15.
 */
public class TestHandler2 extends Activity {

    /**
     * 一、android为什么给我们提供了一套Handler机制用于更新UI ？？？

     假设一个Activity当中，有多个线程去更新UI,并且没有加锁机制，那么会有什么样的影响了？？
     A、更新界面混乱
     那么，如果对更新UI操作进行加锁又如何了？
     B、这样将倒置性能的下降


     所有出于以上的种种的原因，android了给我们提供了一套UI的机制,我们了只需要遵循这个机制就行；
     根本就不用去关心多线程问题，所有更新UI的操作，都是在主线程的消息队列中轮循处理的

     二、handler的原理是什么？？？

     A、handler封装了消息的发送（主要包括消息发送给谁）
     Looper：用于消息的存取和管理....
     B、Looper里面包含了一个消息队列MessageQueue，所有的发送过来的消息，都在这个消息队列
     C、Looper.Looper方法，就是一个死循环，不断的从MessageQueue取出消息，如果有消息就发送消息，没有就阻塞
     D、Handler也没那么简单，内部必须的和Looper进行关联，也就是说在Handler里面会找到Looper,找到了Looper也就找到了MessageQuequ,
     handler的发送消息，也就是向MessageQueue消息队列中发送消息

     总结：Handler负责消息的发送；Looper负责接收和回传Handler消息，并把消息直接发送给handler处理 （这里就调用了Looper.looper方法去轮循操作）
     Messa
     */

    class MyThread extends Thread{
        private Handler handler;
        @Override
        public void run() {
            //通过当前子线程实例化Looper对象
            Looper.prepare();
            //通过当前Looper实例化handler对象(这时handler就与Looper进行了关联)
            handler = new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    Toast.showToast(TestHandler2.this,Thread.currentThread()+"");
                }
            };
            //循环处理消息
            Looper.loop();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView  tv = new TextView(this);
        tv.setText("Test MyThread Handler");
        setContentView(tv);

        MyThread myThread = new MyThread();
        myThread.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        myThread.handler.sendEmptyMessage(1);
    }
}
