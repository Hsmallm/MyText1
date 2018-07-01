package com.example.administrator.text1.newAndroid.other.web;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.administrator.text1.R;

/**
 * @author HuangMing on 2017/12/29.
 */

public class TestWebView extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_webview);

        WebView webView = (WebView) findViewById(R.id.webView);
        //设置一些浏览器相关属性，这里只是设置是否支持javaScript脚本
        webView.getSettings().setJavaScriptEnabled(true);
        //设置当前网页在本应用里面打开
//        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("http://www.baidu.com");
    }
}
