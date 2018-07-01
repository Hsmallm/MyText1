package com.example.administrator.text1.newAndroid.other.service;

import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.text1.R;

/**
 * @author HuangMing on 2017/12/5.
 *         功能描述：解析异步消息处理机制,及其4个主要成员
 *         1、Message：主要用于线程之间的消息的传递，可以携带少量的数据what、arg1、arg2、obj
 *         2、Handler：就是消息的处理者，主要用于消息的发送和处理
 *         3、MessageQueue：消息队列的意思，存放handler发送过来的消息（注：每个线程只有一个消息队列）
 *         4、Looper：每个线程的MessageQueue的管家，调用looper的loop(),就会无线循环，发现MessageQueue有消息就将他去取出来，且一个线程里面只有一个Looper
 */

public class TestThreadOfHandler extends AppCompatActivity {

    private static final int UPDATE_MESSAGE = 1;
    private Dialog dialog = new Dialog(this);

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_MESSAGE:
                    tvChange.setText("This is OK！");
            }
        }
    };

    private TextView tvChange;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_edit);
        tvChange = (TextView) findViewById(R.id.change);
        tvChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Message message = new Message();
                        message.what = UPDATE_MESSAGE;
                        handler.sendMessage(message);
                    }
                }).start();
            }
        });
    }

    /**
     * AsyncTask:实现的原理也是异步消息的处理机制，只是这里Android做了很好的封装
     * Params:执行AsyncTask时候需要传入的参数
     * Progress:后台任务执行，如是需要在界面上显示当前进度
     * Result:任务执行完毕后，对结果进行返回...
     */
    class DownLoadTask extends AsyncTask<Void, Integer, Boolean> {

        @Override
        protected void onPreExecute() {
            //后台任务执行之前调用，用于界面初始化...
            dialog.show();//打开对话框
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            //这里面的代码都会在子线程中运行，所以在这里处理一些耗时操作...(注：这里面无法进行UI操作，如要进行UI操作，如进行进度的更新操作，可以调用publishProgress())
            //执行耗时操作...
            return true;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            //如果后台有调用publishProgress(),当前方法会很快得到执行
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            //dialog.setProgress();//更新设置下载金盾

            dialog.dismiss();//关闭对话框
            //当任务执行完成，并通过return返回时候，就会被调用
        }
    }
}
