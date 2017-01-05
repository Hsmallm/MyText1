package com.example.administrator.text1.ui.testRecycleView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.text1.R;
import com.example.administrator.text1.model.ExampleModel;

import java.util.List;

/**
 * Created by hzhm on 2016/9/29.
 * 功能描述：悬浮Title列表adapter
 * 相关技术：1、itemView.setTag，给adapter里面不同类型的item打上不同的标签
 *           2、holder1.itemView.setContentDescription(exampleModel.sticky)：给显示的itemView设置相应的内容描述
 */

public class ContentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    public static final int FIRST_STICKY_VIEW = 1;
    public static final int HAS_STICKY_VIEW = 2;
    public static final int NONE_STICKY_VIEW = 3;

    private Context context;
    private List<ExampleModel> exampleModelList;

    public ContentAdapter(Context context,List<ExampleModel> exampleModelList){
        this.context = context;
        this.exampleModelList = exampleModelList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_list_item,parent,false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        RecyclerViewHolder holder1 = (RecyclerViewHolder) holder;
        ExampleModel exampleModel = exampleModelList.get(position);
        holder1.tvName.setText(exampleModel.name);
        holder1.tvGender.setText(exampleModel.gender);
        holder1.tvProfession.setText(exampleModel.profession);
        if(position == 0){
            holder1.tvStickyHeader.setVisibility(View.VISIBLE);
            holder1.tvStickyHeader.setText(exampleModel.sticky);
            holder1.itemView.setTag(FIRST_STICKY_VIEW);
        }else {
            //设置一个itemList中唯一的title，即itenList中第一个item给他设置title
            if(!TextUtils.equals(exampleModel.sticky,exampleModelList.get(position-1).sticky)){
                holder1.tvStickyHeader.setVisibility(View.VISIBLE);
                holder1.tvStickyHeader.setText(exampleModel.sticky);
                holder1.itemView.setTag(HAS_STICKY_VIEW);
            }else {//其他的item，title都设置为隐藏
                holder1.tvStickyHeader.setVisibility(View.GONE);
                holder1.itemView.setTag(NONE_STICKY_VIEW);
            }
        }
        holder1.itemView.setContentDescription(exampleModel.sticky);
    }

    @Override
    public int getItemCount() {
        return exampleModelList.size() == 0? 0:exampleModelList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        public TextView tvStickyHeader;
        public RelativeLayout rlContentWrapper;
        public TextView tvName;
        public TextView tvGender;
        public TextView tvProfession;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            tvStickyHeader = (TextView) itemView.findViewById(R.id.tv_title_header_view);
            rlContentWrapper = (RelativeLayout) itemView.findViewById(R.id.rl_content_wrapper);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvGender = (TextView) itemView.findViewById(R.id.tv_gender);
            tvProfession = (TextView) itemView.findViewById(R.id.tv_profession);
        }
    }
}
