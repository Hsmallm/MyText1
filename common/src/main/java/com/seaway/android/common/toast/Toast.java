package com.seaway.android.common.toast;

import android.content.Context;

/**
 * 自定义Toast
 * 
 * 
 */
public class Toast {
	private static android.widget.Toast mToast;
	
	/**
	 * 显示Toast
	 */
	public static void showToast(Context context, CharSequence msg) {
		if (null == mToast) {
			mToast = android.widget.Toast.makeText(context, msg,
					android.widget.Toast.LENGTH_LONG);
		} else {
			mToast.setText(msg);
		}
		mToast.show();
//		android.widget.Toast.makeText(context, msg,
//				android.widget.Toast.LENGTH_LONG).show();
	}
}