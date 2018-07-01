package com.example.administrator.text1.newAndroid.other;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.administrator.text1.R;
import com.example.administrator.text1.newAndroid.intent.TestSecondActivity;
import com.example.administrator.text1.utils.ToastUtil;
import com.seaway.android.common.toast.Toast;

/**
 * @author HuangMing on 2017/11/27.
 *         功能描述：功能描述：自定义一个Application，存储一些全局的状态信息(注：Application的生命周期与应用程序的生命周期是一样的)
 */

public class TestMyApplication extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_sharedpreferences);

        Button btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.showToast(MyApplication.getContext(),"this is a All Context");
            }
        });

        Button btn2 = (Button) findViewById(R.id.btn2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Person person = new Person();
                person.setName("HXM");
                person.setAge(12);
                Intent intent = new Intent(TestMyApplication.this, TestIntent.class);
                intent.putExtra("person",person);
                startActivity(intent);
            }
        });

        Button btn3 = (Button) findViewById(R.id.btn3);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Person2 person = new Person2();
                person.setName("HM");
                person.setAge(24);
                Intent intent = new Intent(TestMyApplication.this, TestIntent.class);
                intent.putExtra("person",person);
                startActivity(intent);
            }
        });
    }
}
