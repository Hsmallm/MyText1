package com.example.administrator.text1.ui.testAndroidAnnotations;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import com.example.administrator.text1.R;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

/**
 * Created by hzhm on 2016/7/18.
 */

@EActivity(R.layout.activity_test_androidannotations2)
public class TestAndroidAnnotations2 extends Activity {

    @ViewById(R.id.txt1_aa2)
    TextView textView1;

    @ViewById(R.id.txt2_aa2)
    TextView textView2;

    //设置并获取Intent传过来参数
    @Extra(TestAndroidAnnotations1.NAME)
    String name;

    @Extra(TestAndroidAnnotations1.AGE)
    String age;


    @AfterViews
    public void setTextView(){
        textView1.setText(name);
        textView2.setText(age);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
