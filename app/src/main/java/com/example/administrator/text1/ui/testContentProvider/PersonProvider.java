package com.example.administrator.text1.ui.testContentProvider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * @author HuangMing on 2018/11/29.
 *         Des：ContentProvider主要用于在不同的应用程序间实现数据共享的功能，允许一个程序访问另外一个程序中的数据，还能保证数据访问的安全性。
 *         ContentProvider相当于进程间的搬运工，对数据一系列的操作（CRUD）
 */

public class PersonProvider extends ContentProvider {

    private DbOpenHelper openHelper;
    private static final UriMatcher MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
    private static final int PERSONS = 1;
    private static final int PERSON = 2;

    /**
     * MIME类型命名规范

     必须以vnd为开头
     如果URI以路径为结尾，则后接android.cursor.dir/，如果URi以记录id为结尾，则后接android.cursor.item/
     最后接上vnd.<授权信息Authority>.<路径Path>
     示例：

     //URi以路径Path为结尾：content://com.hzw.progress/Book
     vnd.android.cursor.dir/vnd.com.hzw.progress.Book

     //URi以id为结尾：content://com.hzw.progress/Book/1
     vnd.android.cursor.item/vnd.com.hzw.progress.1
     */
    static {
        MATCHER.addURI("com.example.administrator.text1.ui.testContentProvider.PersonProvider", "person", PERSONS);
        //* 根据pesonid来删除记录
        MATCHER.addURI("com.example.administrator.text1.ui.testContentProvider.PersonProvider", "person/#", PERSON);
    }

    /**
     * ContentProvider初始化被调用
     * 一般用于创建数据库或者升级等操作，只有在ContentResolver访问的时候，才会触发onCreate方法
     * 此方法是主线程执行，不能做耗时操作
     *
     * @return true:ContentProvider初始化成功，false则失败
     */
    @Override
    public boolean onCreate() {
        openHelper = new DbOpenHelper(this.getContext());
        return false;
    }

    /**
     * 查询数据 -- 查
     *
     * @param uri           根据uri查询具体哪张数据表
     * @param projection    确定查询表中哪些列 ，传null则返回所有的列
     * @param selection     确定查询哪行，传null这返回所有的行
     * @param selectionArgs 与selection类似
     * @param sortOrder     用于对查询结果进行排序，传null则使用默认的排序方式，也可以是无序的。
     * @return 查询的返回值，是个Cursor对象，在取完数据需进行关闭。否则会内存泄漏
     */
    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase sqLiteDatabase = openHelper.getReadableDatabase();
        switch (MATCHER.match(uri)) {
            case 1:
                //查询表单下面所有的数据
                return sqLiteDatabase.query("person", projection, selection, selectionArgs, null, null, sortOrder);
            case 2:
                //查询表单下面对应id的数据
                long rowid = ContentUris.parseId(uri);
                String where = "personid=" + rowid;
                if (selection != null && "".equals(selection.trim())) {
                    where = selection + "and" + where;
                }
                return sqLiteDatabase.query("person", projection, where, selectionArgs, null, null, sortOrder);
            default:
                break;
        }
        return null;

    }

    /**
     * 返回指定内容URL的MIME类型
     *
     * @param uri 具体Url
     * @return MIME类型 比如图片、视频等等，可直接返回null
     */
    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (MATCHER.match(uri)) {
            case 1:
                return "vnd.android.cursor.dir/person";
            case 2:
                return "vnd.android.cursor.item/person";
            default:
                break;
        }
        return null;
    }

    /**
     * 插入一条新数据（添加数据）--增
     *
     * @param uri    根据uri插入到具体哪张数据表
     * @param values ContentValues底层是key-value键值对结构，使用HashMap实现的
     *               key：表示列名，value：表示行名，如果value为空，在表中则是空行，无内容
     * @return 添加成功后，返回这条新数据的uri。
     */
    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        SQLiteDatabase sqLiteDatabase = openHelper.getWritableDatabase();
        switch (MATCHER.match(uri)) {
            case 1:
                long rowid = sqLiteDatabase.insert("person", "name", values);
                return ContentUris.withAppendedId(uri, rowid);

            default:
                break;
        }
        return null;
    }

    /**
     * 删除数据 -- 删
     *
     * @param uri           根据uri删除哪张表的数据
     * @param selection     根据条件删除具体哪行数据
     * @param selectionArgs 与selection类似
     * @return 返回被删除的行数。
     */
    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase sqLiteDatabase = openHelper.getWritableDatabase();
        switch (MATCHER.match(uri)) {
            case 1:
                //删除表单下面所有
                return sqLiteDatabase.delete("person", selection, selectionArgs);
            case 2:
                //删除表单下面对应id的数据
                long rowid = ContentUris.parseId(uri);
                String where = "personid=" + rowid;
                if (selection != null && "".equals(selection.trim())) {
                    where = selection + "and" + where;
                }
                return sqLiteDatabase.delete("person", where, selectionArgs);
            default:
                break;
        }
        return 0;
    }

    /**
     * 更新数据 -- 改
     *
     * @param uri           根据Uri修改具体哪张表的数据
     * @param values        与insert的ContentValues一样，key是列名，若传入的value为空，则会删除原来的数据置空。
     * @param selection     选择符合该条件的行数据进行修改
     * @param selectionArgs 与selection类似
     * @return 更新的行数
     */
    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase sqLiteDatabase = openHelper.getWritableDatabase();
        switch (MATCHER.match(uri)) {
            case 1:
                //更新表单下面所有
                return sqLiteDatabase.update("person", values, selection, selectionArgs);
            case 2:
                //更新表单下面对应id的数据
                long rowid = ContentUris.parseId(uri);
                String where = "personid=" + rowid;
                if (selection != null && "".equals(selection.trim())) {
                    where = selection + "and" + where;
                }
                return sqLiteDatabase.update("person", values, where, selectionArgs);
            default:
                break;
        }
        return 0;
    }
}
