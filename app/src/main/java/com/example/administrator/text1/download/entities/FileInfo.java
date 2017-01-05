package com.example.administrator.text1.download.entities;

import java.io.Serializable;

/**
 * 文件信息实体类
 * Created by Administrator on 2016/1/29.
 */
public class FileInfo implements Serializable{
    private int id;//文件ID
    private String url;//文件的下载地址
    private String fileName;//文件名称
    private int length;//文件长度大小
    private int finished;//文件下载的完成进度

    public FileInfo(){
        super();
    }

    public FileInfo(int id,String url, String fileName,int length, int
                    finished){
        this.id = id;
        this.url = url;
        this.fileName = fileName;
        this.length = length;
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

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getFinished() {
        return finished;
    }

    public void setFinished(int finished) {
        this.finished = finished;
    }

    @Override
    public String toString() {
        return "FileInfo{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", fileName='" + fileName + '\'' +
                ", length=" + length +
                ", finished=" + finished +
                '}';
    }
}
