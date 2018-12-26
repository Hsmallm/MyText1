package com.example.administrator.text1.testRetrofit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.text1.BuildConfig;
import com.example.administrator.text1.R;
import com.example.administrator.text1.model.birthday.DateMode;
import com.example.administrator.text1.utils.http.HttpLoggingInterceptor;
import com.seaway.android.common.toast.Toast;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Cache;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import rx.Observable;
import rx.Observer;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author HuangMing on 2018/11/13.
 *         Des：Retrofit，适用于Android和Java的类型安全的HTTP客户端，是一款针对Android网络请求的开源框架,由square开源的网络Restful请求框架，
 *         底层是基于okhttp实现的(相当于又对okhttp做了一层封装)
 */

public class TestRetrofit extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "TestRetrofit";

    private Button btnGet;
    private Button btnChange;
    private TextView tvContributor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_mvc_main);

        btnGet = (Button) findViewById(R.id.get);
        btnChange = (Button) findViewById(R.id.change);
        tvContributor = (TextView) findViewById(R.id.top_contributor);

        btnGet.setOnClickListener(this);
        btnChange.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.get:
                post();
                break;
            case R.id.change:
                getInfoByRxJavaAndRetrofit();
                break;
            default:
                break;
        }
    }

    /**
     * Retrofit---Get请求
     */
    private void getInfoByRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TestRetrofitApi api = retrofit.create(TestRetrofitApi.class);
        Call call = api.getInfo("Android", "10", 1);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {

            }

            @Override
            public void onFailure(Call call, Throwable t) {

            }
        });
    }

    /**
     * Retrofit---Post请求之上传application/x-www-form-urlencoded 数据是个普通表单
     */
    private void post() {
        OkHttpClient okHttpClient = new OkHttpClient();
        if (BuildConfig.DEBUG) {
            //设置请求拦截器
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            okHttpClient = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build();
        }
        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("http://192.168.1.107:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TestRetrofitApi api = retrofit.create(TestRetrofitApi.class);
        Call call = api.getInfo("zhouguizhi", "123456");
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                Log.e(TAG, "登录请求成功");
                Log.e(TAG, "response=" + response.body());
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.e(TAG, "登录失败");
            }
        });
    }

    /**
     * Retrofit---Post请求之上传multipart/form-data 数据里有文件
     */
    private void uploadFile() {
        OkHttpClient okHttpClient = new OkHttpClient();
        if (BuildConfig.DEBUG) {
            //设置请求拦截器
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            okHttpClient = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build();
        }
        File file = new File("", "zgz3.jpg");
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("uploadfile", file.getName(), requestBody);

        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("http://192.168.1.107:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TestRetrofitApi api = retrofit.create(TestRetrofitApi.class);
        Call call = api.getInfo(body);
        call.enqueue(new Callback<DateMode>() {

            @Override
            public void onResponse(Call<DateMode> call, Response<DateMode> response) {
                Log.e(TAG, "文件上传成功=" + response.body().toString());
            }

            @Override
            public void onFailure(Call<DateMode> call, Throwable t) {
                Log.e(TAG, "文件上传失败" + t.getLocalizedMessage());
            }
        });
    }

    /**
     * Retrofit---Post请求之上传multipart/form-data 数据里有文件 & QueryMap数据
     */
    private void uploadFileAndQuery() {
        OkHttpClient okHttpClient = new OkHttpClient();
        if (BuildConfig.DEBUG) {
            //设置请求拦截器
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            okHttpClient = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build();
        }

        File file = new File("", "");
        RequestBody requestBody = RequestBody.create(MediaType.parse(""), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("", file.getName(), requestBody);

        Map<String, String> map = new HashMap<>();
        map.put("", "");
        map.put("", "");
        map.put("", "");

        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("http://192.168.1.107:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TestRetrofitApi api = retrofit.create(TestRetrofitApi.class);
        Call call = api.getInfo(body, map);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                Log.e(TAG, "上传文件成功！");
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.e(TAG, "上传文件失败");
            }
        });
    }

    /**
     * RxJava+Retrofit实现网络请求
     */
    private void getInfoByRxJavaAndRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.kuaidi100.com/")
                .addConverterFactory(GsonConverterFactory.create())// 添加Gson转换器
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())// 添加Retrofit到RxJava的转换器
                .build();
        RetrofitService retrofitService = retrofit.create(RetrofitService.class);
        Observable observable = retrofitService.getInfo("yuantong", "11111111111");
        observable
                .subscribeOn(Schedulers.io())// 在子线程中进行Http访问
                .observeOn(AndroidSchedulers.mainThread())// UI线程处理返回接口
                .subscribe(new Observer() {// 订阅
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.showToast(com.example.administrator.text1.newAndroid.other.MyApplication.getContext(), String.valueOf(e));
                    }

                    @Override
                    public void onNext(Object o) {
                        Toast.showToast(com.example.administrator.text1.newAndroid.other.MyApplication.getContext(), o.toString());
                        tvContributor.setText(o.toString());
                    }
                });
    }

    /**
     * RxJava+Retrofit实现网络请求---缓存设置
     */
    private void cacheSet() {
        OkHttpClient okHttpClient = new OkHttpClient();
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            int cacheSize = 1024 * 1024 * 6;
            Cache cache = new Cache(new File("FileUtil.getPath()", "cache_content"), cacheSize);
            okHttpClient = new OkHttpClient.Builder().cache(cache)
                    .addInterceptor(httpLoggingInterceptor)
                    .addInterceptor(new CacheInterceptor(this))
                    .build();
        }
        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        RetrofitService retrofitService = retrofit.create(RetrofitService.class);
        Observable observable = retrofitService.getInfo("", "");
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Object o) {

                    }
                });
    }
}
