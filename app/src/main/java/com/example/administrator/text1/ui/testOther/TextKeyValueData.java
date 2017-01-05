package com.example.administrator.text1.ui.testOther;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.text1.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by HM on 2016/1/13.
 * 功能简介：
 * 自定义“业务规则”中的布局文件并生成动态数列且显示
 */
public class TextKeyValueData extends Activity {

    private LinearLayout viewRule;
    private List<Map<String, String>> products = new ArrayList<Map<String,String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text2);
        viewRule = (LinearLayout) this.findViewById(R.id.equ_detail_rule_child);
        viewRule.setVisibility(View.VISIBLE);


        Map<String, String> map = new HashMap<String, String>();
        map.put("1", "张三");
        map.put("2", "李四");
        map.put("3", "王五");

        products.add(map);
        System.out.println(products);

        viewRule.removeAllViews();
        //a.循环List数组里面的 (Map<String, String>
        for (Map<String, String> m : products) {

            //b.
            View view = LayoutInflater.from(this). inflate(R.layout.view_main_toinvest_equ_detail, null);
            TextView tvLeft = (TextView) view.findViewById(R.id.equ_detail_rule_title);
            TextView tvRight = (TextView) view.findViewById(R.id.equ_detail_rule_content);
            //c.循环出String1
            for (String k : m.keySet()) {
                tvLeft.setText(k);
                System.out.println("K:::"+k);
            }
            //d.循环出String2
            for (String k2 : m.values()) {
                tvRight.setText(k2);
                System.out.println("K2:::"+k2);
            }
            //e.再把视图加载到布局文件中
            viewRule.addView(view);
        }
    }
}
