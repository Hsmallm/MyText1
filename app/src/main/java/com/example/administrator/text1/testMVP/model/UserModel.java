package com.example.administrator.text1.testMVP.model;

/**
 * @author HuangMing on 2018/11/5.
 */

public class UserModel implements IUser {

    private String name;
    private String password;

    public UserModel(String name, String password) {
        this.name = name;
        this.password = password;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public int checkUserValidity(String name, String passwd) {
        if (name == null || passwd == null || !name.equals(getName()) || !passwd.equals(getPassword())) {
            return -1;
        }
        return 0;
    }
}
