package com.example.administrator.text1.ui.testAndroidAttribute;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.administrator.text1.R;

/**
 * Created by hzhm on 2016/11/28.
 */

public class TestWindowSoftInputMode2 extends Activity {

    public static Intent newIntent(Activity activity){
        Intent intent = new Intent(activity,TestWindowSoftInputMode2.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_windowsoftinputmode2);
    }
}
