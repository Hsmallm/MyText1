package com.example.administrator.text1.ui.testAnimator.testNewAnima;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.text1.R;

/**
 * Created by hzhm on 2017/2/21.
 */

public class TestNewAnima3 extends Activity {

    private TextView txt;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_new_anima3);
        txt = (TextView) findViewById(R.id.anima_txt);
        txt.setText("TestNewAnima3");
        txt.setBackgroundColor(getResources().getColor(R.color.red));
    }
}
