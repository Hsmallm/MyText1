package com.seaway.android.common.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * 公共输入框
 * 
 * @author Leo.Chang
 * 
 */
public abstract class UICommonEditText extends EditText {

	public UICommonEditText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		initEditText();
	}

	public UICommonEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		initEditText();
	}

	public UICommonEditText(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		initEditText();
	}

	/**
	 * 初始化输入框相关信息
	 */
	protected abstract void initEditText();

	/**
	 * 检查输入的值是否正确
	 * 
	 * @param emptyMessage
	 *            值为空时的提示语 输入框编号
	 * @return true：正确；false：不正确
	 */
	public abstract boolean checkValue(String emptyMessage);
}