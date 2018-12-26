package com.example.administrator.text1.ui.testContentProvider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author HuangMing on 2018/11/29.
 *         Des：SQLiteOpenHelper，数据库创建帮助类，主要用于创建对应表单的数据库
 */

public class DbOpenHelper extends SQLiteOpenHelper {


    /**
     * 创建数据库操作
     * version：表示数据库编号
     *
     * @param context
     */
    public DbOpenHelper(Context context) {
        super(context, "jereh.db", null, 4);
    }

    /**
     * 数据库初始化，主要执行相关数据库表单创建
     * autoincrement：自动增量，即表示id自动增量
     *
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table person(personid integer primary key " +
                " autoincrement,name varchar(20),phone varchar(12))");
    }

    /**
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table person");
        onCreate(db);
    }
}
