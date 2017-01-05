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
 * ClassName：MobileBankBaseActivity.java
 * Description：
 * 	下载线程池
 * Reporting Bugs：
 * Modification history：
 * @version 1.0 2014-4-15
 * @since 2.3
 * @author changtao
 */
package com.seaway.android.common.download.task;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.seaway.android.common.download.SWDownloadTask;
import com.seaway.android.toolkit.base.SWLog;

/**
 * 下载线程池
 * 
 * @version 1.0 2014-4-17
 * @since 2.3
 * @author changtao
 */
public class SWThreadPoolExecutor extends ThreadPoolExecutor {

	public ISWThreadPoolExecutor ipe;

	/**
	 * 构造方法
	 * 
	 * @param corePoolSize
	 * @param maximumPoolSize
	 * @param keepAliveTime
	 * @param unit
	 * @param workQueue
	 * @param threadFactory
	 * @param handler
	 */
	public SWThreadPoolExecutor(int corePoolSize, int maximumPoolSize,
			long keepAliveTime, TimeUnit unit,
			BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory,
			RejectedExecutionHandler handler) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue,
				threadFactory, handler);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void beforeExecute(Thread t, Runnable r) {
		// TODO Auto-generated method stub
		super.beforeExecute(t, r);
		SWLog.LogD("thread " + t.getName() + " is executed");
	}

	@Override
	protected void afterExecute(Runnable r, Throwable t) {
		// TODO Auto-generated method stub
		super.afterExecute(r, t);
		SWLog.LogD("runnable r is executed");
		if (null != ipe) {
			if (r instanceof SWDownloadTask) {
				// 如果是下载任务，通知下载完成
				ipe.taskExecuted(((SWDownloadTask) r).taskID);
			}
		}
	}

	/**
	 * 线程监听
	 * 
	 * @version 1.0 2014-4-17
	 * @since 2.3
	 * @author changtao
	 */
	public interface ISWThreadPoolExecutor {
		/**
		 * 通知线程执行完成
		 * 
		 * @param taskID
		 *            线程编号
		 */
		public void taskExecuted(int taskID);
	}
}
