package com.example.administrator.text1.testOkHttpClient;

import android.app.Activity;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @author HuangMing on 2018/11/12.
 *         des：OkHttpClient请求框架的简单封装
 */

public class OkHttpClientManager {

    private static OkHttpClientManager instance;
    private OkHttpClient mOkHttpClient;
    private static final String TAG = "OkHttpClientManager";
    /**
     * 全局处理子线程和M主线程通信
     */
    private Handler okHttpHandler;

    public OkHttpClientManager() {
        mOkHttpClient = new OkHttpClient.Builder()
                //设置连接超时时间
                .connectTimeout(10, TimeUnit.SECONDS)
                //设置读取超时时间
                .readTimeout(10, TimeUnit.SECONDS)
                //设置写入超时时间
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();
        //初始化Handler(Looper.getMainLooper()：主线程)
        okHttpHandler = new Handler(Looper.getMainLooper());
    }

    public static OkHttpClientManager getInstance() {
        if (instance == null) {
            //synchronized：同步代码块时，一个时间内只能有一个线程得到执行。
            synchronized (OkHttpClientManager.class) {
                if (instance == null) {
                    instance = new OkHttpClientManager();
                }
            }
        }
        return instance;
    }

    /**
     * 异步GET请求
     *
     * @param activity
     * @param url
     * @param resultCallBack
     */
    public void getAsy(final Activity activity, String url, final ResultCallBack resultCallBack) {
        Request request = addHeader()
                .get()
                .tag(activity)
                .url(url)
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                handlerCallBack(false, activity, resultCallBack, String.valueOf(e));
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (response.isSuccessful()) {
                    //response.body().string()：注意，这个操作也得放在子线程里面完成
                    String str = response.body().string();
                    handlerCallBack(true, activity, resultCallBack, str);
                } else {
                    handlerCallBack(false, activity, resultCallBack, "服务器错误！");
                }
            }
        });

    }

    /**
     * 异步POST请求
     *
     * @param activity
     * @param url
     * @param resultCallBack
     */
    public void postAsy(final Activity activity, String url, final ResultCallBack resultCallBack) {
        FormBody formBody = new FormBody.Builder()
                .add("", "")
                .add("", "")
                .add("", "")
                .build();
        RequestBody requestBody = formBody;
        final Request request = addHeader()
                .post(requestBody)
                .tag(activity)
                .url(url)
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                handlerCallBack(false, activity, resultCallBack, String.valueOf(e));
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (response.isSuccessful()) {
                    //response.body().string()：注意，这个操作也得放在子线程里面完成
                    String str = response.body().string();
                    handlerCallBack(true, activity, resultCallBack, str);
                } else {
                    handlerCallBack(false, activity, resultCallBack, "服务器错误！");
                }
            }
        });
    }

    /**
     * 向Header里面添加相应参数
     *
     * @return
     */
    private Request.Builder addHeader() {
        Request.Builder builder = new Request.Builder()
                .addHeader("platform", "2")
                .addHeader("phoneModel", Build.MODEL)
                .addHeader("systemVersion", Build.VERSION.RELEASE)
                .addHeader("appVersion", "3.2.0");
        return builder;
    }

    /**
     * 处理相关请求回调
     */
    private void handlerCallBackByHandler() {
        okHttpHandler.post(new Runnable() {
            @Override
            public void run() {
                //这个可以处理回调函数
            }
        });
    }

    /**
     * 处理相关请求回调
     *
     * @param isSuccess
     * @param activity
     * @param resultCallBack
     * @param response
     */
    private void handlerCallBack(final boolean isSuccess, Activity activity, final ResultCallBack resultCallBack, final String response) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (isSuccess) {
                    resultCallBack.onSuccess(response);
                } else {
                    resultCallBack.onError(response);
                }
            }
        });
    }
}
