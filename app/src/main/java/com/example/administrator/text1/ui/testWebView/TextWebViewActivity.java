package com.example.administrator.text1.ui.testWebView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.text1.R;
import com.example.administrator.text1.common.base.BaseActivity;

/**
 * Created by HM on 2016/5/31.
 */
public class TextWebViewActivity extends BaseActivity{
    private static final String url = "http://bbs.360taihe.com/forum.php";
    private TextView btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text5);

        btn = (TextView) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createShortCut();
            }
        });

    }

    private void creatShortCut() {
        Intent intent = new Intent(Intent.ACTION_CREATE_SHORTCUT);
        Parcelable icon = Intent.ShortcutIconResource.fromContext(this,R.drawable.ic_launcher);
        intent.putExtra("duplicate", false);
        intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "泰和网");
        intent.putExtra(Intent.EXTRA_SHORTCUT_ICON, icon);
        intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT,new Intent(this, TextWebViewActivity.class));
        setResult(RESULT_OK, intent);
    }

    public void createShortCut(){
        //创建快捷方式的Intent
        Intent shortcutintent = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
        //不允许重复创建
        shortcutintent.putExtra("duplicate", false);
        //需要现实的名称
        shortcutintent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "泰和网");
        //快捷图片
        Parcelable icon = Intent.ShortcutIconResource.fromContext(getApplicationContext(), R.drawable.ic_launcher);
        shortcutintent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);
        //点击快捷图片，运行的程序主入口
        shortcutintent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, new Intent(getApplicationContext() , TextWebViewActivity.class));
        //发送广播。OK
        sendBroadcast(shortcutintent);
    }

    private void replaceTo(){
        TextWebViewFragment fragment = new TextWebViewFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TextWebViewFragment.URL,url);
        fragment.setArguments(bundle);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frame, fragment, "TextWebViewFragment");
        ft.addToBackStack("TextWebViewFragment");
        ft.commitAllowingStateLoss();
    }
}
