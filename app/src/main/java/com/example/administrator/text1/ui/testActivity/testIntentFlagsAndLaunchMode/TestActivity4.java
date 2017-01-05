package com.example.administrator.text1.ui.testActivity.testIntentFlagsAndLaunchMode;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.text1.R;

/**
 * Created by hzhm on 2016/12/2.
 */

public class TestActivity4 extends Activity {

    private TextView txtTitle;
    private TextView txtDescribe;
    private Button btn;

    public static Intent newIntent(Activity activity) {
        Intent intent = new Intent(activity, TestActivity4.class);
        return intent;
    }

    public static Intent newIntentAddFlags(Activity activity) {
        Intent intent = new Intent(activity, TestActivity4.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_base);

        txtTitle = (TextView) findViewById(R.id.title);
        txtDescribe = (TextView) findViewById(R.id.describe);
        txtDescribe.setText("Activity4");
        btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(TestFinishActivity.newIntentAddFlags(TestActivity4.this));
            }
        });
    }
}
