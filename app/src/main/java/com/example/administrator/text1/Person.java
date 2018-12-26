package com.example.administrator.text1;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author HuangMing on 2018/11/29.
 */

public class Person implements Parcelable{

    private String userName;
    private String passwd;

    public Person() {
    }

    public Person(String userName, String passwd) {
        this.userName = userName;
        this.passwd = passwd;
    }

    protected Person(Parcel in) {
        userName = in.readString();
        passwd = in.readString();
    }


    /**
     * 为了能够实现模板参数的传入，这里定义Creator嵌入接口,内含两个接口函数分别返回单个和多个继承类实例
     */
    public static final Parcelable.Creator<Person> CREATOR = new Parcelable.Creator<Person>() {
        @Override
        public Person createFromParcel(Parcel in) {
            return new Person(in);
        }

        @Override
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };

    // 内容描述方法,基本不用
    @Override
    public int describeContents() {
        return 0;
    }

    // 写入接口函数,打包
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userName);
        dest.writeString(passwd);
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    @Override
    public String toString() {
        return "Person{" +
                "userName='" + userName + '\'' +
                ", passwd='" + passwd + '\'' +
                '}';
    }
}
