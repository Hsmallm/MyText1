package com.example.administrator.text1.ui.testIndex;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.text1.R;

/**
 * Created by hzhm on 2016/9/17.
 */
public class TestRecycleView extends Activity {


    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_recycleview);

        recyclerView = (RecyclerView) findViewById(R.id.test_recycleview);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position == 0)
                    return 3;
                else if (position < 9)
                    return 1;
                else if (position == 9)
                    return 3;
                else if ((position > 9 && position < 20)) {
                    return 1;
                }
                return 1;
            }
        });
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(new RecyclerView.Adapter<RecyclerView.ViewHolder>() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                if (viewType == 1) {
                    return new Holder1(LayoutInflater.from(TestRecycleView.this).inflate(R.layout.adapter_category1, parent, false));
                } else if (viewType == 2) {
                    return new Holder2(LayoutInflater.from(TestRecycleView.this).inflate(R.layout.adapter_category2, parent, false));
                } else {
                    return new Holder3(LayoutInflater.from(TestRecycleView.this).inflate(R.layout.adapter_category3, parent, false));
                }
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                if (position == 0) {
                    ((Holder1) holder).bind();
                } else if (position == 9) {
                    ((Holder1) holder).bind2();
                } else if (position > 0 && position < 9) {
                    if (position % 2 == 0) {
                        ((Holder2) holder).bind();
                    } else {
                        ((Holder2) holder).bind2();
                    }
                    ((Holder2) holder).bind();
                } else if (position > 9 && position < 20) {
                    if (position % 2 == 0) {
                        ((Holder3) holder).bind();
                    } else {
                        ((Holder3) holder).bind2();
                    }
                }
            }

            @Override
            public int getItemViewType(int position) {
                if (position == 0 || position == 9) {
                    return 1;
                } else if (position > 0 && position < 9) {
                    return 2;
                } else if (position > 9 && position < 20) {
                    return 3;
                } else return 0;
            }

            @Override
            public int getItemCount() {
                return 20;
            }
        });
    }

    class Holder extends RecyclerView.ViewHolder {
        public Holder(View itemView) {
            super(itemView);
        }
    }

    class Holder1 extends RecyclerView.ViewHolder {

        private LinearLayout lin;
        private TextView title;
        private TextView des;
        private View view;

        public Holder1(View itemView) {
            super(itemView);
            lin = (LinearLayout) itemView.findViewById(R.id.cay_lin);
            title = (TextView) itemView.findViewById(R.id.cay_title);
            des = (TextView) itemView.findViewById(R.id.cay_des);
            view = itemView.findViewById(R.id.cay_view);
        }

        public void bind() {
            ((LinearLayout.LayoutParams) lin.getLayoutParams()).topMargin = 0;
            title.setText("全部分类");
            des.setText("Classification");
            view.setVisibility(View.VISIBLE);
        }

        public void bind2() {
            ((LinearLayout.LayoutParams) lin.getLayoutParams()).topMargin = 20;
            title.setText("全球购物");
            des.setText("Destinations");
            view.setVisibility(View.GONE);
        }
    }

    class Holder2 extends RecyclerView.ViewHolder {

        private LinearLayout ll;
        private TextView title;
        private TextView des;

        public Holder2(View itemView) {
            super(itemView);
            ll = (LinearLayout) itemView.findViewById(R.id.cay2_ll);
            title = (TextView) itemView.findViewById(R.id.cay2_title);
            des = (TextView) itemView.findViewById(R.id.cay2_des);
        }

        public void bind() {
//            ((LinearLayout.LayoutParams) ll.getLayoutParams()).bottomMargin = 2;
//            ((LinearLayout.LayoutParams) ll.getLayoutParams()).rightMargin = 2;
//            ll.setBackgroundResource(R.drawable.my);
        }
        public void bind2() {
//            ((LinearLayout.LayoutParams) ll.getLayoutParams()).bottomMargin = 2;
//            ((LinearLayout.LayoutParams) ll.getLayoutParams()).rightMargin = 0;
//            ll.setBackgroundResource(R.drawable.my);
        }
    }

    class Holder3 extends RecyclerView.ViewHolder {

        private LinearLayout lin;
        private TextView img;

        public Holder3(View itemView) {
            super(itemView);
            lin = (LinearLayout) itemView.findViewById(R.id.cay3_lin);
            img = (TextView) itemView.findViewById(R.id.cay3_img);

        }

        public void bind() {
            ((LinearLayout.LayoutParams) lin.getLayoutParams()).leftMargin = 30;
            ((LinearLayout.LayoutParams) lin.getLayoutParams()).rightMargin = 0;
            img.setBackgroundResource(R.drawable.kd);
        }

        public void bind2() {
            ((LinearLayout.LayoutParams) lin.getLayoutParams()).leftMargin = 20;
            ((LinearLayout.LayoutParams) lin.getLayoutParams()).rightMargin = 30;
            img.setBackgroundResource(R.drawable.kd);
        }
    }
}
