package com.example.administrator.text1.newAndroid.listview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.administrator.text1.R;

/**
 * @author HuangMing on 2017/11/22.
 *         功能描述：使用ListView实现简单的纵向列表布局显示
 */

public class TestListViewActivity extends AppCompatActivity {

    private String[] data = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_test_listview);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(TestListViewActivity.this, android.R.layout.simple_list_item_1, data);
//        ListView listView = (ListView) findViewById(R.id.listView);
//        listView.setAdapter(adapter);
    }
}
