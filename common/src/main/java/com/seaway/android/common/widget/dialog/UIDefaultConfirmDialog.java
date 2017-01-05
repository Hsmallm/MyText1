/**	
 * Copyright (c) 2014-2020  Seaway I.T. Co, Ltd, All Rights Reserved.
 *		This source code contains CONFIDENTIAL INFORMATION and 
 *		TRADE SECRETS of Seaway I.T, Co, Ltd. ANY REPRODUCTION,
 * 		DISCLOSURE OR USE IN WHOLE OR IN PART is EXPRESSLY PROHIBITED, 
 * 		except as may be specifically authorized by prior written 
 *		agreement or permission by Seaway I.T, Co, Ltd.
 *
 *		THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE, The copyright 
 *		notice above does not evidence any actual or intended publication
 *		of such source code. 	
 * ClassName：DefaultConfirmDialog.java
 * Description：
 * 	默认的确认弹出框
 * Reporting Bugs：
 * Modification history：
 * @version 1.0 2014-9-22
 * @since 2.3
 */
package com.seaway.android.common.widget.dialog;

import com.seaway.android.common.R;

import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

/**
 * 
 * 默认的确认弹出框<br>
 * 可包含确定和取消按钮
 * 
 * @version 1.0 2014-9-22
 * @since 2.3
 * 
 */
public class UIDefaultConfirmDialog extends Dialog {
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
	
	/**中间按钮（只有一个按钮）*/
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
	

	public UIDefaultConfirmDialog(Context context) {
		super(context, R.style.UIDefaultConfirmDialogTheme);
		// TODO Auto-generated constructor stub
		this.setCancelable(false);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		initDialog();
	}

	/**
	 * 初始化弹出框
	 */
	private void initDialog() {
		View view = LayoutInflater.from(getContext()).inflate(
				R.layout.ui_default_confirm_dialog, null);

		uiDialogMessage = (TextView) view.findViewById(R.id.ui_dialog_message);
		positiveButton = (Button) view
				.findViewById(R.id.ui_default_confirm_dialog_positive_button);
		contentLayout = (LinearLayout) view
				.findViewById(R.id.dialog_content_main);
		twoBtnLayout = (LinearLayout) view.findViewById(R.id.ui_default_confirm_dialog_two_btn_layout);
		positiveButton.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dismiss();
			}
			
		});
		
		negativeButton = (Button) view
				.findViewById(R.id.ui_default_confirm_dialog_negative_button);

		DisplayMetrics metric = getContext().getResources().getDisplayMetrics();

		LayoutParams params = new LayoutParams(
				metric.widthPixels * 7 / 8,
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
		middleButton = (Button) view
				.findViewById(R.id.ui_default_confirm_dialog_middle_button);
		middleButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dismiss();
			}
		});

		setContentView(view, params);
	}

	public void setView(View view) {
		contentLayout.removeAllViews();
		contentLayout.addView(view);
	}
	
	public void setView(View view, LayoutParams param) {
		contentLayout.removeAllViews();
		contentLayout.addView(view, param);
	}
	
	

	public TextView getUiDialogMessage() {
		return uiDialogMessage;
	}


	public Button getPositiveButton() {
		return positiveButton;
	}

	public Button getNegativeButton() {
		return negativeButton;
	}
	
	
	
	/**
	 * @return the twoBtnLayout
	 */
	public LinearLayout getTwoBtnLayout() {
		return twoBtnLayout;
	}

	/**
	 * @param twoBtnLayout the twoBtnLayout to set
	 */
	public void setTwoBtnLayout(LinearLayout twoBtnLayout) {
		this.twoBtnLayout = twoBtnLayout;
	}

	/**
	 * @return the middleButton
	 */
	public Button getMiddleButton() {
		return middleButton;
	}
	

	/**
	 * @param middleButton the middleButton to set
	 */
	public void setMiddleButton(Button middleButton) {
		this.middleButton = middleButton;
	}

	public void setMessage(String msg){
		uiDialogMessage.setText(msg);
	}
}