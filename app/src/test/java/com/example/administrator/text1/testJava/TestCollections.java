package com.example.administrator.text1.testJava;

import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by hzhm on 2016/12/12.
 */

public class TestCollections {

    private List<String> list = new ArrayList<>();

    @Test
    public void hellow() {
        testOther();
    }

    @Before
    public void init() {
        list.add("b张三");
        list.add("d孙六");
        list.add("a李四");
        list.add("e钱七");
        list.add("c赵五");
    }

    /**
     * 测试Collections一些有关排序的方法
     */
    private void testSort() {
        System.out.println("原始序列：" + list);

        //reverse,反序列
        Collections.reverse(list);
        System.out.println("reverse后的序列：" + list);

        //shuffle,随机排列
        Collections.shuffle(list);
        System.out.println("shuffle后的序列：" + list);

        //swap,将数字里面指定的i与j元素进行替换
        Collections.swap(list, 0, 4);
        System.out.println("swap后的序列：" + list);

        //sort,自定义比较器进行排序
        Collections.sort(list);
        System.out.println("sort后的序列：" + list);

        //rotate,将所有元素向右移位指定长度，如果distance等于size那么结果不变(注：位移后相应的序列会发生改变，如果位移后的相应数的序列数大于list.size(),则减去list.size()就等于自身序列数)
        Collections.rotate(list, 2);
        System.out.println("rotate后的序列：" + list);
    }

    /**
     * 测试Collectoins一些查找替换的接口方法
     */
    private void testFind() {

        System.out.println("给定的list列表序列：" + list);

        //max,根据自定义比较器，返回最大元素
        System.out.println("max：" + Collections.max(list));
        //min,根据自定义比较器，返回最小元素
        System.out.println("min：" + Collections.min(list));
        //frequency,返回指定集合中指定对象出现的次数
        System.out.println("frequency：" + Collections.frequency(list, "a李四"));
        //replaceAll,替换集合中指定对象
        Collections.replaceAll(list, "a李四", "aa李四");
        System.out.println("replaceAllst列表序列：" + list);
        //binarySearch，返回指定对象在list集合里面对象的序列
        System.out.println("sort之前的序列：" + list);
        System.out.println("sort之前的序列位置：" + Collections.binarySearch(list, "c赵五"));
        Collections.sort(list);
        System.out.println("sort之后的序列：" + list);
        System.out.println("sort之后的序列位置：" + Collections.binarySearch(list, "c赵五"));

        //用指定的元素填充相应的集合list列表
        Collections.fill(list, "A");
        System.out.println("fill填充相应集合后的列表数据：" + list);
    }

    private void testOther() {
        List<String> list1 = new ArrayList<>();
        List<String> list2 = new ArrayList<>();

        //addAll,将所有指定元素添加到指定集合中
        Collections.addAll(list, "f王八");
        System.out.println("addAll之后list的列表：" + list);

        Collections.addAll(list1, "大家好", "你好", "我也好");
        Collections.addAll(list2, "大家好", "a李四", "我也好");

        //disjoint,如果两个指定 collection 中没有相同的元素，则返回 true。
        boolean b1 = Collections.disjoint(list, list1);
        boolean b2 = Collections.disjoint(list, list2);
        System.out.println(b1 + "\t" + b2);

        //reverseOrder，返回一个比较器，它强行反转指定比较器的顺序。
        //（注：sort,自定义比较器进行按序排序；reverseOrder，指的就是返回一个反序的比较器）
        Collections.sort(list,Collections.<String>reverseOrder());
        System.out.println(list);
    }

}
