package com.example.administrator.text1.ui.testRecycleView;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.text1.R;
import com.example.administrator.text1.utils.DataUtil;

/**
 * Created by hzhm on 2016/9/29.
 * 功能描述：测试RcycleView的findChildViewUnder()“向下查找子视图”方法
 */

public class TestRcyFindChildViewUnder extends Activity {

    private TextView title;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_recycleview);

        title = (TextView) findViewById(R.id.test_recycleview_title);
        title.setText("测试RcycleView的findChildViewUnder()方法...");
        recyclerView = (RecyclerView) findViewById(R.id.test_recycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new ContentAdapter(this,DataUtil.getData()));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                View v = recyclerView.findChildViewUnder(100,100);
                if(v != null){
                    TextView textView = (TextView)v.findViewById(R.id.tv_name);
                    String name = textView.getText().toString();
                    Log.e("scroll","child:"+name);
                }
            }
        });
    }
}
