package com.example.administrator.text1.ui.testRecycleView;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.text1.R;
import com.seaway.android.common.toast.Toast;

/**
 * Created by hzhm on 2016/9/27.
 * 功能描述：RecycleView实现自带Header头部标题栏的Grid布局
 * 重点技术：1、gridLayoutManager.setSpanSizeLookup:设置空间大小监听
 *           2、SpanCount与SpanSize含义与区别：SpanCount,表示正常布局时，一行显示多少列；SpanZize,表示当前item一行所占用的空间大小
 *           3、获取RecyclerView控件里面可视视图的最上面和最下面一个的position
 *              final LinearLayoutManager m = (LinearLayoutManager) contactsListRecyclerView.getLayoutManager();
 *              int firstPosition = m.findFirstVisibleItemPosition();
 *              int lastPosition = m.findLastVisibleItemPosition();
 */

public class TestHeaderRecycleView extends Activity {

    private TextView txtTitle;
    private RecyclerView recyclerView;
    private int[] colors = new int[]{Color.BLUE, Color.RED, Color.WHITE, Color.YELLOW, Color.GRAY, Color.GREEN, Color.DKGRAY};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_recycleview);

        txtTitle = (TextView) findViewById(R.id.test_recycleview_title);
        txtTitle.setText("测试RecycleView实现自带Header的Grid布局");

        recyclerView = (RecyclerView) findViewById(R.id.test_recycleview);
        //实例化一个网格布局管理对象(注：SpanCount，表示这一行显示多少列)
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        //设置表格布局空间大小监听（注：SpanSize,表示所占的空间大小，例如：当SpanCount：3，SpanSize也为3时，则表示这条数据将占据整行，即为一列）
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                //当，当前item为0或是10,SpanSize为3,则为整行显示，即为一行一列；其他item,SpanSize为1，即为一行3列
                if (position == 0 || position == 10) return 3;
                else return 1;
            }
        });
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(new RecyclerView.Adapter() {

            @Override
            public int getItemViewType(int position) {
                if (position == 0 || position == 10)
                    return 1;
                else return 2;
            }

            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                if (viewType == 1) {
                    //实例化一个视图对象，并设置他的布局属性，即为该视图的宽高大小
                    View view = new View(TestHeaderRecycleView.this);
                    view.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200));
                    //再返回一个实例化的ViewHodler对象
                    return new RecyclerView.ViewHolder(view) {
                        @Override
                        public String toString() {
                            return super.toString();
                        }
                    };
                } else {
                    View view = new View(TestHeaderRecycleView.this);
                    view.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 500));
                    return new RecyclerView.ViewHolder(view) {
                        @Override
                        public String toString() {
                            return super.toString();
                        }
                    };
                }
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

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int aa = linearLayoutManager.findFirstVisibleItemPosition();
                if(aa == 1){
                    Toast.showToast(TestHeaderRecycleView.this,"Success!!!");
                }
            }
        });
    }
}
