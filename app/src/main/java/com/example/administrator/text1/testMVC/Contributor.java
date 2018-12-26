package com.example.administrator.text1.testMVC;

/**
 * @author HuangMing on 2018/11/6.
 */

public class Contributor {

    public String login;
    public String contributions;

    @Override
    public String toString() {
        return login + "," + contributions;
    }
}
