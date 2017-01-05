package com.seaway.android.common.widget;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import android.content.Context;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.util.AttributeSet;

import com.seaway.android.common.R;
import com.seaway.android.common.toast.Toast;
import com.seaway.android.java.toolkit.SWVerificationUtil;
import com.seaway.android.toolkit.base.SWLog;

/**
 * 金额输入框
 * 
 * @author Leo.Chang
 * 
 */
public class UICommonMoneyEditText extends UICommonEditText {
	/**
	 * 要保留的小数点后位数,默认2位
	 */
	private static final int DECIMAL_NUM_LENGTH = 2;

	/**
	 * 最长可输入长度，默认10位
	 */
	private static final int MAX_LENGTH = 10;

	public UICommonMoneyEditText(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public UICommonMoneyEditText(Context context, AttributeSet attrs) {
		super(context, attrs);

		// TODO Auto-generated constructor stub
	}

	public UICommonMoneyEditText(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initEditText() {
		this.setInputType(InputType.TYPE_CLASS_NUMBER
				| InputType.TYPE_NUMBER_FLAG_DECIMAL);
		if (getInputType() == (InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL)) {

			InputFilter[] mFilter = new InputFilter[1];

			mFilter[0] = new InputFilter() {

				@Override
				public CharSequence filter(CharSequence source, int start,
						int end, Spanned dest, int dstart, int dend) {
					// SWLog.LogD("=====================================================");
					// SWLog.LogD("source is : " + source);
					// SWLog.LogD("start is : " + start);
					// SWLog.LogD("end is : " + end);
					// SWLog.LogD("dest is : " + dest);
					// SWLog.LogD("dstart is : " + dstart);
					// SWLog.LogD("dend is : " + dend);
					// SWLog.LogD("=====================================================");

					String s = source.toString();
					String d = dest.toString();
					int dianIndex = d.indexOf(".");

					if (s.length() > 1
							&& !SWVerificationUtil.validateIsAmountWithZero(s)) {
						// 不允许粘贴非金额字符串
						SWLog.LogD("不允许粘贴非金额字符串");
						return "";
					}

					if (s.length() + d.length() > MAX_LENGTH) {
						return "";
					}

					if (dest.length() >= MAX_LENGTH) {
						return "";
					}

					if (0 == dstart && ".".equals(s)) {
						// 不允许在第一位输入小数点
						SWLog.LogD("不允许在第一位输入小数点");
						return "";
					}

					if (d.startsWith("0") && 1 == dstart && !".".equals(s)) {
						// 如果第一位已经是0，且在第二位输入的不是小数点，则不允许输入
						SWLog.LogD("如果第一位已经是0，且在第二位输入的不是小数点，则不允许输入");
						return "";
					}

					if (dianIndex != -1) {
						// 如果包含小数点
						if (s.contains(".")) {
							// 如果已包含小数点，不允许复制包含小数点的数据
							SWLog.LogD("如果已包含小数点，不允许复制包含小数点的数据");
							return "";
						}

						String tem = d.substring(dianIndex + 1);
						if (tem.length() >= DECIMAL_NUM_LENGTH
								&& dstart > dianIndex) {
							// 如果在小数点后输入，且小数点后位数已大于等于最大小数位，则不允许输入
							SWLog.LogD("如果在小数点后输入，且小数点后位数已大于等于最大小数位，则不允许输入");
							return "";
						}
						if (d.length() > 0) {
							if (source.equals("0") && dstart == 0
									&& dianIndex > 0) {
								return "";
							}
						}
					} else {
						if (d.length() > (DECIMAL_NUM_LENGTH + 1)
								&& source.equals(".")
								&& dstart < dest.length() - DECIMAL_NUM_LENGTH) {
							return "";
						}
						// if ("0".equals(d) && !".".equals(source)) {
						// return "";
						// }

						if (dest.toString().startsWith("0")
								&& source.equals("0") && dstart <= 1) {
							return "";
						}
					}

					return null;
				}
			};
			this.setFilters(mFilter);
		}
	}

	@Override
	public boolean checkValue(String emptyMessage) {
		String value = this.getText().toString();
		if (SWVerificationUtil.isEmpty(value)) {
			Toast.showToast(getContext(), emptyMessage);
			return false;
		}

		return true;
	}

	public Editable getRealText() {
		String currency = getText().toString();
		if (SWVerificationUtil.validateIsDoubleString(currency)) {
			// 如果是浮点字符串
			BigDecimal bd = new BigDecimal(currency);
			DecimalFormat df = new DecimalFormat("#####0.00");
			String value = df.format(bd);
			SWLog.LogD("value is : " + value);
			this.setText(value);
			this.setSelection(getText().length());
		}

		return getText();
	}

	/**
	 * 检查金额格式
	 * 
	 * @param emptyMessage
	 *            金额为空的提示语
	 * @param errorFormatMessage
	 *            金额格式错误提示语
	 * @return true：没有问题；false：金额有问题
	 */
	public boolean checkValue(String emptyMessage, String errorFormatMessage) {
		String value = this.getText().toString();
		if (SWVerificationUtil.isEmpty(value)) {
			Toast.showToast(getContext(), emptyMessage);
			return false;
		}

		if (SWVerificationUtil.validateIsDoubleString(value)
				&& 0 == Double.valueOf(value)) {
			// 如果输入的金额是0
			Toast.showToast(getContext(),
					getContext().getString(R.string.common_tips_1008));
			return false;
		}

		if (!SWVerificationUtil.validateIsAmount(value)) {
			Toast.showToast(getContext(), errorFormatMessage);
			return false;
		}

		return true;
	}

	/**
	 * 获取保留小数点后位数(默认2位)
	 * 
	 * @return 保留小数点后位数
	 */
	public int getDecimalNum() {
		return DECIMAL_NUM_LENGTH;
	}

	/**
	 * 获取可输入最大长度(默认10位)
	 * 
	 * @return 可输入最大长度
	 */
	public int getMaxLength() {
		return MAX_LENGTH;
	}

	// private String currentMoney(String currency) {
	// BigDecimal bd = new BigDecimal(currency);
	// DecimalFormat df = new DecimalFormat("#####0.00");
	// String value = df.format(bd);
	// SWLog.LogD("value is : " + value);
	// this.setText(value);
	// // this.setSelection(value.length());
	// return value;
	// }
}
