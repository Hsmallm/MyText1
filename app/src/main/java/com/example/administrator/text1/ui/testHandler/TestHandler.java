package com.example.administrator.text1.ui.testHandler;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.text1.R;
import com.seaway.android.common.toast.Toast;

/**
 * 功能描述：Handler是什么、及其handler使用
 * 一、Handler是什么：它是android给我们的一套更新UI的机制，也是一套处理消息的机制；我们可以用它发送消息，也可以用它处理消息
 * 二：handler使用...
 * Created by hzhm on 2016/7/13.
 */
public class TestHandler extends Activity implements View.OnClickListener {

    private int images[] = {R.drawable.image1, R.drawable.image2, R.drawable.image3};
    private int index;
    /// 2.1、handler.postDelayed延时操作处理
    private MyRunable mMyRunable = new MyRunable();

    class MyRunable implements Runnable {
        @Override
        public void run() {
            index++;
            //设置循环数，让index在（0，1，2）之间循环，从而设置图片的轮播
            index = index % 3;
            mImageView.setImageResource(images[index]);
            handler.postDelayed(mMyRunable, 1000);
        }
    }

    private TextView txt;
    private Button btn;
    private ImageView mImageView;
    ///在new一个handler对象时，再new一个Callback对象，实现的boolean类型的handlerMessage方法可以进行消息拦截
    ///(注：返回true:消息被拦截，就不会走下面方法；返回false:消息未被拦截，可走下面方法)
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            Toast.showToast(getApplicationContext(),""+1);
            return true;
        }
    }) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Toast.showToast(getApplicationContext(),""+2);
            txt.setText("" + msg.obj);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_handler1);

        txt = (TextView) findViewById(R.id.txt);
        mImageView = (ImageView) findViewById(R.id.handler_iv);
        btn = (Button) findViewById(R.id.handler_btn);
        btn.setOnClickListener(this);
        /// 2.1、handler.postDelayed延时操作处理
        handler.postDelayed(mMyRunable, 1000);

        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    ///1、方式一：发送message.obj，让Handler里面的handleMessage处理完成UI线程的更新
                    ///(注：msg.arg1、msg.arg2只能传递int整形参数,msg.obj则可以传递任意类型对象)
                    ///创建Message对象的两种方式：1、new一个；2、handler.obtainMessage();
                    Message msg = new Message();
//                    Message mag = handler.obtainMessage();
                    msg.arg1 = 66;
                    msg.arg2 = 88;
                    TestPerson person = new TestPerson();
                    person.age = 23;
                    person.name = "滨江一霸";
                    msg.obj = person;
                    ///发送消息msg给handler的两种方式：1、msg.sendToTarget();；2、handler.sendMessage(msg);
//                    msg.sendToTarget();
                    handler.sendMessage(msg);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();

//        new Thread(){
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(1000);
//                    ///2、方式二：调用handler.post，在run方法里面完成UI线程的更新
//                    handler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            txt.setText("888888");
//                        }
//                    });
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }.start();
    }

    @Override
    public void onClick(View v) {
        ///2.2、使用handler.removeCallbacks()移除消息队列
        handler.removeCallbacks(mMyRunable);
        handler.sendEmptyMessage(1);
    }
}
