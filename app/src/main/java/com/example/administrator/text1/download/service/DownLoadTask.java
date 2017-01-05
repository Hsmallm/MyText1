package com.example.administrator.text1.download.service;

import android.content.Context;
import android.content.Intent;

import com.example.administrator.text1.download.db.ThreadDAO;
import com.example.administrator.text1.download.db.ThreadDAOImpl;
import com.example.administrator.text1.download.entities.FileInfo;
import com.example.administrator.text1.download.entities.ThreadInfo;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * 下载任务类
 * Created by Administrator on 2016/3/16.
 */
public class DownLoadTask {
    private Context context;
    private FileInfo mfileInfo;
    private ThreadDAO mDao = null;
    private int mFinished = 0;
    public boolean isPause = false;
    public DownLoadTask(Context context, FileInfo fileInfo){
        this.context = context;
        this.mfileInfo = fileInfo;
        mDao = new ThreadDAOImpl(context);//（注：数据访问接口实现类的实现即为数据访问接口的实现）
    }

    public void download(){
        //读取存储在数据库中的线程信息
        List<ThreadInfo> threadInfos = mDao.getThreads(mfileInfo.getUrl());
        ThreadInfo threadInfo = null;
        if(threadInfos.size() == 0){
            //初始化线程信息对象
            threadInfo = new ThreadInfo(0,mfileInfo.getUrl(),0,mfileInfo.getLength(),0);
        }else {
            threadInfo = threadInfos.get(0);
        }
        //创建子线程进行下载
        new DownloadThread(threadInfo).start();
    }

    /**
     * 下载线程
     */
    class DownloadThread extends Thread{
        private ThreadInfo mThreadInfo = null;

        public DownloadThread(ThreadInfo mThreadInfo){
            this.mThreadInfo = mThreadInfo;
        }



        @Override
        public void run() {
            super.run();
            //向数据库插入线程信息
            if(!mDao.isExists(mThreadInfo.getUrl(),mThreadInfo.getId())){
                mDao.insertThread(mThreadInfo);
            }
            HttpURLConnection conn = null;
            RandomAccessFile raf = null;
            InputStream input = null;
            try {
                URL url = new URL(mThreadInfo.getUrl());
                conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(3000);
                conn.setRequestMethod("GET");
                //设置下载位置
                int start = mThreadInfo.getStart()+mThreadInfo.getFinished();
                //（注：这个是告诉服务器 你的客户端的配置/需求,比如说你要取某个文件的多少字节到多少字节就通过这个东西告诉服务器）
                conn.setRequestProperty("Range","byte="+start+"-"+mThreadInfo.getEnd());
                //设置文件写入位置
                File file = new File(DownloadService.DOWNLOAD_PATH,mfileInfo.getFileName());
                raf = new RandomAccessFile(file,"rwd");
                raf.seek(start);
                Intent intent = new Intent(DownloadService.ACCTION_UPDATE);
                //更新加载进度
                mFinished += mThreadInfo.getFinished();
                ///开始下载
                if(conn.getResponseCode() == 200){
                    //读取数据
                    input = conn.getInputStream();
                    byte[] buffer = new byte[ 1024 * 4];
                    int len = -1;
                    long time = System.currentTimeMillis();
                    while ((len = input.read(buffer)) != -1){
                        //写入文件
                        raf.write(buffer,0,len);
                        //更新加载进度
                        mFinished += len;
                        if(System.currentTimeMillis() - time > 500){
                            time = System.currentTimeMillis();
                            //把下载进度发送广播给Activity
                            intent.putExtra("finished",mFinished *100 / mfileInfo.getLength());
                            context.sendBroadcast(intent);
                        }
                        //在下载暂停时，保存下载进度
                        if(isPause){
                            mDao.updateThread(mThreadInfo.getUrl(),mThreadInfo.getId(),mFinished);
                            return;
                        }
                    }
                    //删除线程信息
                    mDao.deleteThread(mThreadInfo.getUrl(),mThreadInfo.getId());
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    conn.disconnect();
                    raf.close();
                    input.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
