package com.example.administrator.text1.utils.http;

/**
 * Created by hzhm on 2016/8/18.
 *
 * 功能描述：请求结果回调接口...
 */
public interface Callback<T> {

    void onSuccess(T model);

    void onFail(ServerResultCode serverResultCode, String errorMessage);
}
