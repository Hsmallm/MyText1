package com.example.administrator.text1.testRetrofit;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.example.administrator.text1.utils.NetworkingUtil;
import com.seaway.android.common.toast.Toast;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author HuangMing on 2018/11/13.
 *         Des:缓存设置，我这是请求http://www.qq.com，这个缓存必须要服务器支持,如果服务器不支持的话,是会报错的,
 *         HTTP 504 Unsatisfiable Request (only-if-cached)我刚开始就遇到了这个问题,网上查了下.
 */

public class CacheInterceptor implements Interceptor {

    private Context context;

    public CacheInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (!NetworkingUtil.checkNetworking(context)) {
            Request cacheRequest = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();
            return chain.proceed(cacheRequest);
        }
        Response cacheRequest = chain.proceed(request);
        long cacheMaxTime = 1 * 60 * 60;
        return cacheRequest.newBuilder()
                .header("Cache-Control", "public, only-if-cached, max-stale=" + cacheMaxTime)
                .removeHeader("Pragma")
                .build();
    }
}
