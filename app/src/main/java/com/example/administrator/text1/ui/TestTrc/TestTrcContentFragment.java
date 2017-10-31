package com.example.administrator.text1.ui.TestTrc;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.text1.R;

/**
 * Created by hzhm on 2017/5/20.
 */

public class TestTrcContentFragment extends android.support.v4.app.Fragment{

    private static final String KEY = "key";

    private TextView contentTv;

    private String contentType;

    public static TestTrcContentFragment newInstance(String contentType){
        TestTrcContentFragment fragment = new TestTrcContentFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY,contentType);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_new_trc_content,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        contentTv = (TextView) getActivity().findViewById(R.id.content);

        contentType = getArguments().get(KEY).toString();
        if(contentType.equals("1")){
            contentTv.setText("为你推荐");
            contentTv.setBackgroundColor(Color.parseColor("#27a1e5"));
        }else if (contentType.equals("2")){
            contentTv.setText("母婴专区");
            contentTv.setBackgroundColor(Color.parseColor("#034f79"));
        }else if (contentType.equals("3")){
            contentTv.setText("美容美妆");
            contentTv.setBackgroundColor(Color.parseColor("#69AC7D"));
        }else if (contentType.equals("4")){
            contentTv.setText("营养保健");
            contentTv.setBackgroundColor(Color.parseColor("#555555"));
        }else if (contentType.equals("5")){
            contentTv.setText("全球美食");
            contentTv.setBackgroundColor(Color.parseColor("#FFFF00"));
        }else if (contentType.equals("6")){
            contentTv.setText("数码家电");
            contentTv.setBackgroundColor(Color.parseColor("#f25a2b"));
        }
    }
}
