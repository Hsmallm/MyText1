package com.example.administrator.text1.ui.testSlipButton;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.example.administrator.text1.R;
import com.example.administrator.text1.utils.THSharePreferencesHelperUtil;
import com.example.administrator.text1.utils.ToastUtil;
import com.seaway.android.common.widget.SlipButton;
import com.seaway.android.common.widget.dialog.UIDefaultDialogHelper;

/**
 * Created by hzhm on 2016/12/1.
 * 功能描述: 侧滑按钮控件SlipButton相关使用
 * 坑点描述：1、当（注：除第一次以外）每次setHistoryChosen()这个方法以后，都会重新调用这个监听SetOnChangedListener...
 *           2、(防止非手动操作：如切入后台、上一页返回...，而引起的SetOnChangedListener监听)
 * 解决方案：添加相应的boolean变量控制器First、isHistoryChosen
 */

public class TestSlipButton extends Activity {

    private SlipButton slipButton;

    private boolean isFirst = false;//用于判断是否为第一次进来
    private boolean isHistoryChosen = false;//用于判断是否为历史选择


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_slipbutton);

        slipButton = (SlipButton) findViewById(R.id.test_slipbtn);
        isFirst = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(THSharePreferencesHelperUtil.getIsSelected()){
            if(isFirst){//如果是第一次进入该页面，则说明没有进行历史选择
                isHistoryChosen = false;
            }else {//如果不是第一次进入该页面，则说明有进行历史选择
                isHistoryChosen = true;
            }
            isFirst = false;//无论是不是第一次进来，进来以后就都不是第一次
            slipButton.setHistoryChosen(true);
        }else {
            if(isFirst){
                isHistoryChosen = false;
            }else {
                isHistoryChosen = true;
            }
            isFirst = false;
            slipButton.setHistoryChosen(false);
        }
        slipButton.SetOnChangedListener(new SlipButton.SlipButtonChangeListener() {
            @Override
            public void OnChanged(boolean CheckState) {
                if(isHistoryChosen){//如果是非手动启用的监听，则结束不做处理，并把值改为false
                    isHistoryChosen = false;
                    return;
                }
                if(CheckState){
                    ToastUtil.showNormalToast("开启");
                    THSharePreferencesHelperUtil.setIsSelected(true);
                }else {
                    UIDefaultDialogHelper.showDefaultAskAlert(TestSlipButton.this, "你确定要取消吗", "取消", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            UIDefaultDialogHelper.dialog.dismiss();
                            UIDefaultDialogHelper.dialog = null;
                            ToastUtil.showNormalToast("取消关闭");
                            if(isFirst){
                                isHistoryChosen = false;
                            }else {
                                isHistoryChosen = true;
                            }
                            isFirst = false;
                            slipButton.setHistoryChosen(true);
                        }
                    }, "确定", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            UIDefaultDialogHelper.dialog.dismiss();
                            UIDefaultDialogHelper.dialog = null;
                            ToastUtil.showNormalToast("关闭");
                            THSharePreferencesHelperUtil.setIsSelected(false);
                        }
                    });

                }
            }
        });
    }
}
