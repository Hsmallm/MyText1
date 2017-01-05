package com.example.administrator.text1.utils.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * 功能描述：自定义水平布局RecycleView（即：一般的RecycleView为垂直布局，在这里我们实现水平布局的RecycleView）
 * 相关技术要点：1、实现在重写的setAdaptr里面设置addOnScrollListener滑动事件监听（重点在于滑动停止时获取中间视图）；
 * 2、同样要在onCreateViewHolder方法里面设置setOnClickListener点击事件监听
 * 3、在重写的setAdaptr里面我们要设置该控件的所以子视图、布局方式、可见个数
 * Created by HM on 2016/4/15.
 */
public class MyRecyclerView extends RecyclerView {

    public MyRecyclerView(Context context) {
        super(context);
    }

    public MyRecyclerView(Context context, AttributeSet arrts){
        super(context, arrts);
    }

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
    }

    /**
     * @param visiableItemCount      可见数目
     * @param adapter
     * @param onItemSelectedListener 选择监听对象
     */
    public void setAdapter(final int visiableItemCount, final Adapter adapter, final OnItemSelectedListener onItemSelectedListener) {

        /**
         * 1、实例化一个线性布局管理对象
         * 然后在measureChildWithMargins这个方法里面完成对所以子视图宽高的定义
         */
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext()) {
            @Override
            public void measureChildWithMargins(View child, int widthUsed, int heightUsed) {
                super.measureChildWithMargins(child, widthUsed, heightUsed);
                child.measure(MeasureSpec.makeMeasureSpec(getWidth() / visiableItemCount, MeasureSpec.EXACTLY), ViewGroup.LayoutParams.MATCH_PARENT);
            }
        };
        final int offset = visiableItemCount / 2;
        //设置其为线性水平布局
        layoutManager.setOrientation(HORIZONTAL);
        //设置进入LayoutManager
        setLayoutManager(layoutManager);
        ///2.设置adapter
        setAdapter(new Adapter() {
            @Override
            public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                ViewHolder holder = null;
                if (viewType == 1) {
                    View itemView = new View(getContext());
                    itemView.setVisibility(View.INVISIBLE);
                    holder = new ViewHolder(itemView) {
                        @Override
                        public String toString() {
                            return super.toString();
                        }
                    };
                } else {
                    holder = adapter.onCreateViewHolder(parent, viewType);
                    holder.itemView.setClickable(true);
                    holder.itemView.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //经过计算最终得出:当点击左右两边item时，在X轴上移动的距离正好是“一个item加空隙的偏移量”
                            smoothScrollBy((v.getLeft() + v.getRight() - getWidth()) / 2, 0);
//                            Log.e("xxxxx",(v.getLeft() + v.getRight() - getWidth()) / 2+"");
                        }
                    });
                }
                return holder;
            }


            @Override
            public int getItemViewType(int position) {
                if (position < offset || position > adapter.getItemCount() + offset - 1) {
                    return 1;
                } else {
                    return 2;
                }
            }

            @Override
            public void onBindViewHolder(ViewHolder holder, int position) {
                if (getItemViewType(position) == 1) {

                } else {
                    adapter.onBindViewHolder(holder, position - offset);
                }
            }

            @Override
            public int getItemCount() {
                return adapter.getItemCount() + offset * 2;
            }
        });
        //3、设置RecycleView的滑动监听
        addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //当屏幕停止滑动时
                if (newState == SCROLL_STATE_IDLE) {
                    View centerView =getCenterView(offset);
                    if(Math.abs(centerView.getLeft()+centerView.getRight()-getWidth()) < 2){//允许中间子视图误差在2px以内
                        onItemSelectedListener.onItemSelected(layoutManager.getPosition(centerView)-offset,centerView);
                    }
                    //如果大于这个偏差，则继续向前或是向后滑动使得视图居中
                    else {
                        smoothScrollBy((centerView.getLeft()+centerView.getRight()-getWidth())/2,0);
                    }
                }
            }

            //获取 RecyclerView 的滑动距离(注：dx，表示X轴上移动的距离;dy,表示Y轴上移动的距离)
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(dx==0 && dy==0){
                    smoothScrollBy(1,0);
                }
//                Log.e("dx:",dx+"");
//                Log.e("dy:",dy+"");
            }
        });
    }

    /**
     * 获取当前控件居中的子视图(即循环遍历出所以子视图，通过计算获取并得到中间的子视图)
     * @param offset
     * @return
     */
    private View getCenterView(int offset) {
        View view = getChildAt(0);
        //最小距离
        int minDistance = Math.abs(view.getLeft() + view.getRight() - getWidth());
        Log.e("minDistance",view.getLeft() + view.getRight() - getWidth()+"");
        for (int i = 1; i < getChildCount(); i++) {
            int abs = Math.abs(getChildAt(i).getLeft() + getChildAt(i).getRight() - getWidth());
            Log.e("abs",abs+""+" ;i: "+i);
            if (minDistance == abs) {
                //getLayoutManager().getPosition(view):表示当前视图position位置
                if (getLayoutManager().getPosition(view) < offset) {
                    Log.e("getPosition(view):",getLayoutManager().getPosition(view)+"");
                    return getChildAt(i);
                } else {
                    return getChildAt(i - 1);
                }
            }
            if (minDistance > abs) {
                view = getChildAt(i);
                minDistance = Math.abs(getChildAt(i).getLeft() + getChildAt(i).getRight() - getWidth());
                Log.e("minDistance",minDistance+"");
            } else {
                break;
            }
        }
        return view;
    }

    /**
     * 创建一个选择监听接口
     */
    public interface OnItemSelectedListener {
        void onItemSelected(int position, View view);
    }
}
