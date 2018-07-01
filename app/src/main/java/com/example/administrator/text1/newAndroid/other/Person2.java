package com.example.administrator.text1.newAndroid.other;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author HuangMing on 2017/11/27.
 * 功能描述：Intent传递对象，但对象Parcelable则是将一个完整的对象进行拆分，分解为每一个部分Intent都能够支持
 */

public class Person2 implements Parcelable {

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        //写入Person2对象中的两个字段
        dest.writeString(name);
        dest.writeInt(age);
    }

    /**
     * 必须的提供CREATOR常量，实现了Parcelable.Creator接口
     */
    public static final Parcelable.Creator<Person2> CREATOR = new Parcelable.Creator<Person2>() {

        @Override
        public Person2 createFromParcel(Parcel source) {
            //创建Person2对象，按照写入的顺序读取每个字段的值
            Person2 person2 = new Person2();
            person2.name = source.readString();
            person2.age = source.readInt();
            return person2;
        }

        @Override
        public Person2[] newArray(int size) {
            return new Person2[size];
        }
    };
}
