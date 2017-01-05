package com.example.administrator.text1.ui.testIndex.IndexByListView;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.example.administrator.text1.R;
import com.seaway.android.common.toast.Toast;

import java.util.ArrayList;

/**
 * Created by hzhm on 2016/6/21.
 * 功能描述：实现一个类似与微信联系人索引功能
 * 实现技术：ListView嵌套ListView，外加自定义ListView（即右边绘制有索引的ListView）
 */
public class TestIndexByListView extends Activity {

    private ArrayList<String> mItems;//一级列表数据
    private ArrayList<String> mChilds;//二级列表数据
    private IndexableListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_index);

        //初始化Items对象
        mItems = new ArrayList<>();
        mItems.add("A");
        mItems.add("B");
        mItems.add("C");
        mItems.add("D");
        mItems.add("E");
        mItems.add("F");
        mItems.add("G");
        mItems.add("H");
        mItems.add("I");
        mItems.add("J");
        mItems.add("K");
        mItems.add("L");
        mItems.add("M");
        mItems.add("N");
        mItems.add("O");
        mItems.add("P");
        mItems.add("Q");
        mItems.add("R");
        mItems.add("S");
        mItems.add("T");
        mItems.add("U");
        mItems.add("V");
        mItems.add("W");
        mItems.add("X");
        mItems.add("Y");
        mItems.add("Z");
        mItems.add("111111");
        mItems.add("666666");
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
        ContentAdapter adapter = new ContentAdapter();
        mListView = (IndexableListView) findViewById(R.id.index_rcy_listview);
        mListView.setAdapter(adapter);
        mListView.setFastScrollEnabled(true);

    }

    /**
     * 嵌套外置ListView的Adapter
     */
    private class ContentAdapter extends BaseAdapter implements SectionIndexer {

        private String mSections = "ABCDEFGHIJKLMNOPQRSTUVWXWZ#";

//        ///1、第一步：实现其ArrayAdapter构造方法，完成列表控件ListView数据的绑定
//        public ContentAdapter(Context context, int resource, List<String> mItems) {
////            super(context, resource, mItems);
//        }

        @Override
        public int getCount() {
            return mItems.size();
        }

        @Override
        public Object getItem(int position) {
            return mItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Holder holder;
            if (convertView == null) {
                holder = new Holder();
                convertView = LayoutInflater.from(TestIndexByListView.this).inflate(R.layout.adapter_test_index, null);
                holder.txtTitle = (TextView) convertView.findViewById(R.id.index_title);
                holder.indexListView = (ItemListView) convertView.findViewById(R.id.index_listview);
                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
            }
            holder.txtTitle.setText(mItems.get(position));
            ItemAdapter itemAdapter = new ItemAdapter();
            holder.indexListView.setAdapter(itemAdapter);
//            setListViewHeightBasedChildItem(holder.indexListView);
            return convertView;
        }

        ///2、第二步：实现SectionIndexer接口，获得右边索引对象，然后根据右边的索引查询定位ListView中的列表数据
        //获取数组对象，即为右边的索引对象
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
                int aa = mListView.getCount();
                //遍历所有ListView中的数据列表项
                for (int j = 0; j < mListView.getCount(); j++) {
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

    /**
     * 嵌套内置ListView的adapter
     */
    private class ItemAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mChilds.size();
        }

        @Override
        public Object getItem(int position) {
            return mChilds.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ItemHolder itemHolder;
            if (convertView == null) {
                itemHolder = new ItemHolder();
                convertView = LayoutInflater.from(TestIndexByListView.this).inflate(R.layout.adapter_test_item_index, parent, false);
                itemHolder.itemTitle = (TextView) convertView.findViewById(R.id.index_item_title);
                convertView.setTag(itemHolder);
            } else {
                itemHolder = (ItemHolder) convertView.getTag();
            }
            itemHolder.itemTitle.setText(mChilds.get(position));
            itemHolder.itemTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.showToast(TestIndexByListView.this, mChilds.get(position));
                }
            });
            return convertView;
        }
    }

    public final class Holder {
        private TextView txtTitle;
        private ItemListView indexListView;
    }

    public final class ItemHolder {
        private TextView itemTitle;
    }

    /**
     * 此方法是listview嵌套listview的核心方法：计算parentlistview item的高度。
     * （注：这里传入的listview,为为嵌套内置的listview,ChildListView）
     *
     * @param listView
     */
    public void setListViewHeightBasedChildItem(ListView listView) {
        ItemAdapter adapter = (ItemAdapter) listView.getAdapter();
        if (adapter == null) return;
        int totalHeight = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            View itemView = adapter.getView(i, null, listView);
            itemView.measure(0, 0);
            totalHeight += itemView.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (adapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
}
