package com.example.administrator.text1;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author HuangMing on 2018/11/28.
 */

public class User implements Parcelable {

    private String name;
    private String id;
    /**
     * 进程Id
     */
    private String pId;

    public User(String name, String id, String pId) {
        this.name = name;
        this.id = id;
        this.pId = pId;
    }

    public User() {
    }

    @Override
    public String toString() {
        return "name = " + name + " id = " + id + " pid = " + pId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.id);
        dest.writeString(this.pId);
    }

    protected User(Parcel in) {
        this.name = in.readString();
        this.id = in.readString();
        this.pId = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
