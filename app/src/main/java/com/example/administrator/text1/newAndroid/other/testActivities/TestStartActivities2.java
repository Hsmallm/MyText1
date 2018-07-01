package com.example.administrator.text1.newAndroid.other.testActivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.administrator.text1.R;

/**
 * @author HuangMing on 2018/1/5.
 */

public class TestStartActivities2 extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_start_activities);

        Button btn = (Button) findViewById(R.id.btn);
        btn.setText("Activity2");
    }
}
