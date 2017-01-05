package com.example.administrator.text1.ui.testApplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.administrator.text1.R;
import com.example.administrator.text1.utils.MyApplication;
import com.seaway.android.common.toast.Toast;

/**
 * 功能描述：Application应用之-实现activity之间的数据传递
 * Created by hzhm on 2016/6/13.
 */
public class TextApplication1 extends Activity {

    private EditText editText;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_application1);
        editText = (EditText) findViewById(R.id.edit);
        btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt = editText.getText().toString();
                if(editText.getText().toString() == null || editText.getText().toString().equals("")){
                    Toast.showToast(TextApplication1.this,"请输入传输内容");
                    return;
                }else {
                    MyApplication application = (MyApplication) getApplication();
                    application.setText(editText.getText().toString());
                }
                Intent intent = new Intent(TextApplication1.this,TextApplication2.class);
                startActivity(intent);
            }
        });
    }
}
