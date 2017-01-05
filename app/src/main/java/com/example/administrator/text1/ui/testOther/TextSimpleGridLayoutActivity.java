package com.example.administrator.text1.ui.testOther;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.text1.R;
import com.example.administrator.text1.utils.view.TextSimpleGridLayout;
import com.seaway.android.common.toast.Toast;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能描述：调用自定义控件，并在XML里面完成自定义属性的设置，从而是实现自定义动态九风格布局
 * Created by HM on 2016/5/13.
 */
public class TextSimpleGridLayoutActivity extends Activity implements View.OnClickListener {

    private TextSimpleGridLayout textSimpleGridLayout;
    private HashMap<String, String> map;
    private List<String> mapKey;
    private List<String> mapValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_gridlayout);

        initAndSetListener();
    }

    private void initAndSetListener() {
        textSimpleGridLayout = (TextSimpleGridLayout) this.findViewById(R.id.gridolayout);
        map = new HashMap<>(12);
        map.put("投资记录", "http://7xjivo.com2.z0.glb.qiniucdn.com/o_1ahspcts51cpv221nsb1nm31436k.png?t=1463814252000");
        map.put("T币", "http://7xjivo.com2.z0.glb.qiniucdn.com/o_1ahspdarsigncrr10d91ncr15q1k.png?t=1467685583000");
        map.put("资金流水", "http://7xjivo.com2.z0.glb.qiniucdn.com/o_1ahspdk1514ca1o93l9g1j5t1gvlk.png?t=1467685593000");
        map.put("我的收益", "http://7xjivo.com2.z0.glb.qiniucdn.com/o_1ahspdtddbiqp3q13c817u1jh2k.png?t=1467685602000");
        map.put("投算器", "http://7xjivo.com2.z0.glb.qiniucdn.com/o_1aifa4kvd7ufslg1ri41me251uf.png?t=1463116199000");
        map.put("邀请好友", "http://7xjivo.com2.z0.glb.qiniucdn.com/o_1ahspe6ig19is8h8jakfg4ivk.png?t=1467685612000");
        map.put("消息中心", "http://7xjivo.com2.z0.glb.qiniucdn.com/o_1ahspepufep6be3g0spt7nrbk.png?t=1467685632000");
        map.put("任务中心", "http://7xjivo.com2.z0.glb.qiniucdn.com/o_1ahspf45b17vv8q21bmi4p6miak.png?t=1467685650000");
        map.put("e取", "http://7xjivo.com2.z0.glb.qiniucdn.com/o_1ahspegoebng8hv150p1b6si59k.png?t=1467685623000");
        map.put("11", "http://7xjivo.com2.z0.glb.qiniucdn.com/o_1ahspf45b17vv8q21bmi4p6miak.png?t=1467685650000");
        map.put("22", "http://7xjivo.com2.z0.glb.qiniucdn.com/o_1ahspf45b17vv8q21bmi4p6miak.png?t=1467685650000");
        map.put("33", "http://7xjivo.com2.z0.glb.qiniucdn.com/o_1ahspf45b17vv8q21bmi4p6miak.png?t=1467685650000");

        //如果子视图数目大于map总数，那么就移除多余的视图
        if (textSimpleGridLayout.getChildCount() > map.size()) {
            textSimpleGridLayout.removeViews(0, textSimpleGridLayout.getChildCount() - map.size());
        } else {
            //如果子map总数目小于视图数，则实例化添加相应数目的视图，并添加到textSimpleGridLayout
            int childAccount = textSimpleGridLayout.getChildCount();
            for (int i = 0; i < map.size() - childAccount; i++) {
                View view = LayoutInflater.from(this).inflate(R.layout.adapter_grid_item, null);
                view.setOnClickListener(this);
                textSimpleGridLayout.addView(view);
            }
        }

        //循环遍历出HashMap里面的Key、Value，并将其加载到对象的List数组里面
        mapKey = new ArrayList<>();
        mapValue = new ArrayList<>();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            mapKey.add(entry.getKey());
            mapValue.add(entry.getValue());
        }
        //根据map.size()数目，实例化对象的视图，并绑定相应的对象
        for (int i = 0; i < map.size(); i++) {
            View gridItemView = textSimpleGridLayout.getChildAt(i);
            ImageView ivIcon = (ImageView) gridItemView.findViewById(R.id.grid_icon);
            TextView tvTitle = (TextView) gridItemView.findViewById(R.id.grid_title);

            Map.Entry<String, String> entry = map.entrySet().iterator().next();
            tvTitle.setText(mapKey.get(i));
            Picasso.with(this).load(mapValue.get(i)).into(ivIcon);

            if (tvTitle == null) {
                ivIcon.setPadding(0, 0, 0, 0);
            }

            gridItemView.setTag(map);
            gridItemView.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        switch (mapKey.size()) {
            case 0:
                Toast.showToast(this, mapKey.get(0));
                break;
        }
    }
}
