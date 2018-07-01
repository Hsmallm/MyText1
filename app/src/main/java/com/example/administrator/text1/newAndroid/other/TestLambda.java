package com.example.administrator.text1.newAndroid.other;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.administrator.text1.R;
import com.seaway.android.common.toast.Toast;

/**
 * @author HuangMing on 2017/11/29.
 *         功能描述：Lambda表达式为java8所提供的一个新特性，最低兼容Android2.3,基本上也覆盖了所以Android手机，适用于只有一个待实现方法的接口
 */

public class TestLambda extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_sharedpreferences);
        //1、开启子线程实例
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        }).start();
//
//        new Thread(() -> {
//
//        }).start();
//
//        //只有一个待实现方法的接口
//        Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        };
//
//        Runnable runnable1 = () -> {
//
//        };
//
//        //只有一个待实现方法的接口，切方法带参数有返回值
//        MyListener myListener = new MyListener() {
//            @Override
//            public String doSomething(String a, String b) {
//                return null;
//            }
//        };
//
//        MyListener listener = (String a, String b) -> {
//            String result = a + b;
//            return result;
//        };
//
//        MyListener listener2 = (a, b) -> {
//            String result = a + b;
//            return result;
//        };

//        hello((a, b) -> {
//            String result = a + b;
//            return result;
//        });
    }

    public void hello(MyListener listener) {
        String a = "Hello Lambda";
        String b = "666";
        String result = listener.doSomething(a, b);
        Toast.showToast(MyApplication.getContext(), result);
    }

    public interface MyListener {
        String doSomething(String a, String b);
    }
}
