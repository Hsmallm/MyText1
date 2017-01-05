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
 * 	下载管理回调句柄
 * Reporting Bugs：
 * Modification history：
 * @version 1.0 2014-4-15
 * @since 2.3
 * @author changtao
 */
package com.seaway.android.common.download;

import com.seaway.android.common.download.data.SWDownloadFileInfo;


/**
 * 下载管理回调句柄
 * 
 * @version 1.0 2014-4-17
 * @since 2.3
 * @author changtao
 */
public interface ISWDownloadManagerInterface {
	/**
	 * 通知下载信息
	 * 
	 * @param downloadState
	 *            下载状态；<br>
	 *            SWDownloadTask.DOWNLOAD_FINISH:下载完成；<br>
	 *            SWDownloadTask.DOWNLOAD_CANCEL:下载取消；<br>
	 *            SWDownloadTask.DOWNLOAD_FAIL:下载失败；<br>
	 *            SWDownloadTask.DOWNLOAD_LOADING:下载中；
	 * @param progress
	 *            下载进度百分比
	 * @param id
	 *            线程ID
	 * @param info
	 *            下载文件信息，只有在下载状态为SWDownloadTask.DOWNLOAD_FINISH时有值
	 */
	public void postDownloadInfo(int downloadState, int progress, long length, int id,
								 SWDownloadFileInfo info);
}
