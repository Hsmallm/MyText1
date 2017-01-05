package com.example.administrator.text1.ui.testIndex.IndexByRecycleView;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.text1.R;
import com.example.administrator.text1.api.AddressApi;
import com.example.administrator.text1.model.GroupsBean;
import com.example.administrator.text1.model.IndexModel;
import com.example.administrator.text1.model.address.FinalIndexModel;
import com.example.administrator.text1.utils.CharacterParser;
import com.example.administrator.text1.utils.http.RestCallback;
import com.example.administrator.text1.utils.http.ServerResultCode;
import com.seaway.android.common.toast.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by hzhm on 2016/9/14.
 * 功能描述：自定义一个索引控件，实现一个类似微信的索引效果
 * 相关技术：1、adapter.getPosition(s)：获取adapter里面对应字符的相对位置position
 * 2、获取RecyclerView控件里面可视视图的最上面和最下面一个的position
 * final LinearLayoutManager m = (LinearLayoutManager) contactsListRecyclerView.getLayoutManager();
 * int firstPosition = m.findFirstVisibleItemPosition();
 * int lastPosition = m.findLastVisibleItemPosition();
 * 3、scrollBy:它表示在控件的X、Y方向上各移动dx、dy距离；
 * （注：在对RecycleView控件里面的item进行指定的滚动到顶部时，有三种情况：
 * 1、如果当前选中文本position小于第一条可见视图，（表示文本位置position在可以视图以外,即在可视视图的上面）
 * 2、如果当前选中文本position大于第一条可见视图，小于最后一条可视视图，（表示文本位置position在可以视图以内,即在可视视图的中间）
 * 3、如果当前选中文本position大于最后一条可视视图，（表示文本位置position在可以视图以外,即在可视视图的下面）
 * <p>
 * 总结：1、3这两种情况属于同一类（即不在可见视图范围之内）：都需要先滚动到当前position,再与当前可视视图的最上面一条比较，再次滚动
 * 2、这一类（即在可见视图范围之内），直接与当前可视视图的最上面一条比较，进行位移）
 */
public class TestIndexByRecycleView extends Activity {

    private RecyclerView contactsListRecyclerView;
    private TextView popupTextView;
    private ListSideBar sideBar;
    private ContactsListAdapter contactsListAdapter;
    private ArrayList<GroupsBean> indexModelList = new ArrayList<>();
    private ArrayList<FinalIndexModel> finalIndexModelList = new ArrayList<>();


