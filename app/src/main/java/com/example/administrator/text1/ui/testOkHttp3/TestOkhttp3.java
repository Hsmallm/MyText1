package com.example.administrator.text1.ui.testOkHttp3;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.TextView;

import com.example.administrator.text1.R;
import com.example.administrator.text1.model.TenderModel;
import com.example.administrator.text1.model.address.AreaListModel;
import com.google.gson.Gson;
import com.seaway.android.common.toast.Toast;
import com.ta.utdid2.android.utils.NetworkUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Authenticator;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Credentials;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.Route;

/**
 * Created by hzhm on 2016/11/7.
 * <p>
 * 功能描述：Okhttp3网络底层框架的介绍、使用
 * 一、常见的服务器请求的几种方式：
 * Get、Post、Put、Delete
 * 二、post请求时，请求数据的几种类型：
 * a、POST提交Json数据
 * b、POST提交键值对
 * c、Post方式提交String
 * d、Post方式提交流
 * e、Post方式提交文件
 * f、Post方式提交表单
 * g、Post方式提交分块请求
 */

public class TestOkhttp3 extends Activity {

    private static final String URL = "http://appa.trc.com/trcapi/v1/";
    private static final String URL2 = "http://172.30.250.128:8080/trcloanweb/loans?pageIndex=1&pageSize=10";
    private static final String URL3 = "http://publicobject.com/helloworld.txt";
    //HTTP通信类
    private OkHttpClient client;

    private TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.footer_view);

//        client = new OkHttpClient.Builder().cache();

        txt = (TextView) findViewById(R.id.footer_button);
        reqOfgetToInterceptor();
    }


    /**
     * get 同步请求
     * 注：从Honeycomb SDK（3.0）开始，google不再允许网络请求（HTTP、Socket）等相关操作直接在Main Thread类中
     *
     * @throws IOException
     */
    private void reqOfGetToExecute() throws IOException {
        Request request = new Request.Builder().url(URL).build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            Log.e("response.body().string()", response.body().string() + "");
        }
        Headers header = response.headers();
        for (int i = 0; i < header.size(); i++) {
            Log.e("header.name(" + i + ")" + header.name(i), "header.value(" + i + ")" + header.value(i));
        }
    }

    /**
     * get 异步请求
     */
    private void reqOfGetToEnqueue() {
        Request request = new Request.Builder().url(URL2).get().build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("response.body().string()", e + "");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    //如果请求成功，打印请求响应的响应header头部
                    Headers header = response.headers();
                    for (int i = 0; i < header.size(); i++) {
                        Log.e("header.name(" + i + ")" + header.name(i), "header.value(" + i + ")" + header.value(i));
                    }
                } else {
                    Log.e("response.body().string()", response.body().string() + "");
                }
            }
        });
    }

    /**
     * get 提取相应的响应头部信息
     */
    private void reqOfGetToEnqueue2() {
        Request request = new Request.Builder().url(URL2)
                .header("User-Agent", "OkHttp Headers.java")
                .addHeader("Accept", "application/json; q=0.5")
                .addHeader("Accept", "application/vnd.github.v3+json")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("---onFailure---", e + "");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    Log.e("---Server---", response.header("Server"));
                    Log.e("---Date---", response.header("Date"));
                    Log.e("---Vary---", String.valueOf(response.headers("Vary")));

                    //------解析服务器请求响应相关数据，并将相应的数据转化为所需对象
                    //1、得到服务器请求响应体对象
                    ResponseBody responseBody = response.body();
                    //2、将服务器请求响应对象转化为String字符串类型
                    String responseString = responseBody.string();
                    Log.e("response.body()", responseString);

                    JSONObject jsonObject = null;
                    TenderModel model = null;
                    String code = null;
                    try {
                        //3、再将字符串String类型转化为JSON对象
                        jsonObject = new JSONObject(responseString);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        //4、再将Json对象转化为相应的String、model
                        code = jsonObject.getString("code");
                        model = new Gson().fromJson(jsonObject.getString("data"), TenderModel.class);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    long sysDate = model.sysDate;
                    int canInvestLoanCount = model.canInvestLoanCount;
                    Log.e("code", code);
                    Log.e("---model.sysDate---", sysDate + "---model.canInvestLoanCount---" + canInvestLoanCount);
                } else {
                    Log.e("---onFailure---", response.body().string() + "");
                }
            }
        });
    }

    /**
     * get 响应缓存Cache
     */
    private void reqOfGetToCache() {
        //设置缓存目录
        File file = new File(this.getExternalCacheDir(), "cache");
        int cacheSize = 10 * 1024 * 1024;
        final Cache cache = new Cache(file, cacheSize);

        Request request = new Request.Builder().url(URL3).build();
        //---给client设置相应的cache(即响应缓存的目录地址)：.cache(cache)
        OkHttpClient client2 = new OkHttpClient.Builder().cache(cache).build();
        //实例化相应的request
        //------NetworkUtils.isConnected(this)：判断网络是否连接
        boolean connect = NetworkUtils.isConnected(this);
        if(!connect){
            //------无网络请求时，给request设置是否请求响应缓存.cacheControl(CacheControl.FORCE_NETWORK)：即表示获取响应缓存
            //------注：设置缓存请求时：是request.newBuilder()；设置网络请求：new Request.Builder()
            request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
        }else {
            request = new Request.Builder().url(URL3).build();
        }
        //实例化相应的call
        Call call = client2.newCall(request);
        //执行异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("---onFailure--->>>","访问错误");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    Log.e("---response1---", response.body().string());
                    Log.e("---response1 cacheResponse---", response.cacheResponse() + "");
                    Log.e("---response1 networkResponse---", response.networkResponse() + "");
                }else {
                    Log.e("---onFailure--->>>","服务器异常");
                }
            }
        });

