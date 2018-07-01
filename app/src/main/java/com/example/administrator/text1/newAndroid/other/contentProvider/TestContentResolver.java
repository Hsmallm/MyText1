package com.example.administrator.text1.newAndroid.other.contentProvider;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import com.example.administrator.text1.newAndroid.other.MyApplication;

/**
 * @author HuangMing on 2017/12/28.
 *         功能描述：内容提供器
 *         内容提供器的两种用法？？？
 *         1、使用现有的内容提供器来读取和操作相应程序上的数据
 *         2、创建自己的内容提供器，给我们应用程序的数据提供外部访问的接口
 *         Uri：为内容提供器中的数据建立了一个唯一的标识符，分authority和path两个部分组成
 *         authority：用于区分不同的应用程序，用包名
 *         path：用应用程序中的不同的表作为区分
 *         3、getContentResolver：得到ContentResolver实例从而可以访问内容提供器里面数据，并对此做出操作...
 */

public class TestContentResolver {

    Uri uri = Uri.parse("content://com.example.administrator.text1.newAndroid.other/table1");


    /**
     * 查询
     */
    private void query() {
        //相关参数简介依次是：某一程序下的某一张表、查询列名、where的约束条件、where约束条件占位符提供具体值、查询结果的排列方式
        Cursor cursor = MyApplication.getContext().getContentResolver().query(uri, new String[1], "", new String[0], "");
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String column1 = cursor.getString(cursor.getColumnIndex("column1"));
                int column2 = cursor.getInt(cursor.getColumnIndex("column2"));
            }
            cursor.close();
        }
    }

    /**
     * 插入
     */
    private void insert() {
        ContentValues contentValues = new ContentValues();
        contentValues.put("column1", "text");
        contentValues.put("column2", 1);
        MyApplication.getContext().getContentResolver().insert(uri, contentValues);
    }

    /**
     * 更新
     */
    private void update() {
        ContentValues contentValues = new ContentValues();
        contentValues.put("column1", "");
        MyApplication.getContext().getContentResolver().update(uri, contentValues, "column1 = ? and column2 = ?", new String[]{"text", "1"});
    }

    /**
     * 删除
     */
    private void delete() {
        MyApplication.getContext().getContentResolver().delete(uri, "column2=?", new String[]{"1"});
    }
}
