package com.example.administrator.text1.newAndroid.intent;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.administrator.text1.R;

/**
 * Created by Administrator on 2017/10/31.
 * 功能描述：Intent使用它在android各个组件之间进行交互
 */

public class TestIntent extends AppCompatActivity {

    private Button btn;
    public static final String MY_CATEGORY = "MY_CATEGORY";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_intent);

        btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //1、显式intent
                Intent intent = new Intent(TestIntent.this, TestFirstActivity.class);
                //2、隐式intent
                Intent intent1 = new Intent("android.intent.action.ANSWER");
                //2.1、addCategory():添加相应的category(注：在AndroidManifest里面要配置默认Category)
                intent1.addCategory("net.learn2develop.Apps");

                //2.2、setData():主要用于接收一个Uri对象，主要用于指定当前Intent正在操作的数据
                Intent intent2 = new Intent(Intent.ACTION_VIEW);
//                intent2.setData(Uri.parse("http://www.baidu.com"));

                //2.3、使用隐式Intent跳转系统拨号界面
                Intent intent3 = new Intent(Intent.ACTION_DIAL);
                intent3.setData(Uri.parse("tel:12312"));
                startActivity(intent);
            }
        });
    }
}
