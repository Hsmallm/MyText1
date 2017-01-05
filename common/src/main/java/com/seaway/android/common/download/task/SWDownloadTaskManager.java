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
 * 	文件下载任务管理器
 * Reporting Bugs：
 * Modification history：
 * @version 1.0 2014-4-15
 * @since 2.3
 * @author changtao
 */
package com.seaway.android.common.download.task;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import com.seaway.android.common.download.SWDownloadTask;
import com.seaway.android.common.download.task.SWThreadPoolExecutor.ISWThreadPoolExecutor;
import com.seaway.android.toolkit.base.SWLog;

/**
 * 文件下载任务管理器，单例
 * 
 * @version 1.0 2014-4-17
 * @since 2.3
 * @author changtao
 */
public class SWDownloadTaskManager implements ISWThreadPoolExecutor {
	/**
	 * 文件下载任务管理器
	 */
	private static SWDownloadTaskManager manager;

	private static final String TAG = SWDownloadTaskManager.class
			.getSimpleName();

	// -------------------线程池设置-----------------
	/**
	 * 核心线程数量
	 */
	private static final int CORE_SIZE = 3;
	/**
	 * 线程池活动线程最大数量
	 */
	private static final int POOL_SIZE = 10;
	/**
	 * 线程保持活动周期，单位：秒
	 */
	private static final int KEEP_ALIVE_TIME = 5;
	/**
	 * 线程池队列总数量
	 */
	private static final int QUEUE_SIZE = 128;

	/**
	 * 线程池
	 */
	private SWThreadPoolExecutor mExecutor;

	/**
	 * 活动任务管理器
	 */
	public List<SWDownloadTask> activeAndQueueTasks;

	/**
	 * 构造方法<br>
	 * 创建线程池
	 */
	private SWDownloadTaskManager() {
		// 初始化线程池
		mExecutor = new SWThreadPoolExecutor(CORE_SIZE, POOL_SIZE,
				KEEP_ALIVE_TIME, TimeUnit.SECONDS,
				new ArrayBlockingQueue<Runnable>(QUEUE_SIZE), THREADFACTORY,
				new ThreadPoolExecutor.CallerRunsPolicy());
		mExecutor.ipe = this;
		activeAndQueueTasks = new ArrayList<SWDownloadTask>();
	}

	/**
	 * 线程执行工厂
	 */
	private static final ThreadFactory THREADFACTORY = new ThreadFactory() {
		/**
		 * 自动计数器
		 */
		private final AtomicInteger mCount = new AtomicInteger(0);

		/**
		 * 创建新线程
		 */
		@Override
		public Thread newThread(Runnable r) {
			return new Thread(r, TAG + " #" + mCount.incrementAndGet());
		}
	};

	/**
	 * 获取下载任务管理器实例
	 * 
	 * @return 下载任务管理器实例
	 */
	public static synchronized SWDownloadTaskManager getInstance() {
		if (null == manager) {
			SWLog.LogD("实例化下载线程池");
			manager = new SWDownloadTaskManager();
		}
		return manager;
	}

	/**
	 * 执行任务，并记录活动任务
	 * 
	 * @param command
	 *            下载任务
	 * 
	 */
	public void parallelExecute(Runnable command) {
		if (hasActivted((SWDownloadTask) command)) {
			SWLog.LogD("线程已在执行");
			return;
		}

		mExecutor.execute(command);
		if (command instanceof SWDownloadTask) {
			activeAndQueueTasks.add((SWDownloadTask) command);
		}
	}

	/**
	 * 判断线程是否已经在执行
	 * 
	 * @param task
	 *            待执行的线程
	 * @return true：已在执；false：未执行
	 */
	private boolean hasActivted(SWDownloadTask task) {
		boolean flag = false;
		if (null != activeAndQueueTasks && activeAndQueueTasks.size() > 0) {
			for (int i = 0; i < activeAndQueueTasks.size(); i++) {
				if (activeAndQueueTasks.get(i).taskID == task.taskID) {
					flag = true;
					break;
				}
			}
		}
		return flag;
	}

	/**
	 * 取消任务执行
	 * 
	 * @param taskID
	 *            任务编号
	 */
	public void cancelExecute(int taskID) {
		SWLog.LogD("cancel task id is : " + taskID);
		for (int i = 0; i < activeAndQueueTasks.size(); i++) {
			if (taskID == activeAndQueueTasks.get(i).taskID) {
				SWLog.LogD("取消下载");
				activeAndQueueTasks.get(i).cancel();
				break;
			}
		}
	}

	/**
	 * 任务执行完成回调
	 * 
	 * @param 任务编号
	 */
	@Override
	public void taskExecuted(int taskID) {
		// TODO Auto-generated method stub
		if (null == activeAndQueueTasks || 0 == activeAndQueueTasks.size()) {
			return;
		}
		for (int i = 0; i < activeAndQueueTasks.size(); i++) {
			if (taskID == activeAndQueueTasks.get(i).taskID) {
				activeAndQueueTasks.remove(i);
				break;
			}
		}
	}
}
