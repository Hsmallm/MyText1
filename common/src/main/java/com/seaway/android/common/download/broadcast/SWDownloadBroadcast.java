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
 * 	文件下载广播接收器
 * Reporting Bugs：
 * Modification history：
 * @version 1.0 2014-4-15
 * @since 2.3
 * @author changtao
 */
package com.seaway.android.common.download.broadcast;

import java.io.File;
import java.io.Serializable;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.seaway.android.common.download.SWDownloadTask;
import com.seaway.android.common.download.data.SWDownloadFileInfo;
import com.seaway.android.common.download.task.SWDownloadTaskManager;
import com.seaway.android.toolkit.base.SWLog;

/**
 * 文件下载广播接收器
 *
 * @version 1.0 2014-4-17
 * @since 2.3
 * @author changtao
 */
public class SWDownloadBroadcast extends BroadcastReceiver {
	/**
	 *
	 * 客户端文件名
	 */
//	private static final String APK_NAME = "MobileBank";

	public SWDownloadBroadcast() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		SWLog.LogD("SWDownloadBroadcast receive");

		int state = intent.getIntExtra("taskState", 0);

		if (state == SWDownloadTask.DOWNLOAD_CANCEL) {
			// 如果接收到取消下载请求
			SWDownloadFileInfo downloadInfo = (SWDownloadFileInfo) intent
					.getSerializableExtra("downloadInfo");
			SWDownloadTaskManager taskManager = SWDownloadTaskManager.getInstance();
			if (null == taskManager.activeAndQueueTasks || 0 == taskManager.activeAndQueueTasks.size()) {
				taskManager = null;
			} else {
				taskManager.cancelExecute(downloadInfo.id);
			}

			NotificationManager mNotifyManager = (NotificationManager) context
					.getSystemService(Context.NOTIFICATION_SERVICE);
			mNotifyManager.cancel(downloadInfo.id);
		} else if (state == SWDownloadTask.DOWNLOAD_FINISH) {
			// 如果下载完成
			SWLog.LogE("下载完成");
			Serializable s = intent.getSerializableExtra("downloadInfo");
			if (null != s && s instanceof SWDownloadFileInfo) {
				SWDownloadFileInfo info = (SWDownloadFileInfo) s;
				if (info.installType == SWDownloadFileInfo.DOWNLOAD_FILE_INSTALL_TYPE_APK_PATCH) {
//					SWLog.LogE("补丁包下载完成");
//					// 如果下载类型是APK补丁包
//					// 获取原APK包
//					String oldPath = context.getApplicationContext().getPackageResourcePath();
//					String newFilePath = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
//							+ "/" + APK_NAME + "_V" + info.version + ".apk";
//					// 合并更新包
//					int result = PatchUtil.patch(oldPath, newFilePath, info.fileCreatePath);
//					if (0 == result) {
//						// 如果合并成功，则直接安装
//						Intent i = new Intent(Intent.ACTION_VIEW);
//						i.setDataAndType(
//								Uri.fromFile(new File(newFilePath)),
//								"application/vnd.android.package-archive");
//						i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//						context.startActivity(i);
//					}
				} else if (info.installType == SWDownloadFileInfo.DOWNLOAD_FILE_INSTALL_TYPE_APK) {
					// 如果下载类型是APK
//					if (info.md5.equals(SWAndroidUtil.getFileMD5(info.fileCreatePath))) {
						// 检查下载版本MD5值与服务端是否一直，如果一致则直接安装，否则提示下载版本不完整
						Intent i = new Intent(Intent.ACTION_VIEW);
						i.setDataAndType(
								Uri.fromFile(new File(info.fileCreatePath)),
								"application/vnd.android.package-archive");
						i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						context.startActivity(i);
//					} else {
//						Toast.showToast(context, "下载版本不完整，请重新下载。");
//					}
				} else if (info.installType == SWDownloadFileInfo.DOWNLOAD_FILE_INSTALL_TYPE_ZIP) {
					// 如果下载类型是ZIP包

				}
			}
		}
	}
}