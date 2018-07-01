package com.example.administrator.text1.newAndroid.save;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.administrator.text1.utils.ToastUtil;

/**
 * @author HuangMing on 2017/11/24.
 */

public class MyDatabaseHelper extends SQLiteOpenHelper {

    public static final String CREATE_BOOK = "create table Book(id integer primary key autoincrement,author text,price real,pages integer,name text)";
    public static final String CREATE_CATEGORY = "create table Category(id integer primary key autoincrement,category_name text,category_code integer)";
    private Context context;

    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BOOK);
        db.execSQL(CREATE_CATEGORY);
    }

    /**
     * 用于对数据库进行升级：如果发现数据库中已经存在当前两个表单，那就先删除，然后在重新调用onCreate方法重新创建这两个表单(注：要想调用此方法，那么当前数据库的版本号必须升级，即为
     * 要比之前数据库版本号要高)
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists Book");
        db.execSQL("drop table if exists Category");
        onCreate(db);
    }
}
