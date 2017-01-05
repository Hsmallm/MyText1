package com.example.administrator.text1.ui.testTab.testTabLayout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.text1.R;

/**
 * Created by hzhm on 2016/12/29.
 */

public class TestChildFragment extends Fragment {

    public static final String KEY = "key";
    private TextView txtTitle;

    public static TestChildFragment newFragment(int position) {
        TestChildFragment fragment = new TestChildFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(KEY, position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frg_test_child, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        txtTitle = (TextView) view.findViewById(R.id.test_view);
        if (getArguments() != null) {
            txtTitle.setText(getArguments().getInt(KEY) + "");
        }
    }
}
