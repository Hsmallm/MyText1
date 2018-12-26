package com.example.administrator.text1.testOkHttpClient;

import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.text1.R;
import com.example.administrator.text1.utils.ToastUtil;
import com.seaway.android.common.toast.Toast;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * @author HuangMing on 2018/11/7.
 *         背景：自Android6.0之后就废除了HttpClient，同时官方承认了OKHttp请求框架。我们市面上比较流行的框架包括Vollery OKHttp Retrofit,
 *         而Vollery就是基于HttpClient技术实现的,当HttpClient被废除之后，导致Vollery失去大量使用效果。所以OKHttp成为市面上最大的网络请求框架。
 *         接下我们我们来介绍下这个网络框架 以及该框架如何使用。
 *         <p>
 *         Des:
 *         1.一个处理网络请求的开源项目
 *         2.由移动支付公司Square贡献
 *         3.用于替代HttpUrlConnection和Apache HttpClient
 *         4.OkHttp支持Android 2.3及其以上版本，JDK1.7以上
 *         <p>
 *         POST请求：基本上是客户端上传数据到服务器中去。
 *         POST请求创建request和GET请求是一样的，POST请求需要提交一个表单--RequestBody。
 *         RequestBody需要指定Content-Type，
 *         常见的数据格式有三种：
 *         1.application/x-www-form-urlencoded 数据是个普通表单
 *         2.multipart/form-data 数据里有文件
 *         3.application/json 数据是个json
 *         <p>
 *         同步：就是在你当前主线程进行操作，如果是同步的，在网络请求结束后，才会去进行下面的代码；
 *         而异步：就是子线程，而异步是，开启网络请求后，代码会继续执行下去。而网络请求是同步进行。
 *         用那个得根据你的需要，如果你是要通过结果才能继续接下来的代码的，那就用同步，如果只是发送网络请求，在之后的代码并没有紧密关联，异步就可以了。
 *         访问网络一般都是异步，否则很容易anr
 */

public class TestOkHttpClientActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnGet;
    private Button btnChange;
    private TextView tvContributor;

    private OkHttpClient okHttpClient;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_mvc_main);

        btnGet = (Button) findViewById(R.id.get);
        btnChange = (Button) findViewById(R.id.change);
        tvContributor = (TextView) findViewById(R.id.top_contributor);

        okHttpClient = new OkHttpClient();
        btnGet.setOnClickListener(this);
        btnChange.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.get:
                OkHttpClientManager.getInstance().postAsy(this, "https://www.baidu.com/", new ResultCallBack() {
                    @Override
                    public void onError(String error) {
                        Toast.showToast(com.example.administrator.text1.newAndroid.other.MyApplication.getContext(),error);
                    }

                    @Override
                    public void onSuccess(String response) {
                        Toast.showToast(com.example.administrator.text1.newAndroid.other.MyApplication.getContext(),response);
                    }
                });
                break;
            case R.id.change:
                OkHttpClientManager.getInstance().getAsy(this, "https://www.baidu.com/", new ResultCallBack() {
                    @Override
                    public void onError(String error) {
                        Toast.showToast(com.example.administrator.text1.newAndroid.other.MyApplication.getContext(),error);
                    }

                    @Override
                    public void onSuccess(String response) {
                        Toast.showToast(com.example.administrator.text1.newAndroid.other.MyApplication.getContext(),response);
                    }
                });
                break;
            default:
                break;
        }
    }

    /**
     * Get同步请求（注：同步请求会报错：NetWork...，即表示,网络请求不可再UI线程里面操作）
     */
    private void httpGet() {
        final Request request = new Request.Builder()
                .get()
                .tag(this)
                .url("https://www.baidu.com/")
                .build();
        try {
            okhttp3.Response response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                ToastUtil.showNormalToast(response.body().string());
            } else {
                ToastUtil.showNormalToast("");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get异步请求（注：异步请求虽然可以处理网络请求，但是回调的onFailure、onResponse都在子线程中，无法操作UI界面）
     *
     * @throws IOException
     */
    private void asyGet() throws IOException {
        final Request request = new Request.Builder()
                .get()
                .tag(this)
                .url("https://www.baidu.com/")
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.showToast(com.example.administrator.text1.newAndroid.other.MyApplication.getContext(),e.getMessage());
            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                //这里主要是为了解决子线程里面无法弹Toast(onResponse回调的内容都是在子线程中)
                Looper.prepare();
                Toast.showToast(com.example.administrator.text1.newAndroid.other.MyApplication.getContext(),response.body().string());
                Looper.loop();
            }
        });
    }

    /**
     * POST提交键值对数据（application/x-www-form-urlencoded 数据是个普通表单）
     *
     * @throws IOException
     */
    private void httpPost() throws IOException {
        RequestBody requestBody = new FormBody.Builder()
                .add("code", "")
                .add("phone", "")
                .add("ua", "")
                .build();
        final Request request = new Request.Builder()
                .post(requestBody)
                .tag(this)
                .url("https://www.baidu.com/")
                .build();
        okhttp3.Response response = okHttpClient.newCall(request).execute();
        if (response.isSuccessful()) {
            response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    /**
     * POST提交Json数据(application/json 数据是个json)
     *
     * @throws IOException
     */
    private void httpPostByJson() throws IOException {
        RequestBody requestBody = RequestBody.create(JSON, "");
        Request request = new Request.Builder()
                .url("https://www.baidu.com/")
                .post(requestBody)
                .build();
        okhttp3.Response response = okHttpClient.newCall(request).execute();
        if (response.isSuccessful()) {
            response.body().string();
        } else {
            throw new IOException("" + response);
        }
    }

    /**
     * POST上传文件(multipart/form-data 数据里有文件)
     */
    private void httpPostByUpload() {
        File file = new File("");
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.addFormDataPart("filename", "testpng");
        builder.addFormDataPart("file", file.getName(), RequestBody.create(MediaType.parse("image/png"), file));
        RequestBody requestBody = builder.build();
        Request request = new Request.Builder()
                .tag(true)
                .post(requestBody)
                .url("https://www.baidu.com/")
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {

            }
        });
    }


    /**
     * 异步Post请求
     */
    private void asyPost() {
        RequestBody requestBody = new FormBody.Builder()
                .add("code", "")
                .add("phone", "")
                .add("ua", "")
                .build();
        Request request = new Request.Builder()
                .post(requestBody)
                .tag(this)
                .url("")
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {

            }
        });
    }
}
