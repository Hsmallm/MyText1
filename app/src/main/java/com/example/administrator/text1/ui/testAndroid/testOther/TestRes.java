package com.example.administrator.text1.ui.testAndroid.testOther;

import android.app.Activity;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.text1.R;

import static com.example.administrator.text1.R.array.array_colors;

/**
 * Created by hzhm on 2017/2/20.
 *
 * 功能描述：Android资源文件之---数组资源介绍
 * 1、资源数组文件 : 通常将数组定义在 /res/values/arrays.xml文件中;
 *    -- 根标签 : <resources> ;
 *    -- 子标签 : <array>, <string-array>, <integer-array>;
 * 2、资源数组类型 :  数组的资源的跟标签都是 <resources>, 不同类型的数组的子元素不同;
 *    -- 普通类型数组 : 使用<array>作为子元素标签;
 *    -- 字符串数组 : 使用<string-array>作为子元素标签;
 *    -- 整数数组 : 使用<integer-array>作为子元素标签;
 * 3、相关引用：
 *   a、XML文件中调用数组资源 : @ [packageName :] array/arrayName ;
 *   b、Java文件中调用数组资源 : [packageName . ]R.array.arrayName ;
 * 4、实例化相关数组的方式：
 * -- 获取实际普通数组 : TypeArray obtainTypedArray(int id), 根据普通数组资源名称获取实际普通数组, TypeArray类提供了getXxx(int index)方法获取指定索引的元素;
 * -- 获取字符串数组 : String[] getStringArray(int id), 根据字符串数组资源名称获取字符串数组;
 * -- 获取整数数组 : int[] getIntArray(int id), 根据整数数组资源名称获取实际的整数数组;

 */

public class TestRes extends Activity {

    private ListView mListView;
    private String[] arrayString;
    private int[] arrayInt;
    private TypedArray colors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_res);

        //实例化相关数据类型数组方式：
        Resources res = getResources();
        arrayString = res.getStringArray(R.array.array_string);
        arrayInt = res.getIntArray(R.array.array_int);
        colors = res.obtainTypedArray(array_colors);

        mListView = (ListView) findViewById(R.id.res_listview);
        MyAdapter myAdapter = new MyAdapter();
        mListView.setAdapter(myAdapter);
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return arrayString.length;
        }

        @Override
        public Object getItem(int position) {
            return arrayString[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = LayoutInflater.from(TestRes.this).inflate(R.layout.adapter_test_res, parent, false);
            TextView txt = (TextView) view.findViewById(R.id.item_txt);
            txt.setBackgroundColor(colors.getColor(position,0));
            txt.setText(arrayInt[position] +"、"+ arrayString[position]);
            return view;
        }
    }
}
