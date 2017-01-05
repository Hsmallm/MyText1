package com.example.administrator.text1.ui.testRecycleView;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.text1.R;

/**
 * Created by hzhm on 2016/11/1.
 *
 * 功能描述：RecyclerView与GridLayoutManager实现复杂的列表布局
 * 相关技术点：
 *      1、GridLayoutManager的两种滚动方式：
 *            第一种：两个参数的构造函数，默认垂直滚动，item是先水平布局再垂直布局
 *            第二种：四个参数的构造函数，设置水平滚动，item是先垂直布局再水平布局
 *      2、RecyclerView.ItemDecoration设置Adapter里面item间距
 */

public class TestGridLayoutManager extends Activity {

    private TextView txtTitle;
    private RecyclerView recyclerView;
    //创建一个颜色数组
    private int[] colors = new int[]{Color.BLUE, Color.RED, Color.WHITE, Color.YELLOW, Color.GRAY, Color.GREEN, Color.DKGRAY};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_recycleview);

        txtTitle = (TextView) findViewById(R.id.test_recycleview_title);
        txtTitle.setText("测试GridLayoutManager相关使用");
        recyclerView = (RecyclerView) findViewById(R.id.test_recycleview);
        int spance = getResources().getDimensionPixelSize(R.dimen.test_gridlayoutmanager);
        recyclerView.addItemDecoration(new SpaceItemDecoration(spance));

        //第一种：两个参数的构造函数，默认垂直滚动，item是先水平布局再垂直布局
        GridLayoutManager manager = new GridLayoutManager(this, 3);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return 1;
            }
        });
        //第二种：四个参数的构造函数，设置水平滚动，item是先垂直布局再水平布局
        GridLayoutManager manager1 = new GridLayoutManager(this, 2, LinearLayoutManager.HORIZONTAL, false);
        manager1.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return 1;
            }
        });
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(new RecyclerView.Adapter() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = new View(TestGridLayoutManager.this);
                view.setLayoutParams(new RecyclerView.LayoutParams(200, 200));
                return new RecyclerView.ViewHolder(view) {
                    @Override
                    public String toString() {
                        return super.toString();
                    }
                };
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                holder.itemView.setBackgroundColor(colors[position % colors.length]);
            }

            @Override
            public int getItemCount() {
                return 20;
            }
        });
    }

    class SpaceItemDecoration extends RecyclerView.ItemDecoration {
        private int spance;

        SpaceItemDecoration(int spance) {
            this.spance = spance;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.left = spance;
            outRect.bottom = spance;
            if (parent.getChildLayoutPosition(view) % 3 == 0){
                outRect.left = 0;
            }
        }
    }
}
