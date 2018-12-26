package com.example.administrator.text1.ui.testCourse;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.example.administrator.text1.R;

/**
 * @author HuangMing on 2018/11/27.
 */

public class TestCourse2 extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_course2);

        Button bt = (Button) findViewById(R.id.bt_course);
        if (getIntent().getData() != null) {
            String host = getIntent().getData().getHost();
            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                String value = bundle.getString("value");
                bt.setText(host + "  "+value);
            }
        }
    }
}
