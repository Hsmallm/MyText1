package com.example.administrator.text1.ui.testSlide;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.text1.R;
import com.example.administrator.text1.utils.view.TextUIDefaultDialogHelper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 功能描述：测试滑动删除功能
 * Created by HM on 2016/5/20.
 */
public class TextSlideListActivity extends Activity implements View.OnClickListener {

    private TextSlideListItemWrapper curSlideItem;
    private TextSlideListActivity.adapter madapter;

    private RecyclerView recyclerView;
    private TextView txt;
    private RelativeLayout bottom;
    private TextView txtRead;
    private TextView txtDelete;
    private List<String> list;

    class adapter extends RecyclerView.Adapter<Holder> {
        private View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                curSlideItem.closeMenu();
            }
        };
        private boolean selectionMode;
        //实例化一个HashSet对象（注：HashSet只能存储不重复的对象）
        private Set<String> selectedSet = new HashSet<>();

        //设置checkBox是否显示(并且在每次点击的时候，关闭相应的“删除”视图)
        public void setSelectionMode(boolean selectionMode) {
            this.selectionMode = selectionMode;
            //清空selectedSet集合里面的所以
            selectedSet.clear();
            if (null != curSlideItem) {
                curSlideItem.closeMenu();
            }
            notifyDataSetChanged();
        }

        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(TextSlideListActivity.this).inflate(R.layout.adapter_text_slidelist, parent,false);
            Holder holder = new Holder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(final Holder holder, int position) {
            holder.btn.setOnClickListener(listener);
            holder.btn.setTag(list.get(position));
            holder.txt.setText(list.get(position));
            //设置checkBox是否显示
            holder.checkBox.setVisibility(selectionMode ? View.VISIBLE : View.GONE);
            //设置itemView是否可以滑动
            holder.slideListItemWrapper.blockSlide(selectionMode);
            //给每个checkBox设置对应标记
            holder.checkBox.setTag(list.get(position));
            //设置recycleView的checkBox是否被选中（注：通过selectedSet里面包含的对应对象）
            holder.checkBox.setChecked(selectedSet.contains(list.get(position)));
            holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    //然后通过标记，再点击对象的ItemView时，从selectedSet里面添加或是移除
                    if (isChecked) {
                        selectedSet.add((String) buttonView.getTag());
                    } else {
                        selectedSet.remove(buttonView.getTag());
                    }
                    if (!selectedSet.isEmpty()) {
                        txtDelete.setText("删除");
                        txtRead.setText("标记为已读");
                    } else {
                        txtDelete.setText("全部删除");
                        txtRead.setText("全部标记为已读");
                    }
                }
            });
            holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (txt.getText().equals("完成")) {
                        if (holder.checkBox.isChecked()) {
                            holder.checkBox.setChecked(false);
                        } else {
                            holder.checkBox.setChecked(true);
                        }
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_slidelist);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        txt = (TextView) findViewById(R.id.txt);
        txt.setOnClickListener(this);
        bottom = (RelativeLayout) findViewById(R.id.bottom_item);
        txtRead = (TextView) findViewById(R.id.read);
        txtRead.setOnClickListener(this);
        txtDelete = (TextView) findViewById(R.id.delete);
        txtDelete.setOnClickListener(this);
        //设置recycleView的滑动监听，即当上下滑动recycleView时，关闭当前开启的“删除”视图
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (null != curSlideItem) {
                    curSlideItem.closeMenu();
                }
                curSlideItem = null;
            }
        });
        madapter = new adapter();
        list = new ArrayList<>();
        list.add("恭喜您！您已经成功投资1");
        list.add("恭喜您！您已经成功投资2");
        list.add("恭喜您！您已经成功投资3");
        list.add("恭喜您！您已经成功投资4");
        list.add("恭喜您！您已经成功投资5");
        list.add("恭喜您！您已经成功投资6");
        list.add("恭喜您！您已经成功投资7");
        list.add("恭喜您！您已经成功投资8");
        list.add("恭喜您！您已经成功投资9");
        list.add("恭喜您！您已经成功投资10");
        list.add("恭喜您！您已经成功投资11");
        list.add("恭喜您！您已经成功投资12");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(madapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt:
                if (txt.getText().equals("编辑")) {
                    txt.setText("完成");
                    //设置checkBox控件是否显示
                    madapter.setSelectionMode(true);
                    bottom.setVisibility(View.VISIBLE);
                    txtDelete.setText("全部删除");
                    txtRead.setText("全部标记为已读");
                } else if (txt.getText().equals("完成")) {
                    txt.setText("编辑");
                    madapter.setSelectionMode(false);
                    bottom.setVisibility(View.GONE);
                    txtDelete.setText("全部删除");
                    txtRead.setText("全部标记为已读");
                }
                break;
            case R.id.read:
                bottom.setVisibility(View.GONE);
                madapter.setSelectionMode(false);
                txtDelete.setText("全部删除");
                txtRead.setText("全部标记为已读");
                txt.setText("编辑");
                break;
            case R.id.delete:
                TextUIDefaultDialogHelper.showDefaultAskAlert(this, "确定要删除吗？", "取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TextUIDefaultDialogHelper.dialog.dismiss();
                        TextUIDefaultDialogHelper.dialog = null;
                    }
                }, "确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TextUIDefaultDialogHelper.dialog.dismiss();
                        TextUIDefaultDialogHelper.dialog = null;
                        bottom.setVisibility(View.GONE);
                        madapter.setSelectionMode(false);
                        txtDelete.setText("全部删除");
                        txtRead.setText("全部标记为已读");
                        txt.setText("编辑");
                    }
                });
                break;
            default:
                break;
        }
    }

    class Holder extends RecyclerView.ViewHolder {

        private TextSlideListItemWrapper slideListItemWrapper;
        private TextView txt;
        private CheckBox checkBox;
        private LinearLayout linearLayout;
        private Button btn;

        public Holder(View itemView) {
            super(itemView);
            slideListItemWrapper = (TextSlideListItemWrapper) itemView.findViewById(R.id.slide);
            //设置滑动控件监听，每次滑动时，如果当前curSlideItem不为空，则关闭当前“删除”视图
            slideListItemWrapper.setSlideListener(new TextSlideListItemWrapper.ViewSlideListener() {
                @Override
                public void onViewSliding(TextSlideListItemWrapper view) {
                    if (null != curSlideItem) {
                        curSlideItem.closeMenu();
                    }
                    curSlideItem = view;
                }
            });
            txt = (TextView) itemView.findViewById(R.id.item_type);
            checkBox = (CheckBox) itemView.findViewById(R.id.checkbox);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.adapter_msgCenter_layout_content);
            btn = (Button) itemView.findViewById(R.id.btn_delete);
        }
    }
}
