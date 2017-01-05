package com.example.administrator.text1.ui.testOther;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.text1.R;
import com.example.administrator.text1.utils.view.GestureLinearLayout;
import com.example.administrator.text1.utils.view.HideOrShowFirstViewGroup;

/**
 * 功能描述：自定义ViewGroup(即自定义视图控件)，实现抽屉的展开和收起效果
 * Created by HM on 2016/4/12.
 */
public class TextDrawerActivity extends Activity implements View.OnClickListener {

    private HideOrShowFirstViewGroup hideOrShowFirstViewGroup;
    private TextView showText;
    private TextView hideText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = LayoutInflater.from(this).inflate(R.layout.activity_text_drawer, null);
        GestureLinearLayout gView = (GestureLinearLayout) view;
        gView.setSlideListener(new GestureLinearLayout.SlideListener() {
            @Override
            public void onSlideUp() {
                if (!hideOrShowFirstViewGroup.isHideFirst()) {
                    collapse();
                }
            }

            @Override
            public void onSlideDown() {
                if (hideOrShowFirstViewGroup.isHideFirst()) {
                    expand();
                }
            }
        });
        //注：必须将View强制转换为GestureLinearLayout后再setContentView(gView)
        setContentView(gView);

        hideOrShowFirstViewGroup = (HideOrShowFirstViewGroup) findViewById(R.id.hideShowControllView);
        showText = (TextView) findViewById(R.id.account_detail_withdraw);
        hideText = (TextView) findViewById(R.id.account_detail_recharge);

        showText.setOnClickListener(this);
        hideText.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //提现(即为展开)
            case R.id.account_detail_withdraw:
                expand();
                break;
            //充值（即为收起）
            case R.id.account_detail_recharge:
                collapse();
                break;
        }
    }

    /**
     * 收起抽屉
     */
    private void collapse() {
        hideOrShowFirstViewGroup.hideFirst(300);
    }

    /**
     * 展开抽屉
     */
    private void expand() {
        hideOrShowFirstViewGroup.showFirst(300);
        //重新布局了一下view
        hideOrShowFirstViewGroup.requestLayout();
    }
}
