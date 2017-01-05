package com.example.administrator.text1.ui.testApplication;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.administrator.text1.R;
import com.example.administrator.text1.utils.MyApplication;

/**
 * Created by hzhm on 2016/6/13.
 */
public class TextApplication2 extends Activity {

    private TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_application2);
        txt = (TextView) findViewById(R.id.txt);
        MyApplication application = (MyApplication) getApplication();
        txt.setText(application.getText());
    }
}
