package com.example.administrator.text1.ui.testContentProvider;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.administrator.text1.R;

/**
 * @author HuangMing on 2018/11/29.
 *         Des：使用ContentProvider实现数据共享，主要是共享应用的Sqlite数据库，再一个应用中（本例的shareinfo）提供数据源（Sqlite数据库）并创建ContentProvider组件，
 *         ContentProvider组件主要对外（其他应用）提供访问数据的接口（Uri信息），其他应用（本例的other）通过这个接口（Uri信息）实现跨进程的方法调用
 *         ContentResolver：内容解析器，ContentResolver的作用，统一管理不同的ContentProvider间的操作，ContentResolver提供了与ContentProvider一样的增删改查方法。
 *         实现数据共享，基本使用步骤，可大体分以下三个步骤：
 *         <p>
 *         1、设计数据存储方式
 *         可选择文件和数据库进行存储，一般情况下会用数据库。
 *         2、创建ContentProvider子类，实现核心方法
 *         使用UriMatcher对象统一管理不同的Uri
 *         在onCreate方法初始化一些数据，注意不要耗时操作，可开启一个子线程执行。另外，只有外界调用了getContentResolver方法，才会触发onCreate的初始化。
 *         当数据发生了变化（比如增删改操作）时，需通过ContentResolver的notifyChange方法通知外界访问者ContentProvider中的数据已经改变了。
 *         3、外界通过ContentResolver和Uri对ContentProvider操作
 */

public class TestContentProvider extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "TestContentProvider";

    private Button btnContacts;
    private Button btnAdd;
    private Button btnDelete;
    private Button btnUpdate;
    private Button btnQuery;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_content_provider);

        btnContacts = (Button) findViewById(R.id.btn_contacts);
        btnAdd = (Button) findViewById(R.id.my_add);
        btnDelete = (Button) findViewById(R.id.delete);
        btnUpdate = (Button) findViewById(R.id.update);
        btnQuery = (Button) findViewById(R.id.query);

        btnContacts.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnQuery.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.my_add:
                add();
                com.seaway.android.common.toast.Toast.showToast(com.example.administrator.text1.newAndroid.other.MyApplication.getContext(), "添加成功！");
                break;
            case R.id.delete:
                delete();
                com.seaway.android.common.toast.Toast.showToast(com.example.administrator.text1.newAndroid.other.MyApplication.getContext(), "删除成功！");
                break;
            case R.id.update:
                update();
                com.seaway.android.common.toast.Toast.showToast(com.example.administrator.text1.newAndroid.other.MyApplication.getContext(), "更新成功！");
                break;
            case R.id.query:
                query();
                com.seaway.android.common.toast.Toast.showToast(com.example.administrator.text1.newAndroid.other.MyApplication.getContext(), "查询成功！");
                break;
            case R.id.btn_contacts:
                readContacts();
                break;
            default:
                break;
        }
    }

    /**
     * 系统内置ContentProvider的使用，以获取手机通讯录信息为例
     */
    private void readContacts() {
        int checkSelfPermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS);
        if (checkSelfPermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, 1);
        } else {
            Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
            try {
                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                        String phone = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        Log.d(TAG, "联系人信息：" + name + "---" + phone);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
    }

    private void add() {
        Uri uri = Uri.parse("content://com.example.administrator.text1.ui.testContentProvider.PersonProvider/person");
        ContentResolver contentResolver = this.getContentResolver();
        ContentValues values = new ContentValues();
        values.put("name", "HM");
        values.put("phone", "61662073");
        contentResolver.insert(uri, values);
    }

    private void delete() {
        Uri uri = Uri.parse("content://com.example.administrator.text1.ui.testContentProvider.PersonProvider/person");
        ContentResolver resolver = this.getContentResolver();
        resolver.delete(uri, null, null);
    }

    private void update() {
        Uri uri = Uri.parse("content://com.example.administrator.text1.ui.testContentProvider.PersonProvider/person");
        ContentResolver resolver = getContentResolver();
        ContentValues values = new ContentValues();
        values.put("name", "Hm+");
        values.put("phone", "61662073");
        resolver.update(uri, values, null, null);
    }

    private void query() {
        Uri uri = Uri.parse("content://com.example.administrator.text1.ui.testContentProvider.PersonProvider/person");
        ContentResolver resolver = getContentResolver();
        Cursor cursor = resolver.query(uri, new String[]{"name", "phone"}, null, null, null);
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String phone = cursor.getString(cursor.getColumnIndex("phone"));
            Log.e(TAG, name + phone);
        }
        //关闭光标cursor，避免内存泄漏
        cursor.close();
    }
}
