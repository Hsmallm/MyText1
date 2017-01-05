package com.example.administrator.text1.ui.testOther;

import android.util.Log;

/**
 * 功能描述：测试通过继承一个抽象类，从而实现其父类抽象类里面的protected修饰符所修饰的方法
 * Created by HM on 2016/5/9.
 */
public class TextAbsract2Activity extends TextAbstractActivity {
    @Override
    protected void f1() {
        Log.e("f1","aaaaaa");
    }

    @Override
    protected void f2() {
        Log.e("f2","bbbbbb");
    }

    @Override
    protected void f3() {
        Log.e("f3","cccccc");
    }
}
