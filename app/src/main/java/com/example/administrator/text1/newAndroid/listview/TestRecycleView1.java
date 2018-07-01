package com.example.administrator.text1.newAndroid.listview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.text1.R;
import com.example.administrator.text1.ui.testIndex.TestRecycleView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author HuangMing on 2017/11/23.
 *         功能描述：RecyclerView实现简单的横向数据列表，利用LayoutManage来管理自身的布局排列(相交与ListView交给自身进行的布局排列，RecycleView的可扩展性能更强)
 */


public class TestRecycleView1 extends AppCompatActivity {

    private List<Fruit> fruitList = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_listview);
        initFruit();
        FruitAdapter fruitAdapter = new FruitAdapter();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        //1、创建线性布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        //2、创建瀑布流布局管理器
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        recyclerView.setAdapter(fruitAdapter);
    }

    private void initFruit() {
        for (int i = 0; i < 2; i++) {
            Fruit fruit1 = new Fruit(getRandomLengthName("While"), R.drawable.image1);
            fruitList.add(fruit1);
            Fruit fruit2 = new Fruit(getRandomLengthName("Black"), R.drawable.image2);
            fruitList.add(fruit2);
            Fruit fruit3 = new Fruit(getRandomLengthName("Red"), R.drawable.image3);
            fruitList.add(fruit3);
            Fruit fruit4 = new Fruit(getRandomLengthName("Yellow"), R.drawable.image4);
            fruitList.add(fruit4);
            Fruit fruit5 = new Fruit(getRandomLengthName("blue"), R.drawable.image5);
            fruitList.add(fruit5);
            Fruit fruit6 = new Fruit(getRandomLengthName("Orange"), R.drawable.image1);
            fruitList.add(fruit6);
        }
    }

    private String getRandomLengthName(String name) {
        Random random = new Random();
        int length = random.nextInt(20) + 1;
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            stringBuilder.append(name);
        }
        return stringBuilder.toString();
    }

    class FruitAdapter extends RecyclerView.Adapter<Holder> {

        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new Holder(LayoutInflater.from(TestRecycleView1.this).inflate(R.layout.adapter_test_listview_item, parent, false));
        }

        @Override
        public void onBindViewHolder(Holder holder, int position) {
            Fruit fruit = fruitList.get(position);
            holder.iv.setImageResource(fruit.getImageId());
            holder.tv.setText(fruit.getName());
        }

        @Override
        public int getItemCount() {
            return fruitList.size();
        }
    }

    class Holder extends RecyclerView.ViewHolder {

        private ImageView iv;
        private TextView tv;

        public Holder(View itemView) {
            super(itemView);

            iv = (ImageView) itemView.findViewById(R.id.imageView);
            tv = (TextView) itemView.findViewById(R.id.textView);
        }
    }
}
