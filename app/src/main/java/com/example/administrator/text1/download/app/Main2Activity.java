package com.example.administrator.text1.download.app;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.administrator.text1.R;
import com.example.administrator.text1.download.entities.FileInfo;
import com.example.administrator.text1.download.service.DownloadService;

/**
 * 主线程UI界面
 * Created by Administrator on 2016/1/29.
 */
public class Main2Activity extends Activity{

    private TextView tvFileName;
    private ProgressBar dbProgress;
    private Button btnStop;
    private Button btnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        tvFileName = (TextView) findViewById(R.id.tvFileName);
        dbProgress = (ProgressBar) findViewById(R.id.dbprogress);
        dbProgress.setMax(100);
        btnStop = (Button) findViewById(R.id.btStop);
        btnStart = (Button) findViewById(R.id.btStart);
        //创建文件信息对象,并实例化
        final FileInfo fileInfo = new FileInfo(0,"http://downmini.kugou.com/kugou8041.exe","kugou8000.exe",0,0);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //通过Intent传递参数给Service（注：Android的四大组件之间都可以通过Intent进行调整）
                Intent intent = new Intent(Main2Activity.this, DownloadService.class);
                //设置行为动作
                intent.setAction(DownloadService.ACCTION_START);
                intent.putExtra("fileInfo",fileInfo);
                //启动Service
                startService(intent);
            }
        });
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //通过Intent传递参数给Service
                Intent intent = new Intent(Main2Activity.this, DownloadService.class);
                intent.setAction(DownloadService.ACCTION_STOP);
                intent.putExtra("fileInfo",fileInfo);
                startService(intent);
            }
        });

        //注册广播接收器
        IntentFilter filter = new IntentFilter();
        filter.addAction(DownloadService.ACCTION_UPDATE);
        registerReceiver(mReceive,filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //当当前Activity结束时，注销广播
        unregisterReceiver(mReceive);
    }

    /**
     * 更新UI的广播接收器
     */
    BroadcastReceiver mReceive = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(DownloadService.ACCTION_UPDATE.equals(intent.getAction())){
                int finished = intent.getIntExtra("finished",0);
                dbProgress.setProgress(finished);
            }
        }
    };
}
