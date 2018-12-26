package com.example.administrator.text1.ui.testContentProvider;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * @author HuangMing on 2018/11/29.
 */

public class SqlLiteHelper {

    private Context context;
    private DbOpenHelper helper = null;

    public SqlLiteHelper(Context context) {
        helper = new DbOpenHelper(context);
    }

    /**
     * 增
     *
     * @param personBean
     */
    public void save(PersonBean personBean) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("insert into person(name,phone) values(?,?)", new Object[]{personBean.getName(), personBean.getPhone()});
        db.close();
    }

    /**
     * 删
     *
     * @param id
     */
    public void delete(int id) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("delete from person where personid=?", new Integer[]{id});
        db.close();
    }

    /**
     * 改
     *
     * @param personBean
     */
    public void update(PersonBean personBean) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("update person set name=?,phone=? where personid=?", new Object[]{personBean.getName(), personBean.getPhone(), personBean.getId()});
        db.close();
    }

    /**
     * 查
     *
     * @param personId
     * @return
     */
    public PersonBean find(int personId) {
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from person", null);
        if (cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex("personid"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String phone = cursor.getString(cursor.getColumnIndex("phone"));
            return new PersonBean(personId, name, phone);
        }
        cursor.close();
        return null;
    }
}
