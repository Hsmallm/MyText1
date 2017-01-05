/**********************************************************************
 * FILE                ：CirclePageAdapter.java
 * PACKAGE			   ：com.xbting.circle.vp.widget
 * AUTHOR              ：xubt
 * DATE				   ：2014-7-21 下午2:15:27
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
package com.seaway.android.common.widget.circlevp;

import java.util.List;

import android.view.View;
import android.view.ViewGroup;

/**
 * 项目名称:SimpleCirclePageAdapter
 * 类名称:SimpleCirclePageAdapter
 * 类描述：
 * 创建人：xubt
 * 创建时间:2014-7-21 下午2:15:27
 * -------------------------------修订历史--------------------------
 * 修改人：xubt
 * 修改时间:2014-7-21 下午2:15:27
 * 修改备注：
 * @version：
 */
public class SimpleCirclePageAdapter extends CirclePagerAdapter{
	/**真正的子项数目*/
	private int realCount;
	/**子项list*/
	private List<View> views;

	/**
	 * @param views
	 */
	public SimpleCirclePageAdapter(List<View> views) {
		super();
		this.views = views;
		this.realCount = views.size();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return super.getCount();
	}
	
	@Override
	public int getRealCount() {
		// TODO Auto-generated method stub
		return realCount;
	}
	

	@Override
	public boolean isViewFromObject(View view, Object object) {
		// TODO Auto-generated method stub
		return view==object;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// TODO Auto-generated method stub
		container.removeView((View) object);
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		// TODO Auto-generated method stub
		View view = views.get(position%getRealCount());
		container.addView(view);
		return view;
	}

}
