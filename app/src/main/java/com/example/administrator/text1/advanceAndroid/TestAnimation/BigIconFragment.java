package com.example.administrator.text1.advanceAndroid.TestAnimation;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.text1.R;

/**
 * @author HuangMing on 2018/2/3.
 */

public class BigIconFragment extends Fragment {

    public BigIconFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_big_icon, container, false);
    }
}
