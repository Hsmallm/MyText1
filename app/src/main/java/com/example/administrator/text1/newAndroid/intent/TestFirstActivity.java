package com.example.administrator.text1.newAndroid.intent;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.administrator.text1.R;
import com.example.administrator.text1.utils.ToastUtil;

/**
 * Created by Administrator on 2017/11/1.
 * 功能描述：Intent向上传递数据...
 */

public class TestFirstActivity extends AppCompatActivity {

    private Button btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_intent);

        btn = (Button) findViewById(R.id.btn);
        btn.setText("TestFirstActivity");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TestFirstActivity.this, TestSecondActivity.class);
                //startActivityForResult：用于启动一个活动，第一个参数为intent；第二个参数为requestCode,即为请求的唯一标识码
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //requestCode：请求码，即为startActivityForResult时传递过来的唯一标识请求码
        if (requestCode == 1){
            //requestCode：返回码，即为setResult时传递过来的唯一标识返回码
            if (resultCode == RESULT_OK){
                String returnData = data.getStringExtra("data return");
                ToastUtil.showNormalToast(returnData);
            }
        }
    }
}
