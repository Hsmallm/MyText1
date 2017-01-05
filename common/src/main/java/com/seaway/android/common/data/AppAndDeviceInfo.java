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
 * ClassName：AppAndDeviceInfo.java
 * Description：
 * 	应用及设备信息
 * Reporting Bugs：
 * Modification history：
 * @version 1.0 2014-4-10
 * @since 2.3
 * @author changtao
 */
package com.seaway.android.common.data;

public class AppAndDeviceInfo  {
	/**
	 * 手机唯一标识号
	 */
	public String uuid;

	/**
	 * SIM卡IMEI号
	 */
	public String imei;
	
	/**国际移动用户识别码*/
	public String imsi;

	/**
	 * 运营商信息
	 */
	public String provider;
	
	/**
	 * 操作系统类型
	 */
	public String systemType = "A";
	/**
	 * 屏幕宽度
	 */
	public String screenWidth;
	/**
	 * 屏幕高度
	 */
	public String screenHeight;

	/**
	 * 设备硬件制造商<br>
	 * Build.MANUFACTURER
	 */
	public String manufacturer;

	/**
	 * 手机型号<br>
	 * Build.MODEL
	 */
	public String model;

	/**
	 * 系统版本号 <br>
	 * Build.VERSION.RELEASE
	 */
	public String systemVersion;

	/**
	 * 应用程序版本
	 */
	public String appVersion;

	/**
	 * 设备所在经度
	 */
	public String longitude;

	/**
	 * 设备所在纬度
	 */
	public String latitude;
	
	/**设备所在城市*/
	public String city;
	
	/**设备所在具体地址*/
	public String address;
	
	
}
