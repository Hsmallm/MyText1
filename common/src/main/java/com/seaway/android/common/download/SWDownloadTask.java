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
 * 	文件下载任务
 * Reporting Bugs：
 * Modification history：
 * @version 1.0 2014-4-15
 * @since 2.3
 * @author changtao
 */
package com.seaway.android.common.download;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.seaway.android.common.download.broadcast.SWDownloadBroadcast;
import com.seaway.android.common.download.data.SWDownloadFileInfo;
import com.seaway.android.toolkit.base.SWLog;

/**
 * 文件下载任务
 *
 * @version 1.0 2014-4-17
 * @since 2.3
 * @author changtao
 */
public class SWDownloadTask implements Runnable {
	/**
	 * 下载状态标识：开始下载
	 */
	public static final int DOWNLOAD_START = 0x978909;
	/**
	 * 下载状态标识：下载完成
	 */
	public static final int DOWNLOAD_FINISH = 0x978910;
	/**
	 * 下载状态标识：取消下载
	 */
	public static final int DOWNLOAD_CANCEL = 0x978911;
	/**
	 * 下载状态标识：下载失败
	 */
	public static final int DOWNLOAD_FAIL = 0x978912;
	/**
	 * 下载状态标识：下载中
	 */
	public static final int DOWNLOAD_LOADING = 0x978913;

	/**
	 * 线程编号
	 */
	public int taskID;

	/**
	 * 是否取消
	 */
	private boolean isCancel = false;

	/**
	 * 是否需要通知
	 */
	private boolean hasNotification = false;

	/**
	 * 本地下载文件
	 */
	private File localfile;

	/**
	 * Android本地通知管理器
	 */
	private NotificationManager mNotifyManager;

	/**
	 * Android本地通知创建器
	 */
	private NotificationCompat.Builder mBuilder;

	/**
	 * 下载管理回调句柄
	 */
	private ISWDownloadManagerInterface itf;

	/**
	 * 下载文件信息
	 */
	private SWDownloadFileInfo info;

	/**
	 * 上下文
	 */
	private Context context;

	/**
	 * 构造方法，创建下载任务
	 *
	 * @param hasNotification
	 *            是否需要通知<br>
	 *            true：需要通知，下载时会创建Android本地通知;<br>
	 *            false：不需要通知
	 * @param info
	 *            下载文件信息
	 * @param context
	 *            上下文
	 */
	public SWDownloadTask(boolean hasNotification, SWDownloadFileInfo info,
			Context context) {
		this.hasNotification = hasNotification;
		this.info = info;
		this.context = context;
		this.taskID = info.id;
	}

	/**
	 * 设置下载管理回调句柄
	 *
	 * @param itf
	 *            下载管理回调句柄
	 */
	public void setItf(ISWDownloadManagerInterface itf) {
		this.itf = itf;
	}

	/**
	 * 取消下载任务
	 */
	public void cancel() {
		isCancel = true;
	}

