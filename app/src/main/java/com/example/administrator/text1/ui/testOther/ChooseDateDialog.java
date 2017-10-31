package com.example.administrator.text1.ui.testOther;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.administrator.text1.R;
import com.example.administrator.text1.model.birthday.DayModel;
import com.example.administrator.text1.model.birthday.MonthModel;
import com.example.administrator.text1.model.birthday.YearModel;
import com.example.administrator.text1.utils.DataUtil;
import com.example.administrator.text1.utils.ScrollerNumberPickerView;
import com.example.administrator.text1.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hzhm on 2016/6/28.
 */
public class ChooseDateDialog extends android.support.v4.app.DialogFragment {

    private List<YearModel> listYear;
    private List<MonthModel> listMonth;
    private List<DayModel> listDay;
    private ArrayList<String> listYearStr;
    private ArrayList<String> listMonthStr;
    private ArrayList<String> listdayStr;

    private ScrollerNumberPickerView pickerYear;
    private ScrollerNumberPickerView pickerMonth;
    private ScrollerNumberPickerView pickerDay;

    private String yearName = "浙江省";
    private String monthName = "杭州市";
    private String dayName = "上城区";

    private ChooseDateDialog.ReceiveDataDialogListener receiveListener;
    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View arg0) {
            if (arg0.getId() == R.id.dlgSubmit) {
                if (null != receiveListener) {
                    receiveListener.setData(yearName, monthName, dayName);
                }
            }
            dismiss();
        }
    };

    public interface ReceiveDataDialogListener {
        void setData(String yearName, String monthName, String dayName);
    }

    public void setListener(ChooseDateDialog.ReceiveDataDialogListener listener) {
        this.receiveListener = listener;
    }

    public static ChooseDateDialog newDialogFragment() {
        ChooseDateDialog dialogFrg = new ChooseDateDialog();
        return dialogFrg;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog dialog = new Dialog(getActivity());
        Window window = dialog.getWindow();
        window.requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.trc_dialog_address_picker);
        init(dialog);

        return dialog;
    }

    private void init(Dialog dialog) {
        TextView txtSubmit = (TextView) dialog.findViewById(R.id.dlgSubmit);
        TextView txtCancle = (TextView) dialog.findViewById(R.id.dlgClose);
        txtSubmit.setOnClickListener(listener);
        txtCancle.setOnClickListener(listener);
        pickerYear = (ScrollerNumberPickerView) dialog.findViewById(R.id.province);
        pickerMonth = (ScrollerNumberPickerView) dialog.findViewById(R.id.city);
        pickerDay = (ScrollerNumberPickerView) dialog.findViewById(R.id.county);
        pickerYear.setEnable(false);
        pickerMonth.setEnable(false);
        pickerDay.setEnable(false);

        listYear = new ArrayList<YearModel>();
        listYearStr = new ArrayList<String>();
        listMonth = new ArrayList<MonthModel>();
        listMonthStr = new ArrayList<String>();
        listDay = new ArrayList<DayModel>();
        listdayStr = new ArrayList<>();

        getAreaListData();
    }

    /**
     * 获取省市区列表
     */
    private void getAreaListData() {
        setupUi(DataUtil.getData2());
    }

    /**
     * 获取省市区列表数据，并完成设置
     */
    private void setupUi(List<YearModel> lists) {
        if (null == lists || lists.size() == 0) {//如果请求、缓存都为为空...
            ToastUtil.showNormalToast("无区域信息可选");
            dismiss();
            return;
        }
        //获取省份列表,并设置默认显示
        listYear = lists;
        for (int i = 0; i < listYear.size(); i++) {
            listYearStr.add(listYear.get(i).name);
        }
        pickerYear.setEnable(true);
        pickerYear.setData(listYearStr);
        pickerYear.setDefault(0);
        yearName = listYear.get(0).name;

        //获取市区列表，并设置默认显示
        listMonth = listYear.get(0).modths;
        monthName = listMonth.get(0).name;
        if (listMonth == null || listMonth.size() == 0) {
            listMonthStr.add("");
            monthName = "";
            pickerMonth.setData(listdayStr);
            pickerMonth.setDefault(0);
            pickerMonth.setEnable(false);
        } else {
            pickerMonth.setEnable(true);
            for (int i = 0; i < listMonth.size(); i++) {
                listMonthStr.add(listMonth.get(i).name);
            }
            pickerMonth.setData(listMonthStr);
            pickerMonth.setDefault(0);
            pickerDay.setEnable(true);
        }

        //获取县区列表，并设置默认显示
        //设置县区列表控件触摸选择不可用
        pickerDay.setEnable(false);
        listDay = listMonth.get(0).days;
        if (listDay == null || listDay.size() == 0) {
            listdayStr.add("");
            dayName = "";
            pickerDay.setData(listdayStr);
            pickerDay.setDefault(0);
            pickerDay.setEnable(false);
        } else {
            dayName = listDay.get(0).name;
            for (int i = 0; i < listDay.size(); i++) {
                listdayStr.add(listDay.get(i).name);
            }
            pickerDay.setData(listdayStr);
            pickerDay.setDefault(0);
            pickerDay.setEnable(true);
        }


        //设置相应的选择监听
        pickerYear.setOnSelectListener(new ScrollerNumberPickerView.OnSelectListener() {
            @Override
            public void selecting(int id, String text) {
            }

            @Override
            public void endSelect(int id, String text) {
//                LogUtil.e("errorCode", "provincePicker-----id:" + id + " name:" + listYear.get(id).name + " code:" + listYear.get(id).code);
                try {
                    //获取当前选中的省份编码、及其名称
                    yearName = listYear.get(id).name;

                    //设置选中省份相关联的市区
                    listMonthStr = new ArrayList<String>();
                    listMonth = listYear.get(id).modths;
                    if (listMonth == null || listMonth.size() == 0) {
                        listMonthStr.add("");
                        monthName = "";
                        pickerMonth.setData(listMonthStr);
                        pickerMonth.setDefault(0);
                        pickerMonth.setEnable(false);
                    }
                    for (int i = 0; i < listMonth.size(); i++) {
                        listMonthStr.add(listMonth.get(i).name);
                    }
                    monthName = listMonth.get(0).name;
                    pickerMonth.setData(listMonthStr);
                    pickerMonth.setDefault(0);
                    pickerMonth.setEnable(true);

                    //设置选中市区相关联的县区
                    listdayStr = new ArrayList<String>();
                    listDay = listMonth.get(0).days;
                    if (listDay == null || listDay.size() == 0) {
                        listdayStr.add("");
                        dayName = "";
                        pickerDay.setData(listdayStr);
                        pickerDay.setDefault(0);
                        pickerDay.setEnable(false);
                    } else {
                        for (int i = 0; i < listDay.size(); i++) {
                            listdayStr.add(listDay.get(i).name);
                        }
                        dayName = listDay.get(0).name;
                        pickerDay.setData(listdayStr);
                        pickerDay.setDefault(0);
                        pickerDay.setEnable(true);
                    }
                } catch (Exception e) {
//                    LogUtil.e(e);
                }
            }
        });

        pickerMonth.setOnSelectListener(new ScrollerNumberPickerView.OnSelectListener() {
            @Override
            public void selecting(int id, String text) {
            }

            @Override
            public void endSelect(int id, String text) {
                try {
                    //获取当前选中的市区编码、及其名称
                    monthName = listMonth.get(id).name;
                    //设置选中市区相关联的县区
                    listdayStr = new ArrayList<String>();
                    listDay = listMonth.get(id).days;
                    if (listDay == null || listDay.size() == 0) {
                        listdayStr.add("");
                        dayName = "";
                        pickerDay.setData(listdayStr);
                        pickerDay.setDefault(0);
                        pickerDay.setEnable(false);
                    } else {
                        for (int i = 0; i < listDay.size(); i++) {
                            listdayStr.add(listDay.get(i).name);
                        }
                        dayName = listDay.get(0).name;
                        pickerDay.setData(listdayStr);
                        pickerDay.setDefault(0);
                        pickerDay.setEnable(true);
                    }
                } catch (Exception e) {
//                    LogUtil.e(e);
                }
            }
        });

        pickerDay.setOnSelectListener(new ScrollerNumberPickerView.OnSelectListener() {

            @Override
            public void selecting(int id, String text) {

            }

            @Override
            public void endSelect(int id, String text) {
                try {
                    if (listDay == null || listDay.size() == 0 || listdayStr == null) {
                        return;
                    } else {
//                        LogUtil.e("errorCode", "countyPicker-----id:" + id + " name:" + listDay.get(id).name + " code:" + listDay.get(id).code);
//                        countyCode = listDay.get(id).code;
                        dayName = listDay.get(id).name;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

