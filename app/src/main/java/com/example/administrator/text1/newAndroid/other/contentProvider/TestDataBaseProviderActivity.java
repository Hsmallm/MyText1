package com.example.administrator.text1.newAndroid.other.contentProvider;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.administrator.text1.R;
import com.example.administrator.text1.newAndroid.other.LogUtil;

/**
 * @author HuangMing on 2017/12/29.
 *         功能描述：利用自己创建的内容提供器进行数据操作(增、删、改、查)...
 *         getContentResolver()：获取当前的内容提供器
 *
 */

public class TestDataBaseProviderActivity extends AppCompatActivity implements View.OnClickListener {

    private Button addBtn;
    private Button deleteBtn;
    private Button updateBtn;
    private Button queryBtn;

    private String newId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_database);

        addBtn = (Button) findViewById(R.id.add);
        deleteBtn = (Button) findViewById(R.id.delete);
        updateBtn = (Button) findViewById(R.id.update);
        queryBtn = (Button) findViewById(R.id.query);

        addBtn.setOnClickListener(this);
        deleteBtn.setOnClickListener(this);
        updateBtn.setOnClickListener(this);
        queryBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        //向数据库添加数据
        if (id == R.id.add) {
            Uri uri = Uri.parse("content://com.example.administrator.text1.newAndroid.other.contentProvider.runningPremission/book");
            ContentValues values = new ContentValues();
            values.put("name", "Kings");
            values.put("author", "JIMI");
            values.put("pages", "6868");
            values.put("price", "8686");
            Uri newUri = getContentResolver().insert(uri, values);
            newId = newUri.getPathSegments().get(1);
        } else if (id == R.id.delete) {
            //删除指定表单里面对应id的数据
            Uri uri = Uri.parse("content://com.example.administrator.text1.newAndroid.other.contentProvider.runningPremission/book/" + newId);
            getContentResolver().delete(uri, null, null);
        } else if (id == R.id.update) {
            //更新数据库指定表单对应id的数据
            Uri uri = Uri.parse("content://com.example.administrator.text1.newAndroid.other.contentProvider.runningPremission/book/" + newId);
            ContentValues values = new ContentValues();
            values.put("name", "Kings2");
            values.put("author", "JIMI2");
            values.put("pages", "68682");
            values.put("price", "86862");
            getContentResolver().update(uri, values, null, null);
        } else if (id == R.id.query) {
            //查询数据库对应表单数据
            Uri uri = Uri.parse("content://com.example.administrator.text1.newAndroid.other.contentProvider.runningPremission/book");
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    String name = cursor.getString(cursor.getColumnIndex("name"));
                    String author = cursor.getString(cursor.getColumnIndex("author"));
                    int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                    int price = cursor.getInt(cursor.getColumnIndex("price"));
                    LogUtil.d("TestDataBaseProviderActivity", "book name is " + name);
                    LogUtil.d("TestDataBaseProviderActivity", "book author is " + author);
                    LogUtil.d("TestDataBaseProviderActivity", "book pages is " + pages);
                    LogUtil.d("TestDataBaseProviderActivity", "book price is " + price);
                }
            }
        }
    }
}