//        Call call2 = client2.newCall(request);
//        call2.enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Log.e("---onFailure--->>>","访问错误");
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                if (response.isSuccessful()) {
//                    Log.e("---response2---", response.body().string());
//                    Log.e("---response2 cacheResponse---", response.cacheResponse() + "");
//                    Log.e("---response2 networkResponse---", response.networkResponse() + "");
//                }else {
//                    Log.e("---onFailure--->>>","服务器异常");
//                }
//            }
//        });

          //------CacheControl：缓存控制器的相关设置与使用
//        CacheControl.Builder builder = new CacheControl.Builder();
//        builder.maxAge(10, TimeUnit.MILLISECONDS);
//        CacheControl cache = builder.build();
//        Request request = new Request.Builder().cacheControl(cache).url(URL2).build();
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Log.e("---onFailure--->>>","访问错误");
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                if(response.isSuccessful()){
//                    String responseBody = response.body().string();
//                    Log.e("---onResponse--->>>",responseBody);
//                }else {
//                    Log.e("---onFailure--->>>","服务器异常");
//                }
//            }
//        });
    }

    /**
     * get Interceptor拦截器
     * Interceptor拦截器，见名知意就是拦截操作，这里用来拦截Request对其做一些特殊处理，
     * 举例：比如上面我们使用到了CacheControl，我们怎么拦截一个请求在网络不可用的时候使用CacheControl.FORCE_CACHE；
     */
    private void reqOfgetToInterceptor(){
        OkHttpClient builder0 = new OkHttpClient();
        OkHttpClient.Builder builder = builder0.newBuilder();
        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                boolean connect = NetworkUtils.isConnected(TestOkhttp3.this);
                if(!connect){
                    request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
                }
                Response response = chain.proceed(request);
                String responseBody = response.body().string();
                Log.e("---onResponse--->>>",responseBody);
                return response;
            }
        });
        OkHttpClient client = builder.build();
        Request request = new Request.Builder().url(URL2).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("---onFailure--->>>",e+"");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){
                    String responseBody = response.body().string();
                    Log.e("---onResponse--->>>",responseBody);
                }else {
                    Log.e("---onFailure--->>>","服务器异常");
                }
            }
        });
    }

    /**
     * get Authenticator 用户认证的支持
     * OkHttp 提供了对用户认证的支持。当 HTTP 响应的状态代码是 401 时，
     * OkHttp 会从设置的 Authenticator 对象中获取到新的 Request 对象并再次尝试发出请求。
     * Authenticator 接口中的 authenticate 方法用来提供进行认证的 Request 对象.
     */
    private void reqOfgetToAuthenticator(){
        OkHttpClient client = new OkHttpClient();
        client.newBuilder().authenticator(new Authenticator() {
            @Override
            public Request authenticate(Route route, Response response) throws IOException {
                //设置新的认证信息
                String credential = Credentials.basic("user", "password");
                return response.request().newBuilder()
                        .header("Authorization", credential)
                        .build();
            }
        });

        Request request = new Request.Builder().url(URL2).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("---onFailure--->>>",e+"");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){
                    String responseBody = response.body().string();
                    Log.e("---onResponse--->>>",responseBody);
                }else {
                    Log.e("---onFailure--->>>","服务器异常");
                }
            }
        });
    }

    /**
     * get 设置网络连接超时
     */
    private void reqOfgetToTimeOut(){
        //------给client设置连接超时时间、读取超时时间、写入超时时间
        client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS).build();
        final Request request = new Request.Builder().url(URL3).build();
        //------new Handler().postDelayed：延时操作
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.e("---onFailure--->>>","访问错误");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if(response.isSuccessful()){
                            String responseBody = response.body().string();
                            Log.e("---onResponse--->>>",responseBody);
                        }else {
                            Log.e("---onFailure--->>>","服务器异常");
                        }
                    }
                });
            }
        },11000);
    }

    /**
     * 1、POST提交Json数据
     */
    private void reqJson() {
        //第一步：MediaType：服务器请求的数据类型
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        Map map = new HashMap();
        map.put("pagenum", "1");
        map.put("pagesize", "10");
        //第二步：RequestBody：服务器请求的请求体
        RequestBody body = RequestBody.create(JSON, new Gson().toJson(map));
        //第三步：Request：服务器请求的请求实例
        Request request = new Request.Builder().url(URL).post(body).build();
        //第四步：实例化okhttp3里面的Call对象，在向服务器发送请求（注：请求是异步的...）
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                //---这个onFailure失败原因：网络无连接...(即还没连接请求的服务器之前)
                //---（注：因为请求是异步的，所以回掉的结果也在异步线程，所以需要单独操作）
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.showToast(TestOkhttp3.this, e + "");
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        response.isRedirect();
                        final String str = response.body().string();
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.showToast(TestOkhttp3.this, str);
                                //举例：将JSON转化为相应的Model
                                AreaListModel model = new Gson().fromJson(str, AreaListModel.class);
                                txt.setText(str);
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            //---这个onFailure失败原因：服务器异常、服务器繁忙啊...（即在连接服务器之后）
                            Toast.showToast(TestOkhttp3.this, "服务器繁忙...");
                        }
                    });
                }
            }
        });
    }

    /**
     * 2、POST 提交键值对
     */
    private void reqKeyToValue() {
        FormBody body = new FormBody.Builder()
                .add("", "")
                .add("", "")
                .build();
        Request request = new Request.Builder()
                .url(URL)
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }
}
