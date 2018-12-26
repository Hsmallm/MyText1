package com.example.administrator.text1.ui.testOther;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.administrator.text1.R;
import com.example.administrator.text1.utils.SwipeRefreshLayoutUtil;

/**
 * @author HuangMing on 2018/7/1.
 */

public class TestSwipeRefreshLayout extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView tvTitle;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_swiperefresh);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        SwipeRefreshLayoutUtil.initStyle(swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
    }

    @Override
    public void onRefresh() {

    }
}
