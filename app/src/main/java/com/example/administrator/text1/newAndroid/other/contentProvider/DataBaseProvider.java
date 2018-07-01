package com.example.administrator.text1.newAndroid.other.contentProvider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.administrator.text1.newAndroid.save.MyDatabaseHelper;

/**
 * @author HuangMing on 2017/12/28.
 *         功能描述： 创建自己的内容提供器，给我们应用程序的数据提供外部访问的接口
 *         一、URI的两中标准写法？？？
 *         1、content：//com.example.app.provider/table1  以路径结尾，表示期待访问表中所有的数据
 *         2、content：//com.example.app.provider/table1/1  以id结尾，表示期待访问表中相应id的数据
 *         二、MIME类型3部分组成？？？
 *         1、必须要以vnd开头
 *         2、如uri以路径结尾，后接android.cursor.dir/;如uri以路径id，后接android.cursor.item/
 *         3、最后接上vnd.<authority>.<path>
 *         三、通配符？？？
 *         *：表示匹配任意长度的任意字符串 例如：content：//com.example.app.provider/*  表示可以匹配任意表单的URI
 *         #：表示匹配任意长度的数字 例如：content：//com.example.app.provider/table/1  表示可以匹配table1任意一行数据的URI
 */

public class DataBaseProvider extends ContentProvider {

    public static final int BOOK_DIR = 0;
    public static final int BOOK_ITEM = 1;
    public static final int CATEGORY_DIR = 2;
    public static final int CATEGORY_ITEM = 3;
    public static final String AUTHORITY = "com.example.administrator.text1.newAndroid.other.contentProvider.runningPremission";

    private static UriMatcher uriMatcher;
    private MyDatabaseHelper myDatabaseHelper;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI("com.example.administrator.text1.newAndroid.other.contentProvider.runningPremission", "book", BOOK_DIR);
        uriMatcher.addURI("com.example.administrator.text1.newAndroid.other.contentProvider.runningPremission", "book/#", BOOK_ITEM);
        uriMatcher.addURI("com.example.administrator.text1.newAndroid.other.contentProvider.runningPremission", "category", CATEGORY_DIR);
        uriMatcher.addURI("com.example.administrator.text1.newAndroid.other.contentProvider.runningPremission", "category/#", CATEGORY_ITEM);
    }

    @Override
    public boolean onCreate() {
        myDatabaseHelper = new MyDatabaseHelper(getContext(), "BookStore.db", null, 2);
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase db = myDatabaseHelper.getReadableDatabase();
        Cursor cursor = null;
        switch (uriMatcher.match(uri)) {
            case BOOK_DIR:
                cursor = db.query("Book", projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case BOOK_ITEM:
                String bookId = uri.getPathSegments().get(1);
                cursor = db.query("Book", projection, "id =?", new String[]{bookId}, null, null, sortOrder);
                break;
            case CATEGORY_DIR:
                cursor = db.query("Category", projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case CATEGORY_ITEM:
                String categoryId = uri.getPathSegments().get(1);
                cursor = db.query("Category", projection, "id =?", new String[]{categoryId}, null, null, sortOrder);
                break;
            default:
                break;
        }
        return cursor;
    }

    /**
     * 返回Uir所对应的MIME类型
     *
     * @param uri
     * @return
     */
    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)) {
            case BOOK_DIR:
                return "vnd.android.cursor.dir/vnd.com.example.app.provider.book";
            case BOOK_ITEM:
                return "vnd.android.cursor.item/vnd.com.example.app.provider.book";
            case CATEGORY_DIR:
                return "vnd.android.cursor.dir/vnd.com.example.app.provider.category";
            case CATEGORY_ITEM:
                return "vnd.android.cursor.ietm/vnd.com.example.app.provider.category";
            default:
                break;
        }
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        SQLiteDatabase db = myDatabaseHelper.getReadableDatabase();
        Uri uri1 = null;
        switch (uriMatcher.match(uri)) {
            case BOOK_DIR:
            case BOOK_ITEM:
                long newBookId = db.insert("Book", null, values);
                uri1 = Uri.parse("content://" + AUTHORITY + "/book/" + newBookId);
                break;
            case CATEGORY_DIR:
            case CATEGORY_ITEM:
                long newCategoryId = db.insert("Category", null, values);
                uri1 = Uri.parse("content://" + AUTHORITY + "/category/" + newCategoryId);
                break;
            default:
                break;
        }
        return uri1;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db = myDatabaseHelper.getReadableDatabase();
        int deleteRows = 0;
        switch (uriMatcher.match(uri)) {
            case BOOK_DIR:
                deleteRows = db.delete("Book", selection, selectionArgs);
                break;
            case BOOK_ITEM:
                String bookId = uri.getPathSegments().get(1);
                deleteRows = db.delete("Book", "id =?", new String[]{bookId});
                break;
            case CATEGORY_DIR:
                deleteRows = db.delete("Category", selection, selectionArgs);
                break;
            case CATEGORY_ITEM:
                String categoryID = uri.getPathSegments().get(1);
                deleteRows = db.delete("Category", "id =?", new String[]{categoryID});
                break;
            default:
                break;
        }
        return deleteRows;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db = myDatabaseHelper.getReadableDatabase();
        int updateRows = 0;
        switch (uriMatcher.match(uri)) {
            case BOOK_DIR:
                updateRows = db.update("Book", values, selection, selectionArgs);
                break;
            case BOOK_ITEM:
                String bookId = uri.getPathSegments().get(1);
                updateRows = db.update("Book", values, "id =?", new String[]{bookId});
                break;
            case CATEGORY_DIR:
                updateRows = db.update("Category", values, selection, selectionArgs);
                break;
            case CATEGORY_ITEM:
                String categoryId = uri.getPathSegments().get(1);
                updateRows = db.update("Book", values, "id =?", new String[]{categoryId});
                break;
            default:
                break;
        }
        return updateRows;
    }
}
