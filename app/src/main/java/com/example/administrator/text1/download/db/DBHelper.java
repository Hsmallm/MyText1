package com.example.administrator.text1.download.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 数据库操作类
 * Created by Administrator on 2016/3/16.
 */
public class DBHelper extends SQLiteOpenHelper{
    private static final String DB_NAME = "download.db";//数据库名称
    private static final int VERSION = 1;//数据库版本号
    private static final String SQL_CREATE = "create table thread_info(_id integer primary key autoincrement,thread_id integer" +
            ",url text,start integer,end integer,finished integer)";//创建数据库表单
    private static final String SQL_DROP = "drop table if exists thread_info";//删除数据库表单

    public DBHelper(Context context) {//实例化数据库的构造方法
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {//创建数据库表单
        db.execSQL(SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {//更新数据库表单
        db.execSQL(SQL_DROP);
        db.execSQL(SQL_CREATE);
    }
}
