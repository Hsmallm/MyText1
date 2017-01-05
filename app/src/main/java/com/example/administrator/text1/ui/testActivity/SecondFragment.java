package com.example.administrator.text1.ui.testActivity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.text1.R;

/**
 * Created by hzhm on 2016/6/20.
 */
public class SecondFragment extends Fragment {

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.e("onAttach","--------SecondFragment-onAttach--------");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("onCreate","--------SecondFragment-onCreate--------");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e("onCreateView","--------SecondFragment-onCreateView--------");
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.activity_main,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e("onActivityCreated","--------SecondFragment-onActivityCreated--------");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e("onStart","--------SecondFragment-onStart--------");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("onResume","--------SecondFragment-onResume--------");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("onPause","--------SecondFragment-onPause--------");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e("onStop","--------SecondFragment-onStop--------");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("onDestroy","--------SecondFragment-onDestroy--------");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e("onDestroyView","--------SecondFragment-onDestroyView--------");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.e("onDetach","--------SecondFragment-onDetach--------");
    }
}
