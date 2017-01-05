package com.example.administrator.text1.ui.testOther;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.text1.R;
import com.example.administrator.text1.utils.view.MyRecyclerView;
import com.seaway.android.common.toast.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能描述：自定义水平布局RecycleView（即：一般的RecycleView为垂直布局，在这里我们实现水平布局的RecycleView）
 * Created by HM on 2016/4/15.
 */
public class TextMyRecyclerViewActivity extends Activity{

    private MyRecyclerView myRecyclerView;
    private List<String> list;
    //标记选中item项
    private int selectPos;
    //创建匿名内部类，实例化一个RecyclerView.Adapter对象
    private RecyclerView.Adapter adapter = new RecyclerView.Adapter() {
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            TextView textView = new TextView(TextMyRecyclerViewActivity.this);
            return new RecyclerView.ViewHolder(textView){
                @Override
                public String toString() {
                    return super.toString();
                }
            };
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            TextView txt = (TextView) holder.itemView;
            if(selectPos == position){//选中的item项
                txt.setTextSize(22f);
                txt.setTextColor(Color.parseColor("#0086d1"));
            }else {
                txt.setTextSize(17f);
                txt.setTextColor(Color.parseColor("#808080"));
            }
            //设置文本高度，实现该控件中文本水平、垂直居中的效果
            txt.setHeight(60);
            txt.setGravity(Gravity.CENTER);
            txt.setText(list.get(position).toString());
        }

        @Override
        public int getItemCount() {
            return list == null?0:list.size();
        }
    };

    //创建匿名内部类，实例化一个MyRecyclerView.OnItemSelectedListener监听对象
    private MyRecyclerView.OnItemSelectedListener onItemSelectedListener = new MyRecyclerView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(int position, View view) {
            selectPos = position;
            myRecyclerView.getAdapter().notifyDataSetChanged();
            Toast.showToast(TextMyRecyclerViewActivity.this,position+"");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_myrecyclerview);

        myRecyclerView = (MyRecyclerView) findViewById(R.id.myRecyclerView);

        list = new ArrayList<>();
        list.add("新手尊享");
        list.add("i存-15天");
        list.add("i存-30天");
        list.add("i存-60天");
        list.add("i存-90天");
        list.add("u选-180天");
        list.add("u选-270天");
        list.add("u选-360天");
        myRecyclerView.setAdapter(3,adapter,onItemSelectedListener);
//        myRecyclerView给他延时0.5s向左滚动myRecyclerView.getWidth()/3控件三分之一的距离
        myRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                myRecyclerView.smoothScrollBy(myRecyclerView.getWidth()/3,0);
            }
        },500);

    }
}
