package com.example.administrator.text1.utils.view;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.example.administrator.text1.utils.TextUIDefaultConfirtDialog;

/**
 * 功能描述：创建一个Dialog工具类，实现自定义样式的Dialog
 * Created by HM on 2016/5/11.
 */
public class TextUIDefaultDialogHelper {

    public static TextUIDefaultConfirtDialog dialog;

    /**
     * 自定义Dlalog，并实现对应样式的Dialog
     *
     * @param context
     * @param msg
     * @param leftBtnTxt
     * @param leftListener
     * @param rightBtnTxt
     * @param rightListener
     */
    public static void showDefaultAskAlert(Context context, String msg, String leftBtnTxt, View.OnClickListener leftListener,
                                           String rightBtnTxt, View.OnClickListener rightListener) {

        if(((Activity)context).isFinishing())return;
        dialog = new TextUIDefaultConfirtDialog(context);
        dialog.getTwoBtnLayout().setVisibility(View.VISIBLE);
        dialog.getMiddleButton().setVisibility(View.GONE);
        dialog.getPositiveButton().setVisibility(View.VISIBLE);
        dialog.getNegativeButton().setVisibility(View.VISIBLE);
        dialog.setUiDialogMessage(msg);

        dialog.getPositiveButton().setText(leftBtnTxt);
        dialog.getPositiveButton().setOnClickListener(leftListener);
        dialog.getNegativeButton().setText(rightBtnTxt);
        dialog.getNegativeButton().setOnClickListener(rightListener);

        dialog.show();
    }

}
