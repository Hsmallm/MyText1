package com.example.administrator.text1.ui.testIndex;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.example.administrator.text1.R;
import com.example.administrator.text1.ui.testIndex.IndexByListView.StringMatch;

import java.util.ArrayList;

/**
 * Created by hzhm on 2016/9/13.
 */
public class TestIndexRcyActivity extends Activity {

    private ArrayList<String> mItems;//一级列表数据
    private ArrayList<String> mChilds;//二级列表数据
    private IndexableRecycleView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_index);

        //初始化Items对象
        mItems = new ArrayList<>();
        mItems.add("B  bb");
        mItems.add("C  cc");
        mItems.add("DA  dd");
        mItems.add("A  ee");
        mItems.add("A  aa");
        mItems.add("111111");
        mItems.add("666666");
        mItems.add("F  ff");
        mItems.add("G  aa");
        mItems.add("H  bb");
        mItems.add("I  cc");
        mItems.add("爸  dd");
        mItems.add("K  ee");
        mItems.add("L  ff");
        mItems.add("M  aa");
        mItems.add("N  bb");
        mItems.add("O  cc");
        mItems.add("P  dd");
        mItems.add("Q  ee");
        mItems.add("R  ff");
        mItems.add("S  ss");
        mItems.add("T  cc");
        mItems.add("U  dd");
        mItems.add("V  ee");
        mItems.add("W  ff");
        mItems.add("X  cc");
        mItems.add("Y  dd");
        mItems.add("Z  ee");
        //排序
//        Collections.sort(mItems);

        //初始化mChilds对象
        mChilds = new ArrayList<>();
        mChilds.add("Aptamil 爱他美");
        mChilds.add("AOC");
        mChilds.add("Adidas 阿迪达斯");
        mChilds.add("Aptamil 爱他美");
        mChilds.add("AOC");
        mChilds.add("Adidas 阿迪达斯");


        //根据section获取position,直接使用ArrayAdapter,SectionIndexer接口
        ContentRcyAdapter adapter = new ContentRcyAdapter();
        mListView = (IndexableRecycleView) findViewById(R.id.index_rcy_listview);
        mListView.setLayoutManager(new LinearLayoutManager(TestIndexRcyActivity.this));
        mListView.setHasFixedSize(true);
        mListView.setAdapter(adapter);
        mListView.setmIsFastScrollEnable(true);

    }

    private class ContentRcyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements SectionIndexer {
        private String mSections = "ABCDEFGHIJKLMNOPQRSTUVWXWZ#";
        private int TYPE_HEADER = 0;
        private int TYPE_FOOTER = 1;

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            RecyclerView.ViewHolder holder;
            View view = LayoutInflater.from(TestIndexRcyActivity.this).inflate(R.layout.adapter_test_index_rcy_heard, parent, false);
            holder = new HeaderHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ((HeaderHolder) holder).bind(position);
        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }

        @Override
        public Object[] getSections() {
            String[] sections = new String[mSections.length()];
            //将每个Section作为一个单独的元素存到sections中
            for (int i = 0; i < mSections.length(); i++) {
                //从mSections中获取每一个单独的字符
                sections[i] = String.valueOf(mSections.charAt(i));
            }
            return sections;
        }

        //3、第三步：根据右边的索引查询定位ListView中的列表数据
        @Override
        public int getPositionForSection(int sectionIndex) {
            //从当前sectionIndex往前查，直到查到第一个item为止，查不到则不用进行定位
            //(注：i表示右边索引的位置,j表示左边listView列表项；索引原则：当选中的索引没有时，会不断向上查询，例如：当查询c，c没有查到时，会继续向上查询A、B)
            for (int i = sectionIndex; i >= 0; i--) {
                int aa = mListView.getChildCount();
                //遍历所有ListView中的数据列表项
                for (int j = 0; j < mListView.getChildCount(); j++) {
                    //查其他非字母：(当i为0时，即i等于第一个索引项时，则进行数字匹配查询)
                    if (i == mSections.length() - 1) {
                        for (int k = 0; k <= 9; k++) {
                            if (StringMatch.macth(String.valueOf(mItems.get(j).charAt(0)), String.valueOf(k))) {
                                return j;
                            }
                        }
                    } else {
                        //查字母
                        if (StringMatch.macth(String.valueOf(mItems.get(j).charAt(0)), String.valueOf(mSections.charAt(i)))) {
                            return j;
                        }
                    }
                }
            }
            return 0;
        }

        @Override
        public int getSectionForPosition(int position) {
            return 0;
        }
    }

    class HeaderHolder extends RecyclerView.ViewHolder {

        private TextView headerTxt;

        public HeaderHolder(View itemView) {
            super(itemView);
            headerTxt = (TextView) itemView.findViewById(R.id.index_rcy_title);
        }

        public void bind(int position) {
            headerTxt.setText(mItems.get(position));
        }
    }

    class FooterHolder extends RecyclerView.ViewHolder {

        private TextView footererTxt;

        public FooterHolder(View itemView) {
            super(itemView);
            footererTxt = (TextView) itemView.findViewById(R.id.index_item_title);
        }

        public void bind(int position) {
            footererTxt.setText(mChilds.get(position));
        }
    }

}
