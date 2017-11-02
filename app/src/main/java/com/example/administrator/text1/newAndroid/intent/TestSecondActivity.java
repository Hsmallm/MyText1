package com.example.administrator.text1.newAndroid.intent;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.administrator.text1.R;

/**
 * Created by Administrator on 2017/11/1.
 * 功能描述：Intent向上传递数据
 */

public class TestSecondActivity extends AppCompatActivity {

    private Button btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_intent);

        btn = (Button) findViewById(R.id.btn);
        btn.setText("TestSecondActivity");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("data return","Hello FirstActivity I'm from SecondActivity");
                //setResult：专门用于向上一个活动传递数据；第一个参数为返回码，第二个参数为intent传递的数据
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }
}
