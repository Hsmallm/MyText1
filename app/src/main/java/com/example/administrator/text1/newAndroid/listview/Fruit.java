package com.example.administrator.text1.newAndroid.listview;

/**
 * @author HuangMing on 2017/11/22.
 */

public class Fruit {

    private String name;
    private int imageId;

    Fruit(String name, int imageId) {
        this.name = name;
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public int getImageId() {
        return imageId;
    }
}
