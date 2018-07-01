package com.example.administrator.text1.testJava;

import org.junit.Test;

/**
 * @author HuangMing on 2017/11/21.
 *         <p>
 *         功能描述：java基础之---toLowerCase()：将字母转化为小写
 *         indexOf： Java中字符串中子串的查找，并返回当前子串在当前字符串的当前索引
 */

public class TestToLowerCase {

    @Test
    public void testToLowerCase() {
        String str = "AbCdEfGh";
        System.out.print(str.toLowerCase());
    }

    @Test
    public void testIndexOf() {
        String str = "xXccxxxXX";
        System.out.print(str.indexOf("c"));
        System.out.print(str.indexOf("c", 3));
        //从右边开始返回最近的当前字符所在的索引
        System.out.print(str.lastIndexOf("x"));
        //若字符串中没有当前字符，则返回-1
        System.out.print(str.indexOf("y"));
    }
}
