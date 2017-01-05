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
 * 	下载文件信息
 * Reporting Bugs：
 * Modification history：
 * @version 1.0 2014-4-15
 * @since 2.3
 * @author changtao
 */
package com.seaway.android.common.download.data;

import java.io.Serializable;


/**
 * 下载文件信息封装类
 * 
 * @version V1.0,2014-03-13
 * @since SDK1.5
 * @author changtao
 * 
 */
public class SWDownloadFileInfo  implements Serializable {
	/**
	 * 下载文件类型：APK安装包
	 */
	public static final int DOWNLOAD_FILE_INSTALL_TYPE_APK = 0;

	/**
	 * 下载文件类型：ZIP包
	 */
	public static final int DOWNLOAD_FILE_INSTALL_TYPE_ZIP = 1;

	/**
	 * 下载文件类型：APK补丁包
	 */
	public static final int DOWNLOAD_FILE_INSTALL_TYPE_APK_PATCH = 2;

	/**
	 * 序列号编号
	 */
	private static final long serialVersionUID = -2155734225139880800L;

	/**
	 * 构造方法
	 * 
	 * @param id
	 *            文件编号
	 * @param downloadPath
	 *            下载地址
	 * @param fileCreatePath
	 *            文件保存地址
	 * @param appName
	 *            应用名称
	 * @param iconResourceID
	 *            通知图片
	 * @param installType
	 *            下载文件安装类型<br>
	 *            DOWNLOAD_FILE_INSTALL_TYPE_APK：APK安装包；<br>
	 *            DOWNLOAD_FILE_INSTALL_TYPE_ZIP：ZIP包；<br>
	 *            DOWNLOAD_FILE_INSTALL_TYPE_APK_PATCH：APK补丁包
	 * @param version
	 *            文件版本号
	 */
	public SWDownloadFileInfo(int id, String downloadPath,
			String fileCreatePath, String appName, int iconResourceID,
			int installType, String md5, String version) {
		super();
		this.id = id;
		this.downloadPath = downloadPath;
		this.fileCreatePath = fileCreatePath;
		this.appName = appName;
		this.iconResourceID = iconResourceID;
		this.installType = installType;
		this.md5 = md5;
		this.version = version;
	}

	/**
	 * 文件编号
	 */
	public int id;

	/**
	 * 文件下载地址
	 */
	public String downloadPath;

	/**
	 * 保存创建路径
	 */
	public String fileCreatePath;

	/**
	 * 应用名称，用于显示在Notification中
	 */
	public String appName;

	/**
	 * 文件图标资源编号
	 */
	public int iconResourceID;

	/**
	 * 安装类型
	 */
	public int installType;

	/**
	 * 下载文件MD5
	 */
	public String md5;

	/**
	 * 文件版本号
	 */
	public String version;
}
