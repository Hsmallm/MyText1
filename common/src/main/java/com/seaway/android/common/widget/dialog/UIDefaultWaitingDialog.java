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
 * ClassName：UIDefaultWaitingDialog.java
 * Description：
 * 	
 * Reporting Bugs：
 * Modification history：
 * @version 1.0 2014-4-30
 * @since 2.3
 * @author changtao
 */
package com.seaway.android.common.widget.dialog;


import com.seaway.android.common.R;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;

/**
 * 
 * 
 * 
 * @version 1.0 2014-4-30
 * @since 2.3
 * @author changtao
 * 
 */
public class UIDefaultWaitingDialog extends Dialog {

	public UIDefaultWaitingDialog(Context context) {
		super(context, R.style.UIDefaultWaitingProgressDialog);
		// TODO Auto-generated constructor stub
		this.setContentView(R.layout.ui_default_waiting_dialog);
		this.getWindow().getAttributes().gravity = Gravity.CENTER;
	}

}
