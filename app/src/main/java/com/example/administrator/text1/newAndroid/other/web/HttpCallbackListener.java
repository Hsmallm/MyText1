package com.example.administrator.text1.newAndroid.other.web;

/**
 * @author HuangMing on 2017/12/31.
 */

public interface HttpCallbackListener {

    void onSuccess(String reponse);

    void onFail(Exception e);
}
