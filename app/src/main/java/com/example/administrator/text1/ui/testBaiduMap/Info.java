package com.example.administrator.text1.ui.testBaiduMap;

import com.example.administrator.text1.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 功能描述：自定义Marker标记对象
 * Created by hzhm on 2016/7/1.
 */
public class Info implements Serializable{

    private static final long seriaVersionUID = -1010711755;
    private double latitude;
    private double longtitude;
    private int imgId;
    private String name;
    private String distance;
    private int zan;
    public static List<Info> infos = new ArrayList<Info>();
    static {
        infos.add(new Info(34.242652,108.971171, R.drawable.shop1,"好兄弟餐厅","距离1209米",1456));
        infos.add(new Info(34.242952,108.972171, R.drawable.shop2,"TB兑换体验店","距离209米",1456));
    }

    public Info(double latitude,double longtitude,int imgId,String name,String distance,int zan){
        this.latitude = latitude;
        this.longtitude = longtitude;
        this.imgId = imgId;
        this.name = name;
        this.distance = distance;
        this.zan = zan;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(double longtitude) {
        this.longtitude = longtitude;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public int getZan() {
        return zan;
    }

    public void setZan(int zan) {
        this.zan = zan;
    }
}
