package com.example.administrator.text1.utils.http;

/**
 * Created by hzhm on 2016/8/18.
 */
public interface Callback<T> {

    void onSuccess(T model);

    void onFail(ServerResultCode serverResultCode, String errorMessage);
}
