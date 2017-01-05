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
 * ClassName：CheckVersionHelper.java
 * Description：
 * 	默认弹出框帮助类，实现各种通用弹出框的实现
 * Reporting Bugs：
 * Modification history：
 * @version 1.0 2014-9-22
 * @since 2.3
 */
package com.seaway.android.common.widget.dialog;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * 
 * 
 * @version 1.0 2014-9-22
 * @since 2.3
 * 
 */
public class UIDefaultDialogHelper {
	public static UIDefaultConfirmDialog dialog;
	

	/**
	 * 默认提示弹出框<br>
	 * 只包含提示内容及确定按钮<br>
	 * 确定按钮仅隐藏弹出框
	 * 
	 * @param context
	 *            上下文
	 * @param resourceId
	 *            图片资源编号
	 * @param msg
	 *            提示内容
	 */
	public static void showDefaultAlert(Context context,String msg) {
		if(((Activity)context).isFinishing())return;
		final UIDefaultConfirmDialog dialog = new UIDefaultConfirmDialog(context);
		dialog.getTwoBtnLayout().setVisibility(View.GONE);
		dialog.getMiddleButton().setVisibility(View.VISIBLE);
		dialog.getUiDialogMessage().setText(msg);
		dialog.getMiddleButton().setText("确定");
		dialog.getMiddleButton().setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				});

		dialog.show();
	}

	/**
	 * 默认提示弹出框<br>
	 * 只包含提示内容及确定按钮<br>
	 * 确定按钮默认仅隐藏弹出框
	 * 
	 * @param context
	 *            上下文
	 * @param resourceId
	 *            图片资源编号
	 * @param msg
	 *            提示内容
	 * @param listener
	 *            确定按钮点击事件
	 * 
	 */
	public static void showDefaultAlert(Context context,String msg, OnClickListener listener) {
		if(((Activity)context).isFinishing())return;
		dialog = new UIDefaultConfirmDialog(context);
		dialog.getTwoBtnLayout().setVisibility(View.GONE);
		dialog.getMiddleButton().setVisibility(View.VISIBLE);
		
		dialog.getUiDialogMessage().setText(msg);
		dialog.getMiddleButton().setText("确定");
		if (null == listener) {
			dialog.getMiddleButton().setOnClickListener(
					new OnClickListener() {
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							dialog.dismiss();
							dialog = null;
						}
					});
		} else {
			dialog.getMiddleButton().setOnClickListener(listener);
		}

		dialog.show();
	}
	
	
	/**
	 * 
	 * 警告弹出框<br>
	 * 顶部图标为感叹号，包含左右两个按钮<br>
	 * 左边取消按钮仅隐藏弹出框
	 * 
	 * @param context
	 *            上下文
	 * @param msg
	 *            提示内容
	 * @param rightButtonText
	 *            右按钮文字
	 * @param listener
	 *            右边按钮点击监听
	 */
	public static void showDefaultWarningAlert(Context context,String msg, OnClickListener listener) {
		if(((Activity)context).isFinishing())return;
		dialog = new UIDefaultConfirmDialog(context);
		dialog.getTwoBtnLayout().setVisibility(View.VISIBLE);
		dialog.getMiddleButton().setVisibility(View.GONE);
		dialog.getPositiveButton().setVisibility(View.VISIBLE);
		dialog.getNegativeButton().setVisibility(View.VISIBLE);
		dialog.getUiDialogMessage().setText(msg);
		dialog.getPositiveButton().setText("确定");
		
		if (null == listener) {
			dialog.getPositiveButton().setOnClickListener(
					new OnClickListener() {
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							dialog.dismiss();
							dialog = null;
						}
					});
		} else {
			dialog.getPositiveButton().setOnClickListener(listener);
		}

		dialog.show();
	}

	/**
	 * 询问弹出框<br>
	 * 顶部图标为问号，包含左右两个按钮<br>
	 * 左边取消按钮仅隐藏弹出框
	 * 
	 * @param context
	 *            上下文
	 * @param msg
	 *            提示内容
	 * @param rightButtonText
	 *            右按钮文字
	 * @param listener
	 *            右边按钮点击监听
	 * 
	 */
	public static void showDefaultAskAlert(Context context, String msg,
			String rightButtonText, OnClickListener listener) {
		if(((Activity)context).isFinishing())return;
		dialog = new UIDefaultConfirmDialog(context);
		dialog.getTwoBtnLayout().setVisibility(View.VISIBLE);
		dialog.getMiddleButton().setVisibility(View.GONE);
		dialog.getPositiveButton().setVisibility(View.VISIBLE);
		dialog.getNegativeButton().setVisibility(View.VISIBLE);
		dialog.getUiDialogMessage().setText(msg);
		
		dialog.getPositiveButton().setText("取消");
		dialog.getPositiveButton().setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						dialog.dismiss();
						dialog = null;
					}
				});
		
		dialog.getNegativeButton().setVisibility(View.VISIBLE);
		dialog.getNegativeButton().setText(rightButtonText);
		dialog.getNegativeButton().setOnClickListener(listener);

		dialog.show();
	}
    /**
     * 询问弹出框<br>
	 * 顶部图标为问号，包含左右两个按钮<br>
	 * 左右两边自定义事件
     * @param context
     * @param msg
     * @param leftButtonText
     * @param leftListener
     * @param rightButtonText
     * @param rightListener
     */
	public static void showDefaultAskAlert(Context context, String msg,
			String leftButtonText,OnClickListener leftListener,String rightButtonText, OnClickListener rightListener) {
		if(((Activity)context).isFinishing())return;
		dialog = new UIDefaultConfirmDialog(context);
		dialog.getTwoBtnLayout().setVisibility(View.VISIBLE);
		dialog.getMiddleButton().setVisibility(View.GONE);
		dialog.getPositiveButton().setVisibility(View.VISIBLE);
		dialog.getNegativeButton().setVisibility(View.VISIBLE);
		dialog.getUiDialogMessage().setText(msg);
		
		dialog.getPositiveButton().setText(leftButtonText);
		dialog.getPositiveButton().setOnClickListener(leftListener);
		dialog.getNegativeButton().setVisibility(View.VISIBLE);
		dialog.getNegativeButton().setText(rightButtonText);
		dialog.getNegativeButton().setOnClickListener(rightListener);

		dialog.show();
	}

	
	/**
	 * 操作成功提示框<br>
	 * 顶部图标为对钩，包含一个确定按钮<br>
	 * 确定按钮默认隐藏提示框
	 * 
	 * @param conext
	 *            上下文
	 * @param msg
	 *            提示内容
	 * @param listener
	 *            确定按钮点击监听
	 */
	public static void showDefaultSuccessAlert(Context context, String msg,
			OnClickListener listener) {
		if(((Activity)context).isFinishing())return;
		dialog = new UIDefaultConfirmDialog(context);
		dialog.getTwoBtnLayout().setVisibility(View.VISIBLE);
		dialog.getMiddleButton().setVisibility(View.GONE);
		dialog.getPositiveButton().setVisibility(View.VISIBLE);
		dialog.getNegativeButton().setVisibility(View.VISIBLE); 
		dialog.getUiDialogMessage().setText(msg);
		dialog.getMiddleButton().setText("确定");
		if (null != listener) {
			// 如果前端设置了监听
			dialog.getPositiveButton().setOnClickListener(listener);
		} else {
			// 如果前端未设置监听，则默认隐藏提示框
			dialog.getMiddleButton().setOnClickListener(
					new OnClickListener() {
						@Override
						public void onClick(View v) {
							dialog.dismiss();
							dialog = null;
						}
					});
		}

		dialog.show();
	}
}
