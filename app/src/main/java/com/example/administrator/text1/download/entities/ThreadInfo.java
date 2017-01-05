package com.example.administrator.text1.download.entities;

/**
 * 线程信息实体类
 * Created by Administrator on 2016/1/29.
 */
public class ThreadInfo {
    private int id;//线程ID
    private String url;//线程URL
    private int start;//线程开始位置
    private int end;//线程结束位置
    private int finished;//线程完成进度

    public ThreadInfo() {
        super();
    }

    public ThreadInfo(int id,
                      String url,
                      int start,
                      int end,
                      int finished) {
        super();
        this.id = id;
        this.url = url;
        this.start = start;
        this.end = end;
        this.finished = finished;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public int getFinished() {
        return finished;
    }

    public void setFinished(int finished) {
        this.finished = finished;
    }

    @Override
    public String toString() {
        return "ThreadInfo{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", start=" + start +
                ", end=" + end +
                ", finished=" + finished +
                '}';
    }
}
