package com.example.administrator.text1.newAndroid.other.meterial_design;

/**
 * @author HuangMing on 2017/12/1.
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

    public void setName(String name) {
        this.name = name;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
