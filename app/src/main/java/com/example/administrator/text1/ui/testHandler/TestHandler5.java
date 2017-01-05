package com.example.administrator.text1.ui.testHandler;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import com.example.administrator.text1.R;

/**
 * 功能描述：Android里面更新UI的几种方式：
 * 一：handler.sendEmptyMessage(1);
 * 二：handler.post(new Runnable() {...}
 * 三：runOnUiThread(new Runnable() {...}
 * 四：txt.post(new Runnable() {...}
 *
 * 总结：查看原代码看出，本质上都是handler.sendEmptyMessage(1);
 * （注：异步线程并不是完全不能在里面操作UI线程；如果异步线程里面UI线程的更新是在耗时操作之前，则是可以在里面操作的；如果是在耗时操作之后是不行的）
 * Created by hzhm on 2016/7/15.
 */
public class TestHandler5 extends Activity {

    private TextView txt;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            txt.setText("handler.sendEmptyMessage OK！");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_handler5);

        txt = (TextView) findViewById(R.id.handler_txt5);

        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                txt.setText("threadUI OK!");
            }
        }.start();
    }

    /**
     * 通过handler.post(new Runnable() {...}
     */
    private void handler1() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                txt.setText("handler.post OK!");
            }
        });
    }

    /**
     * 通过handler.sendEmptyMessage(1);
     */
    private void handler2() {
        handler.sendEmptyMessage(1);
    }

    /**
     * 通过runOnUiThread(new Runnable() {...}
     */
    private void updateUI(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                txt.setText("runOnUiThread OK!");
            }
        });
    }

    /**
     * 通过txt.post(new Runnable() {...}
     */
    private void viewUI(){
        txt.post(new Runnable() {
            @Override
            public void run() {
                txt.setText("viewUI OK!");
            }
        });
    }
}
