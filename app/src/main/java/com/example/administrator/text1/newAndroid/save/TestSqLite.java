package com.example.administrator.text1.newAndroid.save;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.administrator.text1.R;
import com.example.administrator.text1.utils.ToastUtil;

import org.litepal.LitePal;

/**
 * @author HuangMing on 2017/11/24.
 */

public class TestSqLite extends AppCompatActivity {

    private MyDatabaseHelper myDatabaseHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_sharedpreferences);
        myDatabaseHelper = new MyDatabaseHelper(this, "BookStore", null, 2);
        Button btn = (Button) findViewById(R.id.btn);
        btn.setText("Create");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //创建或升级数据库...
                myDatabaseHelper.getWritableDatabase();
            }
        });
        //向表单中插入数据
        Button btn2 = (Button) findViewById(R.id.btn2);
        btn2.setText("AddTable");
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = myDatabaseHelper.getWritableDatabase();
                ContentValues contentValues = new ContentValues();
                contentValues.put("name", "dave");
                contentValues.put("author", "dave");
                contentValues.put("pages", "666");
                contentValues.put("price", 16.66);
                contentValues.clear();
                contentValues.put("name", "dave2");
                contentValues.put("author", "dave2");
                contentValues.put("pages", "666");
                contentValues.put("price", 16.66);
                db.insert("Book", null, contentValues);
                ToastUtil.showNormalToast("Add success!");
            }
        });
        //更新表单中的数据
        Button btn3 = (Button) findViewById(R.id.btn3);
        btn3.setText("UpdateTable");
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = myDatabaseHelper.getWritableDatabase();
                ContentValues contentValues = new ContentValues();
                contentValues.put("price", "18.88");
                db.update("Book", contentValues, "name = ?", new String[]{"The Da Vinci Code"});
                ToastUtil.showNormalToast("Update success!");
            }
        });

        //删除表单中的数据
        Button btn4 = (Button) findViewById(R.id.btn4);
        btn4.setText("DeleteTable");
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = myDatabaseHelper.getWritableDatabase();
                db.delete("Book", "pages > ?", new String[]{"500"});
                ToastUtil.showNormalToast("Update success!");
            }
        });

        //查询表单中的数据
        Button btn5 = (Button) findViewById(R.id.btn5);
        btn5.setText("queryTable");
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = myDatabaseHelper.getWritableDatabase();
                Cursor cursor = db.query("Book", null, null, null, null, null, null);
                if (cursor.moveToFirst()) {
                    do {
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        String author = cursor.getString(cursor.getColumnIndex("author"));
                        String pages = cursor.getString(cursor.getColumnIndex("pages"));
                    } while (cursor.moveToNext());
                }
                cursor.close();
                ToastUtil.showNormalToast("Update success!");
            }
        });
    }
}
