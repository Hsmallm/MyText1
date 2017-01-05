package com.example.administrator.text1.ui.testOther;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.administrator.text1.R;

/**
 * 项目需求：要根据某些条件来对EditText弹出键盘与否进行控制
 * 功能描述：
 * 1、editText.setFocusableInTouchMode(false)：对EditText弹出键盘与否进行控制（true获取焦点键盘弹出，false失去焦点键盘不弹出）
 * (注：在使用setFocusableInTouchMode（）控制键盘是否弹出时，必须点击两次输入框，才会弹出键盘：第一次点击是让EditText获取焦点，第二次才是弹出)
 * 2、editText2.setFocusable()：设置相关控件是否获取焦点；（true：获取焦点,editText2里面会有光标闪电;false失去焦点,editText2里面不会有光标闪电）
 * Created by hzhm on 2016/6/16.
 */
public class TestFocusable extends Activity {

    private EditText editText;
    private EditText editText2;

    //判断条件
    private int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_focusable);

        editText = (EditText) findViewById(R.id.edit);
        editText2 = (EditText) findViewById(R.id.edit2);
        editText2.setFocusable(false);
        editText.setFocusableInTouchMode(false);
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(index == 0){
                    editText.setFocusableInTouchMode(false);
                    editText.clearFocus();
                    index = 1;
                }else {
                    editText.setFocusableInTouchMode(true);
                    editText.requestFocus();
                    index = 0;
                }
            }
        });
    }
}
