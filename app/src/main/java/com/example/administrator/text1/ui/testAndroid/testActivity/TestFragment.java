package com.example.administrator.text1.ui.testAndroid.testActivity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.text1.R;
import com.example.administrator.text1.ui.testOther.ChooseDateDialog;

/**
 * 功能描述：测试Fragment的生命周期
 * 场景描述：1、第一次运行程序： Log打印：onAttach---onCreate----onCreateView---onActivityCreated----onStart----onResume
 *           2、再按一下Back键，Log打印：onPause---onStop----onDestroyView---onDestroy----onDetach
 *           3、再一次运行程序： Log打印：onAttach---onCreate----onCreateView---onActivityCreated----onStart----onResume
 *           4、再按一下home键，Log打印：onPause---onStop
 *           5、再打开程序： Log打印：onStart----onResume
 *
 * 总结：
 * 1. 当一个fragment被创建的时候，它会经历以下状态.
        onAttach()
        onCreate()
        onCreateView()
        onActivityCreated()
  2. 当这个fragment对用户可见的时候，它会经历以下状态。
        onStart()
        onResume()
  3. 当这个fragment进入“后台模式”的时候，它会经历以下状态。
        onPause()
        onStop()
  4. 当这个fragment被销毁了（或者持有它的activity被销毁了），它会经历以下状态。
        onPause()
        onStop()
        onDestroyView()
        onDestroy() // 本来漏掉类这个回调，感谢xiangxue336提出。
        onDetach()
  5. 就像activitie一样，在以下的状态中，可以使用Bundle对象保存一个fragment的对象。
        onCreate()
        onCreateView()
        onActivityCreated()
  6. fragments的大部分状态都和activitie很相似，但fragment有一些新的状态。
         onAttached() —— 当fragment被加入到activity时调用（在这个方法中可以获得所在的activity）。
        onCreateView() —— 当activity要得到fragment的layout时，调用此方法，fragment在其中创建自己的layout(界面)。
        onActivityCreated() —— 当activity的onCreated()方法返回后调用此方法
        onDestroyView() —— 当fragment中的视图被移除的时候，调用这个方法。
        onDetach() —— 当fragment和activity分离的时候，调用这个方法。
        一旦activity进入resumed状态（也就是running状态），你就可以自由地添加和删除fragment了。因此，只有当activity在resumed状态时
        fragment的生命周期才能独立的运转，其它时候是依赖于activity的生命周期变化的。
 * Created by hzhm on 2016/6/20.
 */
public class TestFragment extends Fragment{

    private TextView txt;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.e("onAttach","--------onAttach--------");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("onCreate","--------onCreate--------");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e("onCreateView","--------onCreateView--------");
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.footer_view,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.e("onActivityCreated","--------onActivityCreated--------");
        super.onActivityCreated(savedInstanceState);

        txt = (TextView) getActivity().findViewById(R.id.footer_button);
        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                FragmentManager fm = getFragmentManager();
//                FragmentTransaction ft = fm.beginTransaction();
//                ft.add(R.id.content,new SecondFragment());
//                ft.commitAllowingStateLoss();
                ChooseDateDialog dialog = ChooseDateDialog.newDialogFragment();
                dialog.show(getFragmentManager(),"");
            }
        });
    }

    @Override
    public void onStart() {
        Log.e("onStart","--------onStart--------");
        super.onStart();
    }

    @Override
    public void onResume() {
        Log.e("onResume","--------onResume--------");
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.e("onPause","--------onPause--------");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.e("onStop","--------onStop--------");
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        Log.e("onDestroyView","--------onDestroyView--------");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Log.e("onDestroy","--------onDestroy--------");
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        Log.e("onDetach","--------onDetach--------");
        super.onDetach();
    }
}
