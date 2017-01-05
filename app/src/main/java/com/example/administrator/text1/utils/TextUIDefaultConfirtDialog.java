package com.example.administrator.text1.utils;

import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.text1.R;

/**
 * 功能描述:自定义一个Dialog对象
 * Created by HM on 2016/5/11.
 */
public class TextUIDefaultConfirtDialog extends Dialog {

    /**
     * 确定
     */
    public static final int CONFIRM_DIALOG_RESULT_OK = 0;

    /**
     * 取消
     */
    public static final int CONFIRM_DIALOG_RESULT_CANCEL = 1;

    /**
     * 提示内容
     */
    private TextView uiDialogMessage;

    /**
     * 中间按钮
     */
    private Button middleButton;

    /**
     * 左边确定按钮
     */
    private Button positiveButton;

    /**
     * 右边取消按钮
     */
    private Button negativeButton;

    private LinearLayout contentLayout;
    private LinearLayout twoBtnLayout;



    public TextUIDefaultConfirtDialog(Context context) {
        //设置Dialog对话框的整体样式
        super(context,R.style.UIDefaultConfirmDialogTheme);
        //设置Dialog对话框为不可取消
        this.setCancelable(false);
        //设置对话框窗体头部样式
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        initDialog();
    }

    private void initDialog() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.ui_default_confirm_dialog,null);
        uiDialogMessage = (TextView) view.findViewById(R.id.ui_dialog_message);
        middleButton = (Button) view.findViewById(R.id.ui_default_confirm_dialog_middle_button);
        positiveButton = (Button) view.findViewById(R.id.ui_default_confirm_dialog_positive_button);
        negativeButton = (Button) view.findViewById(R.id.ui_default_confirm_dialog_negative_button);

        contentLayout = (LinearLayout) view.findViewById(R.id.dialog_content_main);
        twoBtnLayout = (LinearLayout) view.findViewById(R.id.ui_default_confirm_dialog_two_btn_layout);
        //DisplayMetircs类可以很方便的获取分辨率
        DisplayMetrics metrics = getContext().getResources().getDisplayMetrics();
        //实例化LayoutParams对象，即是给该视图设置对象的宽高（metrics.widthPixels*7/8表示获取屏幕宽度7/8的分辨率，高是继承父类）
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(metrics.widthPixels*7/8, ViewGroup.LayoutParams.WRAP_CONTENT);

        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        middleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        setContentView(view,params);
    }

    public Button getPositiveButton(){
        return positiveButton;
    }

    public Button getNegativeButton(){
        return negativeButton;
    }

    public void setUiDialogMessage(String txt){
        uiDialogMessage.setText(txt);
    }

    public TextView getUiDialogMessage(){
        return uiDialogMessage;
    }


    /**
     *
     * @param middleButton
     */
    public void setMiddleButton(Button middleButton){
        this.middleButton = middleButton;
    }

    /**
     *
     * @return middleButton
     */
    public Button getMiddleButton(){
        return middleButton;
    }

    /**
     *
     * @param twoBtnLayout
     */
    public void setTwoBtnLayout(LinearLayout twoBtnLayout){
        this.twoBtnLayout = twoBtnLayout;
    }

    /**
     *
     * @return middleButton
     */
    public LinearLayout getTwoBtnLayout(){
        return twoBtnLayout;
    }
}
