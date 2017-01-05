package com.seaway.android.common.widget;

import com.seaway.android.toolkit.base.SWAndroidUtil;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.TextView;


/**
 * 加载更多条目View
 * 
 * @author changtao
 * 
 */
public class UIMoreItemsView extends TextView {
	/**
	 * 编号
	 */
	public static final int UI_MORE_ITEMS_VIEW_ID = 0x9513;
	
	/**
	 * 加载更多文字
	 */
	public static final String LOAD_MORE_TEXT = "加载更多";

	/**
	 * 加载中提示文字
	 */
	public static final String LOADING_MORE_TEXT = "加载中...请稍候";
	
	/**
	 * 没有更多内容
	 */
	public static final String NO_MORE_TEXT = "没有更多内容";

	public UIMoreItemsView(Context context) {
		super(context);
		initView();
	}

	/**
	 * 初始化界面
	 */
	private void initView() {
		this.setId(UI_MORE_ITEMS_VIEW_ID);
		this.setBackgroundColor(Color.parseColor("#f3f3f3"));
		this.setGravity(Gravity.CENTER);
		this.setText(UIMoreItemsView.LOAD_MORE_TEXT);
		this.setTextColor(Color.parseColor("#999999"));
		this.setTextSize(12.f);
		this.setFocusable(true);
		this.setClickable(true);
		
		AbsListView.LayoutParams params = new AbsListView.LayoutParams(
				LayoutParams.MATCH_PARENT, SWAndroidUtil.dip2px(
						getContext(), 34.f));
		this.setLayoutParams(params);
	}
}