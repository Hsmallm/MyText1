package com.example.administrator.text1.newAndroid.other;

import java.io.Serializable;

/**
 * @author HuangMing on 2017/11/27.
 * 功能描述：Intent传递对象，但对象必须的经过Serializable序列化：即将对象转化成可存储或是可传输的状态
 */

public class Person implements Serializable{

    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
