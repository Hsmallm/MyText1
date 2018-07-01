package com.example.administrator.text1.newAndroid.other.contentProvider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * @author HuangMing on 2017/12/28.
 *         功能描述： 创建自己的内容提供器，给我们应用程序的数据提供外部访问的接口
 *         一、URI的两中标准写法？？？(组成结构：content、包名、表单)
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

public class TestMyContentProvider extends ContentProvider {

    public static final int TABLE1_DIR = 0;
    public static final int TABLE1_ITEM = 1;
    public static final int TABLE2_DIR = 2;
    public static final int TABLE2_ITEM = 3;

    private static UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI("com.example.administrator.text1.newAndroid.other.contentProvider.runningPremission", "table1", TABLE1_DIR);
        uriMatcher.addURI("com.example.administrator.text1.newAndroid.other.contentProvider.runningPremission", "table1/#", TABLE1_ITEM);
        uriMatcher.addURI("com.example.administrator.text1.newAndroid.other.contentProvider.runningPremission", "table2", TABLE2_DIR);
        uriMatcher.addURI("com.example.administrator.text1.newAndroid.other.contentProvider.runningPremission", "table2/#", TABLE2_ITEM);
    }

    @Override


    public boolean onCreate() {
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        switch (uriMatcher.match(uri)) {
            case TABLE1_DIR:

                break;
            case TABLE1_ITEM:


                break;
            case TABLE2_DIR:

                break;
            case TABLE2_ITEM:

                break;
            default:
                break;
        }
        return null;
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
            case TABLE1_DIR:
                return "vnd.android.cursor.dir/vnd.com.example.app.provider.table1";
            case TABLE1_ITEM:
                return "vnd.android.cursor.item/vnd.com.example.app.provider.table1";
            case TABLE2_DIR:
                return "vnd.android.cursor.dir/vnd.com.example.app.provider.table2";
            case TABLE2_ITEM:
                return "vnd.android.cursor.ietm/vnd.com.example.app.provider.table2";
            default:
                break;
        }
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
