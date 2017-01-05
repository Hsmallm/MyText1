package com.example.administrator.text1.ui.testTab.testTabLayout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.text1.R;
import com.example.administrator.text1.ui.testDragRecycleView.DragAdapter;
import com.example.administrator.text1.ui.testDragRecycleView.drag.DragItemCallBack;
import com.example.administrator.text1.ui.testDragRecycleView.drag.RecycleCallBack;
import com.example.administrator.text1.utils.ObjCacheUtil;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by hzhm on 2016/12/29.
 */

public class TestRankFragment extends Fragment implements RecycleCallBack{

    private RecyclerView recyclerView;
    private OnResultListener mListener;
    private DragAdapter mAdapter;
    private ArrayList<String> mList;
    private ItemTouchHelper mItemTouchHelper;

    /**
     * 点击相应的item之后，返回相应的结果监听...
     */
    public interface OnResultListener {
        void onResult(int position,ArrayList<String> Lists);
    }

    /**
     * 点击相应的item之后，返回相应的结果监听...
     * @param listener
     */
    public void setOnResultListener(OnResultListener listener) {
        mListener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frg_test_rank, container, false);
        return view;
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            mList.add("泰然金融" + i);
        }
        recyclerView = (RecyclerView) getActivity().findViewById(R.id.test_recycleview);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        //将实现的RecycleCallBack接口传入这个Adapter里面，完成对item的点击监听
        mAdapter = new DragAdapter(this, mList);

        //将实现的RecycleCallBack接口传入这个DragItemCallBack里面，完成对item的移动监听
        mItemTouchHelper = new ItemTouchHelper(new DragItemCallBack(this));
        mItemTouchHelper.attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(mAdapter);
//        recyclerView.setAdapter(new RecyclerView.Adapter<ViewHolder>() {
//            @Override
//            public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//                View view1 = LayoutInflater.from(getActivity()).inflate(R.layout.channel_item, parent, false);
//                return new ViewHolder(view1);
//            }
//
//            @Override
//            public void onBindViewHolder(ViewHolder holder, int position) {
//                holder.txt.setText("排序" + (position + 1));
//                holder.txt.setTag(position);
//            }
//
//            @Override
//            public int getItemCount() {
//                return 10;
//            }
//        });
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txt;

        public ViewHolder(View itemView) {
            super(itemView);
            txt = (TextView) itemView.findViewById(R.id.text);
            txt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final int position = (int) txt.getTag();
                    mListener.onResult(position,mList);
                    popFragment();
                }
            });
        }
    }



    @Override
    public void itemOnClick(int position, View view) {
//        final int position = (int) view.getTag();
        ObjCacheUtil.save("mList",mList);
        mListener.onResult(position,mList);
        popFragment();
    }

    @Override
    public void onMove(int from, int to) {
        synchronized (this) {
            if (to == 1 || to == 0) {
                return;
            }
            if (from > to) {//从大到小
                int count = from - to;
                for (int i = 0; i < count; i++) {
                    //-------list-- 将指定List集合中i处元素和j出元素进行交换
                    // i-- 要交换的一个元素的索引。
                    // j-- 要交换的其它元素的索引。
                    if (from - i - 1 == 1) {//保持固定位置不变
                        Collections.swap(mList, from - i, from - i - 2);
                    } else {
                        if (from - i != 1) {
                            Collections.swap(mList, from - i, from - i - 1);
                        }
                    }
                }
            }
            if (from < to) {//从小到大
                int count = to - from;
                for (int i = 0; i < count; i++) {
                    if (from + i + 1 == 1) {//保持固定位置不变
                        Collections.swap(mList, from + i, from + i + 2);
                    } else {
                        if (from + i != 1) {
                            Collections.swap(mList, from + i, from + i + 1);
                        }
                    }
                }
            }
            mAdapter.setData(mList);
            //-------实现item模块的拖拽、移动时的通知
            mAdapter.notifyItemMoved(from, to);
            //--------拖拽移动后，其删除光标的位置也要随之移动...
            mAdapter.show.clear();
            mAdapter.show.put(to, to);
        }
    }

    public void popFragment() {
        FragmentManager fm = getFragmentManager();
        fm.popBackStack();
    }
}
