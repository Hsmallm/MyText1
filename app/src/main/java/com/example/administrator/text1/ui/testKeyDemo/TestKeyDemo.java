package com.example.administrator.text1.ui.testKeyDemo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.example.administrator.text1.R;

import java.lang.reflect.Method;

import static com.example.administrator.text1.ui.testKeyDemo.KeyboardUtil.QWERTY;
import static com.example.administrator.text1.ui.testKeyDemo.KeyboardUtil.SYMBOLS;

/**
 * @author HuangMing on 2017/11/20.
 *         <p>
 *         功能描述：自定义软键盘
 *         <p>
 *         相关问题：这是由于显示的字体时白色，而背景也是白色导致的。后来各种百度，
 *         有人说是因为theme的问题，因为我用的是android5.0.1的API，自动生成项目时，
 *         生成的activity是直接继承ActionBarActivity的，直接改theme会导致应该出现秒退现象。
 *         如果要改theme就要先改继承ActionBarActivity为Activity，然后再去改theme，
 *         但是我很不喜欢这样，因为这样没有通用性，后来在google了一下外国人的贴子，
 *         才了解到这个弹出的东西叫preview，我们可以修改它的布局的。在我们自定义的KeyboardView中加入android：keyPreviewLayout标签，
 *         加入后如下：然后在layout文件夹中，新建一个key_preview_layout.xml文件，里面这样写  >>>>  dialog_test_key_demo布局文件
 */

public class TestKeyDemo extends Activity {

    private KeyboardUtil keyboardUtil;
    private Context ctx;
    private Activity act;
    private EditText edit;
    private EditText edit1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_key_demo);
        ctx = this;
        act = this;

        edit = (EditText) this.findViewById(R.id.edit);
        edit1 = (EditText) this.findViewById(R.id.edit1);

        edit.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                keyboardUtil = new KeyboardUtil(act, ctx, edit);
                keyboardUtil.setKeyboard(QWERTY);
                keyboardUtil.showKeyboard();
                disableShowSoftInput(edit);
                return false;

            }
        });

        edit1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
//                int inputBack = edit1.getInputType();
//                edit1.setInputType(InputType.TYPE_NULL);
                keyboardUtil = new KeyboardUtil(act, ctx, edit1);
                keyboardUtil.setKeyboard(SYMBOLS);
                keyboardUtil.showKeyboard();
                disableShowSoftInput(edit1);
                return false;
            }
        });
    }

    /**
     * 禁止EditText弹出软件盘，光标依然正常显示。
     */
    public void disableShowSoftInput(EditText editText) {
        if (android.os.Build.VERSION.SDK_INT <= 10) {
            editText.setInputType(InputType.TYPE_NULL);
        } else {
            Class<EditText> cls = EditText.class;
            Method method;
            try {
                method = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
                method.setAccessible(true);
                method.invoke(editText, false);
            } catch (Exception e) {
            }

            try {
                method = cls.getMethod("setSoftInputShownOnFocus", boolean.class);
                method.setAccessible(true);
                method.invoke(editText, false);
            } catch (Exception e) {
            }
        }
    }
}
