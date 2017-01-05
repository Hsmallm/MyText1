package com.example.administrator.text1.ui.testHandler;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import com.example.administrator.text1.R;

/**
 * 功能描述：使用Handler时常见异常
 * 异常一：“ViewRootImpl$CalledFromWrongThreadException”
 * 解析：在非UI线程(子线程)中更新UI，所导致的异常
 * 异常二：“Can't create handler inside thread that has not called Looper.prepare()”
 * 解析：在创建一个与子线程线相关联的Handler时，没有给Handler传人相关的Looper对象
 * Created by hzhm on 2016/7/15.
 */
public class TestHandler6 extends Activity {

    private TextView txt;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            txt.setText("");
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
                Handler handler = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        txt.setText("111");
                    }
                };
            }
        }.start();
    }
}
