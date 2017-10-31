package com.example.administrator.text1.utils;

import com.example.administrator.text1.model.ExampleModel;
import com.example.administrator.text1.model.birthday.DayModel;
import com.example.administrator.text1.model.birthday.MonthModel;
import com.example.administrator.text1.model.birthday.YearModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by hzhm on 2016/9/29.
 * 功能描述：数据工具类，根据相应的model模板，生成相应的假数据源
 */

public class DataUtil {

    public static final int MODEL_COUNT = 30;

    public static List<ExampleModel> getData() {
        List<ExampleModel> exampleModelLists = new ArrayList<>();
        for (int index = 0; index < MODEL_COUNT; index++) {
            if (index < 5) {
                exampleModelLists.add(new ExampleModel(
                        "吸顶文本1", "name" + index, "gender" + index, "profession" + index));
            } else if (index < 15) {
                exampleModelLists.add(new ExampleModel(
                        "吸顶文本2", "name" + index, "gender" + index, "profession" + index));
            } else if (index < 25) {
                exampleModelLists.add(new ExampleModel(
                        "吸顶文本3", "name" + index, "gender" + index, "profession" + index));
            } else {
                exampleModelLists.add(new ExampleModel(
                        "吸顶文本4", "name" + index, "gender" + index, "profession" + index));
            }
        }
        return exampleModelLists;
    }

    public static List<YearModel> getData2() {
        List<YearModel> dateList = new ArrayList<>();//年
        for (int i = 1950; i < 2018; i++) {
            final YearModel yearModel = new YearModel(i + "年");
            dateList.add(yearModel);
            for (int j = 0; j < 12; j++) {
                MonthModel monthModel = new MonthModel((j+1)+"月");
                yearModel.addMonthModel(monthModel);
                int maxDay = getMaxDay(i, j);
                for (int n = 1; n < maxDay + 1; n++) {
                    DayModel dayModel = new DayModel(n+"日");
                    monthModel.addDayModel(dayModel);
                }
            }
        }
        return dateList;
    }

    public static int getMaxDay(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear(Calendar.DAY_OF_MONTH);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.YEAR, year);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }
}
