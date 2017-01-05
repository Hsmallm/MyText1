package com.example.administrator.text1.utils;

import com.example.administrator.text1.model.ExampleModel;

import java.util.ArrayList;
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
}
