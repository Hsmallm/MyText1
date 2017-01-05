/**********************************************************************
 * FILE                ：SlideLinearLayout.java
 * PACKAGE			   ：com.seaway.android.common.widget
 * AUTHOR              ：xubt
 * DATE				   ：2014-10-14 上午11:36:18
 * FUNCTION            ：
 *
 * 杭州思伟版权所有
 *======================================================================
 * CHANGE HISTORY LOG
 *----------------------------------------------------------------------
 * MOD. NO.|  DATE    | NAME           | REASON            | CHANGE REQ.
 *----------------------------------------------------------------------
 *         |          | xubt        | Created           |
 *
 * DESCRIPTION:
 *
 ***********************************************************************/
package com.seaway.android.common.widget;

import com.seaway.android.toolkit.base.SWAndroidUtil;
import com.seaway.android.toolkit.base.SWLog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * 项目名称:seaway-android-common 类名称:SlideLinearLayout 类描述： 创建人：xubt
 * 创建时间:2014-10-14 上午11:36:18
 * -------------------------------修订历史-------------------------- 修改人：xubt
 * 修改时间:2014-10-14 上午11:36:18 修改备注：
 * 
 * @version：
 */
@SuppressLint("NewApi")
public class SlideBackLinearLayout extends LinearLayout implements
		OnGestureListener {
	private GestureDetector mDetector;
	private Context context;
	private boolean isBack;
	private boolean isEnabled;

	/**
	 * @param context
	 */
	public SlideBackLinearLayout(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context = context;
		init();
	}

	/**
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public SlideBackLinearLayout(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		this.context = context;
		init();
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public SlideBackLinearLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.context = context;
		init();

	}

	private void init() {
		mDetector = new GestureDetector(context, this);
		this.setClickable(true);
		isBack = false;
		isEnabled = true;
	}

	@Override
	public void setEnabled(boolean enabled) {
		this.isEnabled = enabled;
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		mDetector.onTouchEvent(ev);
		return super.dispatchTouchEvent(ev);
	}

	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		float dx = e2.getX() - e1.getX();
		SWLog.LogD("SlideLinearLayout dx:" + dx);
		if (dx > SWAndroidUtil.dip2px(context, 80)) {
			if (!isBack && isEnabled) {
				if (context instanceof FragmentActivity) {
					FragmentManager fm = ((FragmentActivity) context)
							.getSupportFragmentManager();
					if (fm.getBackStackEntryCount() > 1) {
						fm.popBackStack();
					} else {
						((FragmentActivity) context).finish();
					}
				}
				isBack = true;
			}
		}
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		// TODO Auto-generated method stub
		return false;
	}

}
