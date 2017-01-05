package com.example.administrator.text1.ui.testWebView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.text1.R;
import com.example.administrator.text1.utils.SwipeRefreshLayoutUtil;
import com.seaway.android.common.toast.Toast;
import com.seaway.android.common.widget.dialog.UIDefaultDialogHelper;

/**
 * 功能描述：测试WebView网页加载控件的相关使用、技巧
 * Created by HM on 2016/5/31.
 */
public class TextWebViewFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    public static final String URL = "http://bbs.360taihe.com/forum.php";
    private String webUrl;

    private ImageView imgBack;
    private TextView txtTitle;
    private ImageView imgShare;
    private SwipeRefreshLayout swipeRefreshLayout;
    private WebView webView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.frg_text_webview, null);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imgBack = (ImageView) view.findViewById(R.id.back);
        imgBack.setOnClickListener(this);
        txtTitle = (TextView) view.findViewById(R.id.title);
        imgShare = (ImageView) view.findViewById(R.id.share);
        imgShare.setOnClickListener(this);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        SwipeRefreshLayoutUtil.initStyle(swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);
        webView = (WebView) view.findViewById(R.id.webView);

        txtTitle.setText("论坛");

        checkPage();
        initWebView();
    }

    private void checkPage() {
        //1、检查网络
//        if (NetworkingUtil.checkNetworkingState(getActivity()) == -1) {
//            try {
//                Toast.showToast(getActivity(), "请检查你的网络！");
//                return;
//            } catch (Exception e) {
//                e.printStackTrace();
//                return;
//            }
//        }

        //2、第二部再检测上页面传过的URl
        try {
            Bundle bundle = getArguments();
            if (bundle != null) {
                webUrl = bundle.getString(URL);
            }
            if (TextUtils.isEmpty(webUrl)) {
                Toast.showToast(getActivity(), "请检查你的网络！");
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.showToast(getActivity(), "请检查你的网络！");
            return;
        }
    }

    private void initWebView() {
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                swipeRefreshLayout.setRefreshing(true);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                swipeRefreshLayout.setRefreshing(false);
                //在网页加载完成后，设置页面标题
                if (webUrl.equals(url)) {
                    txtTitle.setText("论坛");
                } else {
                    txtTitle.setText(view.getTitle() == null ? "" : "标题");
                }
            }

            //在点击请求的是链接是才会调用，重写此方法返回true表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边。
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, final String url) {
                try {
                    if (url.indexOf("tel:") < 0) {
                        webView.loadUrl(webUrl);
                    } else {
                        UIDefaultDialogHelper.showDefaultAskAlert(getActivity(), "400-669-0685", "取消", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                UIDefaultDialogHelper.dialog.dismiss();
                                UIDefaultDialogHelper.dialog = null;

                            }
                        }, "确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                UIDefaultDialogHelper.dialog.dismiss();
                                UIDefaultDialogHelper.dialog = null;
                                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(url));
                                startActivity(intent);
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            }
        });


        //设置物理返回键监听
        webView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    //1、当上一页面传入的URl为空时，则返回上级Fragment页面
                    if (TextUtils.isEmpty(URL) || !webView.canGoBack()) {
                        FragmentManager fm = getFragmentManager();
                        fm.popBackStack();
                    } else {
                        //2、当上一页面传入的URL等于当前控件获取的URL
                        if (webView.getUrl().equals(URL)) {
                            FragmentManager fm = getFragmentManager();
                            fm.popBackStack();
                            //其他情况，则返回上级WebView页面
                        } else {
                            webView.goBack();
                        }
                    }
                    return true;
                }
                return false;
            }
        });

        ///设置WebView网页控件相关属性
        webView.setFocusable(true);
        webView.setFocusableInTouchMode(true);
        webView.requestFocus();
        webView.clearCache(true);
        webView.clearHistory();
        webView.clearFormData();
        webView.destroyDrawingCache();

        /** ===== WebSettings的设置 ======== */
        // 1、支持JavaScript
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        // 2、支持ajax跨域操作
        settings.setAllowFileAccess(true);
        if (Build.VERSION.SDK_INT >= 16) {// 解决4.1以上跨域问题
            settings.setAllowUniversalAccessFromFileURLs(true);
        }
        // 3、支持LocalStorage
        settings.setDomStorageEnabled(true);
        settings.setLoadsImagesAutomatically(true);
        // 设置可以支持缩放
        settings.setSupportZoom(true);
        // 设置出现缩放工具
        settings.setBuiltInZoomControls(true);
        //不显示webview缩放按钮
        settings.setDisplayZoomControls(false);

        //自适应屏幕
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setLoadWithOverviewMode(true);

        //加载相应的网页
        webView.loadUrl(URL);
    }

    @Override
    public void onRefresh() {
        webView.loadUrl(URL);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //设置返回监听判断
            case R.id.back:
                if (TextUtils.isEmpty(URL)) {
                    FragmentManager fm = getFragmentManager();
                    fm.popBackStack();
                } else {
                    if (webView.getUrl().equals(URL)) {
                        FragmentManager fm = getFragmentManager();
                        fm.popBackStack();
                    } else {
                        webView.goBack();
                    }
                }
                break;

            case R.id.share:
                Toast.showToast(getActivity(), "OK!");
                break;
            default:
                break;
        }
    }
}
