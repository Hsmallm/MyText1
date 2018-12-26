package com.example.administrator.text1.testRetrofit;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author HuangMing on 2018/11/13.
 *         Des：Header拦截器，Header统一添加参数
 */

public class AppendHeaderParamInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Headers headers = request.headers().newBuilder().add("token", "mytoken").build();
        Request newRequest = request.newBuilder().headers(headers).build();
        return chain.proceed(newRequest);
    }
}
