package com.example.administrator.text1.ui.testCourse;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.administrator.text1.R;

/**
 * @author HuangMing on 2018/11/27.
 *         Des：1、Activity可以跨进程调用其他应用程序的Activity
 *         应用程序中要共享某个Activity，需要为这个Activity指定一个字符串ID，也就是Action。也可以将这个Action看做这个Activity的key。
 *         在其他的应用程序中只要通过这个 Action就可以找到与Action对应的Activity，并通过startActivity方法来启动这个Activity。
 *         如何将应用程序的Activity共享出来
 *         1、 在AndroidManifest.xml文件中指定Action。使用标签在android:name属性中指定Action
 *         2、在AndroidManifest.xml文件中指定访问协议。在指定Uri（Intent类的第2个参数）时需要访问协议。访问协议需要使 用标签的android:scheme属性来指定。如果该属性的值是“tel”，那么Uri就应该是“tel://Uri的主体 部分”，访问协议是Uri的开头部分。
 *         3、通过getIntent().getData().getHost()方法获得协议后的Uri的主体部分。这个Host只是个称谓，并不一定是主机名。
 *         4、从Bundle对象中获得其他应用程序传递过来的数据。
 *         5、获取数据后做相应的处理。(注：隐式intent至少包含"android.intent.category.DEFAULT")
 */

public class TestCourse1 extends AppCompatActivity implements View.OnClickListener {

    private Button btCourse;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_course);

        btCourse = (Button) findViewById(R.id.bt_course);
        btCourse.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_course:
                jumpCustomActivity();
                break;
            default:
                break;
        }
    }

    private void jumpToActivity() {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:13554054250"));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);
            return;
        }
        startActivity(intent);
    }

    private void jumpCustomActivity() {
        //隐式intent至少包含"android.intent.category.DEFAULT"
        Intent intent = new Intent("ui.testCourse.TestCourse2", Uri.parse("owen://start Activity"));
        intent.putExtra("value", "this is from TestCourse1");
        startActivity(intent);
    }
}
