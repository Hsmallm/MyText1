package com.example.administrator.text1.newAndroid.listview;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.text1.R;
import com.example.administrator.text1.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author HuangMing on 2017/11/22.
 *         <p>
 *         功能描述：ListView数据加载优化，增强运行性能
 */

public class TestListViewActivity2 extends AppCompatActivity {

    private String[] data = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
    private List<Fruit> fruitList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_test_listview);
//        initFruits();
//        FruitAdapter fruitAdapter = new FruitAdapter(TestListViewActivity2.this, R.layout.adapter_test_listview_item, fruitList);
//        ListView listView = (ListView) findViewById(R.id.listView);
//        listView.setAdapter(fruitAdapter);
//        //设置ListView控件子视图点击事件监听
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                ToastUtil.showNormalToast(position + "");
//            }
//        });
    }

    private void initFruits() {
        for (int i = 0; i < 2; i++) {
            Fruit fruit1 = new Fruit("1", R.drawable.image1);
            fruitList.add(fruit1);
            Fruit fruit2 = new Fruit("2", R.drawable.image2);
            fruitList.add(fruit2);
            Fruit fruit3 = new Fruit("3", R.drawable.image3);
            fruitList.add(fruit3);
            Fruit fruit4 = new Fruit("4", R.drawable.image4);
            fruitList.add(fruit4);
            Fruit fruit5 = new Fruit("5", R.drawable.image5);
            fruitList.add(fruit5);
            Fruit fruit6 = new Fruit("6", R.drawable.image1);
            fruitList.add(fruit6);

        }
    }

    class FruitAdapter extends ArrayAdapter<Fruit> {

        private int resourceId;

        public FruitAdapter(@NonNull Context context, int resource, @NonNull List<Fruit> objects) {
            super(context, resource, objects);
            resourceId = resource;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            Fruit fruit = getItem(position);
            View view;
            Holder holder;
            //1、利用getView的convertView对之前加载的布局文件进行缓存，避免重新创建，造成性能下降
            if (null == convertView) {
                view = LayoutInflater.from(TestListViewActivity2.this).inflate(resourceId, parent, false);
                holder = new Holder(view);
                view.setTag(holder);
            } else {
                view = convertView;
                //2、新增一个Holder类对空间进行换成，避免重新创建，造成性能下降
                holder = (Holder) view.getTag();
            }
            holder.iv.setImageResource(fruit.getImageId());
            holder.tv.setText(fruit.getName());
            return view;
        }

        @Override
        public int getCount() {
            return fruitList.size();
        }
    }

    class Holder {

        private ImageView iv;
        private TextView tv;

        public Holder(View view) {
            iv = (ImageView) view.findViewById(R.id.imageView);
            tv = (TextView) view.findViewById(R.id.textView);
        }
    }
}
