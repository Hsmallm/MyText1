package com.seaway.android.common.widget.dialog;


import com.seaway.android.common.R;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;

public class UIDefaultDialog extends Dialog{

	public UIDefaultDialog(Context context) {
		super(context, R.style.UIDefaultWaitingProgressDialog);
		this.getWindow().getAttributes().gravity = Gravity.CENTER;
	}

}
