package com.example.administrator.text1.ui.testOther;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.administrator.text1.R;

/**
 * Created by HM on 2016/2/25.
 * 功能描述：
 * Android系统信息获取
 */
public class Text5Activity extends Activity implements View.OnClickListener{

    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text5);

        btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String borad = Build.BOARD;//主板信息
        String production = Build.PRODUCT;//手机产品名
        String osVersion = System.getProperty("os.version");//OS版本
        switch (v.getId()){

            case R.id.btn:
                Toast toast = Toast.makeText(this, production, Toast.LENGTH_SHORT);
                toast.show();
                break;

            default:
                break;
        }
    }
}
