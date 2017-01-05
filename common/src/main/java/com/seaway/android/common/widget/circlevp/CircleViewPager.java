/**********************************************************************
 * FILE                ：CircleViewPager.java
 * PACKAGE			   ：com.xbting.circle.vp.widget
 * AUTHOR              ：xubt
 * DATE				   ：2014-7-21 下午2:14:52
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



import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 项目名称:CircleViewPage
 * 类名称:CircleViewPager
 * 类描述：无限循环ViewPage
 * 创建人：xubt
 * 创建时间:2014-7-21 下午2:14:52
 * -------------------------------修订历史--------------------------
 * 修改人：xubt
 * 修改时间:2014-7-21 下午2:14:52
 * 修改备注：
 * @version：
 */
@SuppressLint("HandlerLeak")
public class CircleViewPager extends ViewPager{

	private Handler circleHandler;
	private  CircleViewPager mViewPager;
	private  boolean isAutoScroll;
	private  boolean isPause;
	private  long intervalTime = 5*1000;
	
	public CircleViewPager(Context context) {
		super(context);
		initView();
		
	}
	
	public CircleViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}


	private void initView(){
//		SWLog.LogD("CircleViewPager----initView");
		mViewPager = this;
	
	}
	
	 class ScrollHandler extends Handler{
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
//			SWLog.LogD("ScrollHandler----handleMessage");
			if(isAutoScroll && !isPause){
				mViewPager.setCurrentItem(mViewPager.getCurrentItem()+1);
				this.sendEmptyMessageDelayed(0, intervalTime);
			}
		}
	}

	/**
	 * 开始自动滚动，在setAdapter()之后调用
	 * 
	 * void
	 */
	public void startAutoScroll(){
		isAutoScroll = true;
		if(circleHandler==null){
			circleHandler = new ScrollHandler();
		}
		circleHandler.removeCallbacksAndMessages(null);
		circleHandler.sendEmptyMessageDelayed(mViewPager.getCurrentItem(),intervalTime);
	}
	
	/**
	 * 停止自动滚动
	 * 
	 * void
	 */
	public void stopAutoScroll(){
		isAutoScroll = false;
		circleHandler.removeCallbacksAndMessages(null);
	}
	
	@Override
	public int getCurrentItem() {
		// TODO Auto-generated method stub
		return super.getCurrentItem();
	}
	
	/**
	 * get real item
	 * @return
	 * int real item
	 */
	public int getRealCurrentItem(){
		return getCurrentItem()%getCirclePageAdapter().getRealCount();
	}
	
	/**
	 * set real item
	 * @param realItem 
	 * void real item
	 * 
	 */
	public void setRealCurrentItem(int realItem){
		setCurrentItem(realItem);
	}


	/**
	 * 
	 * @return the circlePageAdapter
	 */
	public CirclePagerAdapter getCirclePageAdapter() {
		return (CirclePagerAdapter) getAdapter();
	}


	/**
	 * @param circlePageAdapter the circlePageAdapter to set
	 */
	public void setCirclePageAdapter(CirclePagerAdapter circlePageAdapter) {
		this.setAdapter(circlePageAdapter);
	}
	
	
	
	
	/**
	 * 设置自动滚动的间隔时间，单位毫秒，默认3*1000毫秒
	 * @return the intervalTime
	 */
	public  long getIntervalTime() {
		return intervalTime;
	}

	/**
	 * 获取自动滚动的间隔时间，单位毫秒
	 * 
	 * @param intervalTime the intervalTime to set
	 */
	public  void setIntervalTime(long intervalTime) {
		this.intervalTime = intervalTime;
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		if(ev.getAction()==MotionEvent.ACTION_DOWN){//按下停止自动滚动
			isPause = true;
			if(circleHandler!=null){
				circleHandler.removeCallbacksAndMessages(null);
			}
		}else if(ev.getAction()==MotionEvent.ACTION_UP){//放开重新自动滚动
			isPause = false;
			if(circleHandler!=null){
				circleHandler.sendEmptyMessageDelayed(0, intervalTime);
			}
		}
		return super.dispatchTouchEvent(ev);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent arg0) {
		// TODO Auto-generated method stub
		return super.onTouchEvent(arg0);
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent arg0) {
		// TODO Auto-generated method stub
		return super.onInterceptTouchEvent(arg0);
		
	}
	
	
	

}
