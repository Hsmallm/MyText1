package com.example.administrator.text1.testMVP.model;

/**
 * @author HuangMing on 2018/11/5.
 */

public interface IUser {

    String getName();

    String getPassword();

    int checkUserValidity(String name, String password);
}