    /**
     * 汉字转换成拼音的类
     */
    private CharacterParser characterParser;
    private boolean move = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_list);

        contactsListRecyclerView = (RecyclerView) findViewById(R.id.contactsListRecyclerView);
        contactsListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        contactsListAdapter = new ContactsListAdapter();
        contactsListRecyclerView.setAdapter(contactsListAdapter);

        popupTextView = (TextView) findViewById(R.id.popupTextView);
        sideBar = (ListSideBar) findViewById(R.id.sidrbar);
        sideBar.setTextView(popupTextView);
        setListeners();

        characterParser = CharacterParser.getInstance();

        getListData();

        // 根据a-z进行排序源数据
        Collections.sort(indexModelList, new Comparator<GroupsBean>() {
            @Override
            public int compare(GroupsBean lhs, GroupsBean rhs) {
                return lhs.name.compareTo(rhs.name);
            }
        });
    }

    /**
     * 根据右边选中的索引文本与左边的recycleView进行相关联
     */
    private void setListeners() {
        // 设置右侧触摸监听
        sideBar.setOnTouchingLetterChangedListener(new ListSideBar.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String s) {
                //*** adapter.getPosition(s)：获取adapter里面对应字符的相对位置position
                final int position = contactsListAdapter.getPosition(s);
                Log.e("---setListeners---", s);
                Log.e("---thisPosition---", position + "");
                if (position != -1) {
                    //*** 获取RecyclerView控件里面可视视图的最上面和最下面一个的position
                    final LinearLayoutManager m = (LinearLayoutManager) contactsListRecyclerView.getLayoutManager();
                    int firstPosition = m.findFirstVisibleItemPosition();
                    int lastPosition = m.findLastVisibleItemPosition();
                    Log.e("---firstPosition---", firstPosition + "");
                    Log.e("---lastPosition---", lastPosition + "");

                    //1、如果当前选中文本position小于第一条可见视图，（表示文本位置position在可以视图以外,即在可视视图的上面）
                    if (position <= firstPosition) {
                        contactsListRecyclerView.scrollToPosition(position);
                        move = true;
                        //2、如果当前选中文本position大于第一条可见视图，小于最后一条可视视图，（表示文本位置position在可以视图以内,即在可视视图的中间）
                    } else if (position <= lastPosition) {
                        //*** scrollBy:它表示在控件的X、Y方向上各移动dx、dy距离
                        int aa = contactsListRecyclerView.getChildAt(position-firstPosition).getTop();
                        Log.e("------dis------","aa");
                        contactsListRecyclerView.scrollBy(0, aa);
                        //3、如果当前选中文本position大于最后一条可视视图，（表示文本位置position在可以视图以外,即在可视视图的下面）
                    } else {
                        contactsListRecyclerView.scrollToPosition(position);
                        move = true;
                    }

                    contactsListRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                        @Override
                        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                            super.onScrolled(recyclerView, dx, dy);
                            if (move) {
                                move = false;
                                int offset = position - m.findFirstVisibleItemPosition();
                                if (offset >= 0 && offset <= recyclerView.getChildCount()) {
                                    contactsListRecyclerView.scrollBy(0, contactsListRecyclerView.getChildAt(offset).getTop());
                                }
                            }
                        }
                    });
                }
            }
        });
    }

    public void getListData() {
        AddressApi.init();
        AddressApi.getIndexList(new RestCallback<IndexModel>() {
            @Override
            public void onSuccess(IndexModel model) {

                indexModelList = (ArrayList<GroupsBean>) model.groups;
                for (int i = 0; i < indexModelList.size(); i++) {
                    for (int j = 0; j < indexModelList.get(i).brands.size(); j++) {
                        FinalIndexModel finalModel = new FinalIndexModel();
                        if (j == 0) {
                            finalModel.setTitle(indexModelList.get(i).name);
                        }
                        finalModel.setName(indexModelList.get(i).brands.get(j).name);
                        finalModel.setUrl(indexModelList.get(i).brands.get(j).url);
                        finalIndexModelList.add(finalModel);
                    }
                }
                contactsListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFail(ServerResultCode serverResultCode, String errorMessage) {

            }
        });
    }

    private class ContactsListAdapter extends RecyclerView.Adapter<ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contacts_list, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            final FinalIndexModel model = finalIndexModelList.get(position);
            if (model.getTitle() == null) {
                holder.contactsListItemNameTextView.setVisibility(View.GONE);
            } else {
                holder.contactsListItemNameTextView.setVisibility(View.VISIBLE);
                holder.contactsListItemNameTextView.setText(model.getTitle());
            }
            holder.contactsListItemNumberTextView.setText(model.getName());
            holder.contactsListItemNumberTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.showToast(TestIndexByRecycleView.this, "model.getName()");
                }
            });
        }

        @Override
        public int getItemCount() {
            return finalIndexModelList.size();
        }


        public int getPosition(String s) {
            for (int i = 0; i < finalIndexModelList.size(); i++) {
                String sortKey = finalIndexModelList.get(i).getTitle();
                if (sortKey != null) {
                    if (sortKey.equals(s)) {
                        return i;
                    }
                }
            }
            return -1;
        }

    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public View rootView;
        public TextView contactsListItemNameTextView;
        public TextView contactsListItemNumberTextView;

        public ViewHolder(View v) {
            super(v);
            rootView = v;
            contactsListItemNameTextView = (TextView) v.findViewById(R.id.contactsListItemNameTextView);
            contactsListItemNumberTextView = (TextView) v.findViewById(R.id.contactsListItemNumberTextView);
        }
    }
}
