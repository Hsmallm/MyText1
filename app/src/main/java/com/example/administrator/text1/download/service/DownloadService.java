package com.example.administrator.text1.download.service;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.administrator.text1.download.entities.FileInfo;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 * Created by Administrator on 2016/1/29.
 */
public class DownloadService extends Service{

    public static final int MSG_INIT = 0;
    public static final String DOWNLOAD_PATH = Environment.getExternalStorageDirectory().getAbsolutePath()+"/download/";
    public static final String ACCTION_START = "ACCTION_START";
    public static final String ACCTION_UPDATE = "ACCTION_UPDATE";
    public static final String ACCTION_STOP = "ACCTION_STOP";
    private DownLoadTask mTask = null;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //获取Activity传过来的参数
        if(ACCTION_START.equals(intent.getAction())){
            FileInfo fileInfo = (FileInfo) intent.getSerializableExtra("fileInfo");
            Log.i("test","Start:"+fileInfo.toString());
            //启动初始化线程
            new InitThread(fileInfo).start();
        }else if(ACCTION_STOP.equals(intent.getAction())){
            FileInfo fileInfo = (FileInfo) intent.getSerializableExtra("fileInfo");
            Log.i("test","Stop:"+fileInfo.toString());
            if(mTask != null){
                mTask.isPause = true;
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * 创建一个Handle匿名内部类
     */
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case MSG_INIT:
                    //获取子线程中传出的相关对象
                    FileInfo fileInfo = (FileInfo) msg.obj;
                    Log.i("test","Init:"+fileInfo);
                    //启动下载任务
                    mTask = new DownLoadTask(DownloadService.this,fileInfo);
                    mTask.download();
                    break;
            }
        }
    };

    /**
     *初始化子线程
     */
    class InitThread extends Thread{
        private FileInfo fileInfo = null;

        public InitThread(FileInfo fileInfo){
            super();
            this.fileInfo = fileInfo;
        }

        @Override
        public void run() {//(注：在Android4.0以后的版本中，只要涉及到网络操作的就必须用多线程处理，不然就会报错...)
            super.run();
            HttpURLConnection conn = null;
            RandomAccessFile raf = null;
            try{
                //连接网络文件
                URL url = new URL(fileInfo.getUrl());
                //打开连接对象
                conn = (HttpURLConnection) url.openConnection();
                //连接超时
                conn.setConnectTimeout(3000);
                //因为是从服务器下载文件，所以我们选择GET方式来获取数据，除了下载操作之外，其他的一般都用POST
                conn.setRequestMethod("GET");
                int length = -1;
                //判断是否连接成功
                if(conn.getResponseCode() == 200){
                    //获取文件长度
                    length = conn.getContentLength();
                }
                if(length <= 0){
                    return;
                }
                //创建目录
                File dir = new File(DOWNLOAD_PATH);
                if(!dir.exists()){
                    dir.mkdir();
                }
                //在本地创建文件
                File file = new File(dir,fileInfo.getFileName());
                //创建文件流操作对象（注：RandomAccessFile的唯一父类是Object，与其他流父类不同。是用来访问那些保存数据记录的文件的，你可以用它来设定标记）
                raf = new RandomAccessFile(file,"rwd");
                //设置文件长度
                raf.setLength(length);
                fileInfo.setLength(length);
                //通过handler对象将子线程中的获取对象传回主线程
                handler.obtainMessage(MSG_INIT,fileInfo).sendToTarget();
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                try {
                    raf.close();
                    conn.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
