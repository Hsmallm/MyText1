package com.example.administrator.text1.ui.testTab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.text1.R;

 /**
 * Created by hzhm on 2016/7/5.
 */
public class TabFragment extends Fragment {

    private int pos;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_tab_view4, container, false);
        TextView tv= (TextView) view.findViewById(R.id.tv);
        tv.setText(TestTab5.titles[pos]);
        return view;
    }

    public TabFragment(int pos) {
        this.pos = pos;
    }
}
