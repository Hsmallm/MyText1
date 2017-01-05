package com.example.administrator.text1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.text1.R;

import java.util.List;

/**
 * Created by Administrator on 2016/2/24.
 */
public class Main3Adapter extends BaseAdapter {

    private Context context;
    private List<String> list;

    public Main3Adapter(Context context, List<String> list){
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return null == list ? 0:list.size();
    }

    @Override
    public Object getItem(int position) {
        return null == list ? 0:list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(null == convertView){
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_main3, null);
            holder = new ViewHolder();
            holder.item = (TextView) convertView.findViewById(R.id.list_item);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.item.setText(list.get(position));
        return convertView;
    }

    private class ViewHolder{
        private TextView item;
    }
}
