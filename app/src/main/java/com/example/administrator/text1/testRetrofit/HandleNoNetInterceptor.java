package com.example.administrator.text1.testRetrofit;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import com.example.administrator.text1.utils.NetworkingUtil;
import com.seaway.android.common.toast.Toast;
import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * @author HuangMing on 2018/11/13.
 *         Des：无网络拦截器，无网络统一添加参数
 */

public class HandleNoNetInterceptor implements Interceptor {

    private Context context;

    public HandleNoNetInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        if (NetworkingUtil.checkNetworking(context)) {
            return chain.proceed(chain.request());
        } else {
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.showToast(com.example.administrator.text1.newAndroid.other.MyApplication.getContext(), "网络异常！");
                }
            });
            throw new RuntimeException("网络异常了~~~~~");
        }
    }
}