	/**
	 * 执行下载任务
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		boolean flag = false;

		localfile = new File(info.fileCreatePath);

		if (localfile.exists()) {
			localfile.delete();
		} else {
			try {
				localfile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (hasNotification) {
			// 如果有下载通知，则创建通知
			createDownloadNotification();
		}

		URLConnection connection = null;
		InputStream ins = null;
		OutputStream os = null;

		try {
			Thread.currentThread().setName(("Thread" + info.id));
			URL url = new URL(info.downloadPath);
			connection = url.openConnection();
			connection.setConnectTimeout(10 * 1000);
			ins = connection.getInputStream();
			// 文件总大小
			long length = connection.getContentLength();
			SWLog.LogD("文件总大小：" + length);
			byte[] bs = new byte[1024];
			int len = -1;
			long count = 0;
			// 记录旧的进度，防止重复通知
			int oldProgress = 0;

			os = new FileOutputStream(localfile);
			if (null != itf) {
				itf.postDownloadInfo(DOWNLOAD_START, 0, length, info.id,null);
			}

			while ((len = ins.read(bs)) != -1 && count < length && !isCancel) {
				os.write(bs, 0, len);
				count += len;
				int progress = (int) (count * 100 / length);
				if (0 == progress % 3 && oldProgress != progress) {
					// 如果下载进度是3的倍数，则发送通知
					SWLog.LogD("progress is ：" + progress);
					if (hasNotification) {
						// 如果有下载通知，则通知下载进度
						mBuilder.setContentText(
								info.appName + "已下载" + progress + "%...")
								.setProgress(100, progress, false);
						mNotifyManager.notify(info.id, mBuilder.build());
					} else {
						// 如果没有下载通知，则通过回调通知下载进度
						if (null != itf) {
							itf.postDownloadInfo(DOWNLOAD_LOADING, progress,length,
									info.id,null);
						}
					}
					oldProgress = progress;
				}
			}
			SWLog.LogD("progress is ：" + 100);
			Thread.sleep(500);
			// 标识完成下载或取消下载
			flag = true;
			os.close();
			ins.close();
		} catch (Exception e) {
			e.printStackTrace();
			SWLog.LogE(e.getMessage());

			if (!isCancel) {
				// 如果在下载失败前未取消下载，则通知下载失败
				if (hasNotification) {
					// 如果有下载通知，则通知下载失败
					mBuilder.setContentText("下载失败!").setProgress(0, 0, false);
					mBuilder.setAutoCancel(true).setOngoing(false);
					mNotifyManager.notify(info.id, mBuilder.build());
				} else {
					// 如果没有下载通知，则通过回调通知下载失败
					if (null != itf) {
						itf.postDownloadInfo(DOWNLOAD_FAIL, 0, 0, info.id,null);
					}
				}
			} else {
				// 如果已经取消了下载，则执行下载取消通知
				flag = true;
			}

			// 下载失败，删除文件
			if (localfile.exists()) {
				localfile.delete();
			}
		} finally {
			if (null != os) {
				try {
					os.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				os = null;
			}
			if (null != ins) {
				try {
					ins.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ins = null;
			}
			if (null != connection) {
				if (connection instanceof HttpURLConnection) {
					SWLog.LogE("关闭连接！");
					((HttpURLConnection)connection).disconnect();
				}
				connection = null;
			}
		}

		if (!flag) {
			SWLog.LogD("下载失败");
			return;
		}

		if (isCancel) {
			if (hasNotification) {
				// 如果有下载通知，则通知已取消下载
				// mBuilder.setContentText("取消下载！").setProgress(0, 0, false);
				// mBuilder.setAutoCancel(true).setOngoing(false);
				// mNotifyManager.notify(info.id, mBuilder.build());
			} else {
				// 如果没有下载通知，则通过回调通知已取消下载
				if (null != itf) {
					itf.postDownloadInfo(DOWNLOAD_CANCEL, 0, 0, info.id,null);
				}
			}

			// 下载取消，删除文件
			if (localfile.exists()) {
				localfile.delete();
			}
		} else {
			if (hasNotification) {
				// 如果有下载通知，则通知下载完成
				mBuilder.setContentText("下载完成！").setProgress(0, 0, false);
				mBuilder.setAutoCancel(true).setOngoing(false);
				mNotifyManager.notify(info.id, mBuilder.build());
				Intent intent = new Intent(context, SWDownloadBroadcast.class);
				intent.setAction("com.seaway.common.net.download.broadcast.SWDownloadBroadcast");
				intent.putExtra("taskState", DOWNLOAD_FINISH);
				intent.putExtra("downloadInfo", info);
				context.sendBroadcast(intent);
			} else {
				// 如果没有下载通知，则通过回调通知下载完成
				if (null != itf) {
					itf.postDownloadInfo(DOWNLOAD_FINISH, 100, 0, info.id,info);
				}
			}
		}
	}

	/**
	 * 创建本地通知实例
	 */
	private void createDownloadNotification() {
		mNotifyManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);

		mBuilder = new NotificationCompat.Builder(context);

		mBuilder.setContentTitle(info.appName)
				.setContentText(info.appName + "下载...")
				.setSmallIcon(info.iconResourceID).setOngoing(true);
		mBuilder.setProgress(100, 0, false);
		Intent intent = new Intent(context, SWDownloadBroadcast.class);
		intent.setAction("com.seaway.common.net.download.broadcast.SWDownloadBroadcast");
		intent.putExtra("taskState", DOWNLOAD_CANCEL);
		intent.putExtra("downloadInfo", info);
		mBuilder.setContentIntent(PendingIntent.getBroadcast(context, 1000,
				intent, PendingIntent.FLAG_CANCEL_CURRENT));
		mNotifyManager.notify(info.id, mBuilder.build());

	}
}
