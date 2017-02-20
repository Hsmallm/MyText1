package com.example.administrator.text1.testJava;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hzhm on 2016/11/30.
 * 功能描述：测试字符char的相关属性及其使用...
 * 1、java中的char字符型数据类型的相关简介？？？
 * char是Java中的保留字，与别的语言不同的是，char在Java中是16位的，因为Java用的是Unicode。不过8位的ASCII码包含在Unicode中，是从0~127的
 * Java中使用Unicode的原因是，Java的Applet允许全世界范围内运行，那它就需要一种可以表述人类所有语言的字符编码。Unicode。
 * 但是English，Spanish，German, French根本不需要这么表示，所以它们其实采用ASCII码会更高效。
 * 2、字节型byte数据类型和字符型数据类型char的比较？？？
 * byte 是字节数据类型 ，是有符号型的，占1 个字节(即8位)；大小范围为-128—127 。
 * char 是字符数据类型 ，是无符号型的，占2字节(即16位,Unicode码 ）；大小范围 是0—65535 ；char是一个16位二进制的Unicode字符，JAVA用char来表示一个字符
 */

public class TestChar {

    private List<String> list3 = new ArrayList<>();

    @Test
    public void hello() {
        filterLists();
    }

    private void filterLists() {
        List<String> list1 = new ArrayList<>();
        List<String> list2 = new ArrayList<>();
        list1.add("额额额");

        list1.add("啊啊啊");
        list1.add("不不不");
        list1.add("烦烦烦");

        list1.add("踩踩踩");
        list1.add("顶顶顶");
        list1.add("嘎嘎嘎");


        list2.add("啊啊啊");
        list2.add("不不不");
        list2.add("踩踩踩");
        list2.add("顶顶顶");
        list3.add("嘎嘎嘎");
        for (int i = 0; i < list1.size(); i++) {
            for (int j = 0; j < list2.size(); j++) {
                if (list1.get(i).equals(list2.get(j))) {
                    list1.remove(i);
                }
            }
        }
        list3 = list1;
        System.out.println(list3.size());
    }

    /**
     * 1、Char是无符号型的，可以表示一个整数，不能表示负数；而byte是有符号型的，可以表示-128—127 的数；如：
     */
    private void testNumber() {
        char c = (char) -3; // char不能识别负数，必须强制转换否则报错，即使强制转换之后，也无法识别
        System.out.println(c);
        byte d1 = 1;
        byte d2 = -1;
        byte d3 = 127; // 如果是byte d3 = 128;会报错
        byte d4 = -128; // 如果是byte d4 = -129;会报错
        System.out.println(d1);
        System.out.println(d2);
        System.out.println(d3);
        System.out.println(d4);
    }

    /**
     * char可以表中文字符，byte不可以；因为一个中文一般占有两个字节
     */
    private void testChina() {
        char e1 = '中';
        char e2 = '国';
        byte d = (byte) '中';
        System.out.println(e1);
        System.out.println(e2);
        System.out.println(d);
    }

    /**
     * char、byte、int对于英文字符，可以相互转化
     * 注：1、char i = 86：当给char字符类型赋值一个数字时，实质输出赋值的为对应的字符
     * 2、char i = 'X'：当给char字符类型赋值一个字符时，实质输出的为对应的字符
     * (注：字符的赋值只能用''表示，且里面有且只能赋予一个字符)
     */
    private void testEnglish() {
        byte g = 98;   //b对应ASCII是98
        char h = 0;
        char i = 8611;    //U对应ASCII是85
        int j = 'h';    //h对应ASCII是104
        System.out.println(g);
        System.out.println(h);
        System.out.println(i);
        System.out.println(j);
    }
}
