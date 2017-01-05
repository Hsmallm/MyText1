package com.example.administrator.text1.ui.testOther;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.text1.R;

/**
 * Created by HM on 2016/1/11.
 * 功能简介：
 * setOnKeyListener键盘事件监听And  setOnTouchListener：触屏事件监听
 */
public class TextActivity extends Activity {

    private EditText editText;
    private TextView textView;

    private TextView imageView;
    //键盘事件监听
    private View.OnKeyListener listener = new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            switch (event.getAction()) {
                //键盘按下（(注：转载：这个OnKeyListener事件只能在物理键盘或者模拟器旁边的键盘起作用，
            //如果使用系统自带的软键盘输入的话，监听器就像聋子一样，对你的软键盘点击操作毫无表示，除了del键、退格键、
            // 空格键有事件产生 Google了好久就也不知道什么原因，
            // 后来在一个外国的Android开发论坛中无意看到这个文档我才涣然大悟.....
            // "it will not properly work with the Virtual Keyboard",嗯，没错，
            // android.widget.EditText的OnKeyListener监 听器在系统自带的软键盘是不起作用的，如果想监听Ed itText里面的文本改变事件，可以使用addTextChangedListner监听器！)

                case KeyEvent.ACTION_DOWN:
                    Toast toast = Toast.makeText(TextActivity.this, "111", Toast.LENGTH_LONG);
                    toast.show();
                    break;
                //键盘松开
                case KeyEvent.ACTION_UP:
                    String str = editText.getText().toString();
                    textView.setText(str);
            }
            return false;
        }
    };

    //触屏事件监听
    private View.OnTouchListener touchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {

            String info = "X:"+event.getX()+"  Y:"+event.getY();
            textView.setText(info);

            switch (event.getAction()){

                case MotionEvent.ACTION_DOWN:
                    Toast toast = Toast.makeText(TextActivity.this, "111", Toast.LENGTH_LONG);
                    toast.show();
                    break;

                case MotionEvent.ACTION_MOVE:
                    Toast toast2 = Toast.makeText(TextActivity.this, "333", Toast.LENGTH_LONG);
                    toast2.show();
                    break;
                case MotionEvent.ACTION_UP:
                    Toast toast1 = Toast.makeText(TextActivity.this, "222", Toast.LENGTH_LONG);
                    toast1.show();
                    break;
            }
            //（注：一定要将默认的return false，改为return true：这样就表示他将执行以下事件）
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text1);

        editText = (EditText) findViewById(R.id.et);
        textView = (TextView) findViewById(R.id.txt);
        imageView = (TextView) findViewById(R.id.image);

        editText.setOnKeyListener(listener);
        imageView.setOnTouchListener(touchListener);
    }

    private Handler handler = new Handler();

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.print("Runnable" + Thread.currentThread().getId());
        }
    };
}
