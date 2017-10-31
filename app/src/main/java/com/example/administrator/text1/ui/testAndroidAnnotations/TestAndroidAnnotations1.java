//package com.example.administrator.text1.ui.testAndroidAnnotations;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.os.Bundle;
//import android.widget.Button;
//import android.widget.TextView;
//
//import com.example.administrator.text1.R;
//import com.seaway.android.common.toast.Toast;
//
//import org.androidannotations.annotations.AfterViews;
//import org.androidannotations.annotations.Click;
//import org.androidannotations.annotations.EActivity;
//import org.androidannotations.annotations.ViewById;
//
///**
// * 功能描述：AndroidAnnotations开发框架的使用，即相关标签的使用
// * Created by hzhm on 2016/7/18.
// */
//
////实例化视图对象
//@EActivity(R.layout.activity_test_androidannotations1)
//public class TestAndroidAnnotations1 extends Activity {
//
//    public static final String NAME = "name";
//    public static final String AGE = "age";
//
//    //初始化相关控件
//    @ViewById(R.id.btn_aa)
//    Button btn;
//    @ViewById(R.id.txt1_aa)
//    TextView txt1;
//    @ViewById(R.id.txt2_aa)
//    TextView txt2;
//
//    //初始化控件后的相关设置（例如：设置文本信息）
//    @AfterViews
//    public void setTextView(){
//        txt1.setText("Hellow!!!");
//        txt2.setText("你好！！！");
//    }
//
//    //设置点击事件集合
//    @Click({R.id.txt1_aa,R.id.txt2_aa})
//    public void onclickTextView(){
//        Toast.showToast(TestAndroidAnnotations1.this,"OK!");
//    }
//
//    //设置点击事件
//    @Click(R.id.btn_aa)
//
//    public void startActivity(){
//        Intent intent = new Intent(TestAndroidAnnotations1.this, TestAndroidAnnotations2_.class);
//        intent.putExtra(NAME,"HM");
//        intent.putExtra(AGE,"18");
//        startActivity(intent);
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
////        setContentView(R.layout.activity_main2);
//    }
//}
