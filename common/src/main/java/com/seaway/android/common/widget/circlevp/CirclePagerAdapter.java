/**********************************************************************
 * FILE                ：CirclePagerAdapter.java
 * PACKAGE			   ：com.seaway.android.common.widget.circlevp
 * AUTHOR              ：xubt
 * DATE				   ：2014-7-22 上午10:32:11
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

import android.support.v4.view.PagerAdapter;
import android.view.View;

/**
 * 项目名称:seaway-android-common
 * 类名称:CirclePagerAdapter
 * 类描述：
 * 创建人：xubt
 * 创建时间:2014-7-22 上午10:32:11
 * -------------------------------修订历史--------------------------
 * 修改人：xubt
 * 修改时间:2014-7-22 上午10:32:11
 * 修改备注：
 * @version：
 */
public class CirclePagerAdapter extends PagerAdapter{

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return Integer.MAX_VALUE;
	}
	
	
	/**
	 * 获取真正的数目，必须重写次方法
	 * @return
	 * int 
	 */
	public int getRealCount(){
		return 0;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return false;
	}

}
