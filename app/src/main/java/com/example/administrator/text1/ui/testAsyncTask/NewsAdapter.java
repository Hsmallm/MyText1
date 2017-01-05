package com.example.administrator.text1.ui.testAsyncTask;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.text1.R;
import com.example.administrator.text1.model.NewSBean;

import java.util.List;

/**
 * 重点功能描述：在图片加载显示时，给Item项设置OnScrollListener(onScrollStateChanged:为滚动状态改变回调，onScroll：为滚动回调)
 * 使其在停止滚动时进行加载显示，滚动是停止加载
 * 问题一：当ListView里面的Item过于复杂时，我们滑动ListView会有卡顿的xianxiang
 * 分析：这主要是应为，我们的图片的加载虽然是在异步进行操作的，但是最终加载完成还是会显示在UI主线程，及会重新刷新主线程，这时如果ListView正在滚动时，就会有卡顿的现象
 * 问题解决：ListView在滑动停止时，才进行加载可见项,ListView在滑动时，则停止加载一切加载操作
 * Created by hzhm on 2016/7/8.
 */
@TargetApi(Build.VERSION_CODES.M)
public class NewsAdapter extends BaseAdapter implements AbsListView.OnScrollListener {

    private List<NewSBean> mList;
    private LayoutInflater inflater;
    private ImageLoader mImageLoader;
    private int mStart, mEnd;
    public static String[] URLS;
    private boolean mFirst;

    public NewsAdapter(Context context, List<NewSBean> list, ListView listView) {
        mFirst = true;
        mList = list;
        inflater = LayoutInflater.from(context);
        mImageLoader = new ImageLoader(listView);
        URLS = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            URLS[i] = list.get(i).newsIconUrl;
        }
        //给listView设置滑动监听
        listView.setOnScrollListener(this);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.adapter_test_asynctask_item, null);
            viewHolder.ivIcon = (ImageView) convertView.findViewById(R.id.asy_image);
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.asy_title);
            viewHolder.tvContent = (TextView) convertView.findViewById(R.id.asy_content);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.ivIcon.setImageResource(R.drawable.trphoto);
        String url = mList.get(position).newsIconUrl;
        ///(注：给ImageView设置tag标签，解决listView缓存机制的问题)
        viewHolder.ivIcon.setTag(url);
        //开启先线程实现图片的加载显示
//        mImageLoader.showImageByThread(viewHolder.ivIcon,url);
        //使用AsyncTask实现图片的加载显示
//        mImageLoader.showImageByAsyncTask(viewHolder.ivIcon, url);
        viewHolder.tvTitle.setText(mList.get(position).newsTitle);
        viewHolder.tvContent.setText(mList.get(position).newsContent);
        return convertView;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        //当滚动停止时，加载可见项
        if (scrollState == SCROLL_STATE_IDLE) {
            mImageLoader.loadImage(mStart, mEnd);
        } else {
            //当滚动开始时，停止任务
            mImageLoader.cancleAllTask();
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        mStart = firstVisibleItem;
        mEnd = firstVisibleItem + visibleItemCount;
        //设置首次显示第一屏数据
        if (mFirst && visibleItemCount > 0) {
            mImageLoader.loadImage(mStart, mEnd);
            mFirst = false;
        }
    }

    class ViewHolder {
        private TextView tvTitle, tvContent;
        private ImageView ivIcon;
    }
}