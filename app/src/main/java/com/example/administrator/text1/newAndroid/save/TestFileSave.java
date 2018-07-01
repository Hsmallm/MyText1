package com.example.administrator.text1.newAndroid.save;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.EditText;

import com.example.administrator.text1.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * @author HuangMing on 2017/11/24
 *         功能描述：Android系统最基本的数据存储方式之---文件存储：比较适用于一些简单的文本数据
 *
 */

public class TestFileSave extends AppCompatActivity {

    private EditText et;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_file_save);
        et = (EditText) findViewById(R.id.edit);
        String text = load();
        if (!TextUtils.isEmpty(text)) {
            et.setText(text);
            et.setSelection(text.length());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        String inputText = et.getText().toString();
        save(inputText);
    }

    /**
     * 使用文件存储读取文本文件
     *
     * @return
     */
    private String load() {
        FileInputStream in = null;
        BufferedReader reader = null;
        StringBuilder content = new StringBuilder();
        try {
            //实例化文件输入流对象
            in = openFileInput("data");
            //实例化文件输入读取对象
            reader = new BufferedReader(new InputStreamReader(in));
            //读取文件数据
            String line = "";
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return content.toString();
    }

    /**
     * 使用文件存储，存储文本数据
     *
     * @param text
     * @throws IOException
     */
    private void save(String text) {
        FileOutputStream out = null;
        BufferedWriter writer = null;
        try {
            //实例化文件输出流对象，设置文件存储名称、操作方式
            out = openFileOutput("data", Context.MODE_PRIVATE);
            //实例化文件写入对象
            writer = new BufferedWriter(new OutputStreamWriter(out));
            //写入文本
            writer.write(text);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
