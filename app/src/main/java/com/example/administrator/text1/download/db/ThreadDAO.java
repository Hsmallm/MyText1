package com.example.administrator.text1.download.db;

import com.example.administrator.text1.download.entities.ThreadInfo;

import java.util.List;

/**
 * 数据访问接口
 * Created by Administrator on 2016/3/16.
 */
public interface ThreadDAO {

    /**
     * 插入线程信息
     * @param threadInfo
     */
    public void insertThread(ThreadInfo threadInfo);

    /**
     * 删除线程信息
     * @param url
     * @param thread_id
     */
    public void deleteThread(String url,int thread_id);

    /**
     * 更新线程下载信息
     * @param url
     * @param thread_id
     * @param finished
     */
    public void updateThread(String url,int thread_id,int finished);

    /**
     * 查询文件的线程信息
     * @param url
     * @return
     */
    public List<ThreadInfo> getThreads(String url);

    /**
     * 线程信息是否存在
     * @param url
     * @param thread_id
     * @return
     */
    public boolean isExists(String url,int thread_id);
}
