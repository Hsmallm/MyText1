package com.example.administrator.text1.testOkHttpClient;

/**
 * @author HuangMing on 2018/11/12.
 */

public interface ResultCallBack {

    public void onError(String error);

    public void onSuccess(String response);
}
