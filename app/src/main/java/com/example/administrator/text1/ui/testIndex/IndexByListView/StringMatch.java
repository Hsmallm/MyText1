package com.example.administrator.text1.ui.testIndex.IndexByListView;

/**
 * Created by hzhm on 2016/6/21.
 * 功能描述：查询匹配两个字符串是否相等，即主要是查询ListView的item中是否含有右边的索引
 */
public class StringMatch {

    //value,为listView列表中item的字符；keyword,为索引列表中的字符
   public static boolean macth(String item, String keyword) {
        if (item == null || keyword == null) {
            return false;
        }
        if (keyword.length() > item.length()) {
            return false;
        }

        int i = 0;//i为item的指针
        int j = 0;//j为keyword的指针
        do {
            /** example：
             *
             * 1、item = bcde    keyword = bc
             *
             * 1、i = 0  j = 0; b = b; +1
             * 2、i = 1  j = 1; c = c; +1
             * 3、i = 2  j = 2; break;
             *
             * 2、item = abcde    keyword = bc
             *
             * 1、i = 0  j = 0; b !=a; i+1
             * 2、i = 1  j = 0; b = b; +1
             * 3、i = 2  j = 1; c = c; +1
             * 4、i = 3  j = 2; break
             */
            //当item的某一项与keyword的某一项相等时，则同时加1，继续比较他们的下一项
            if (keyword.charAt(j) == item.charAt(i)) {
                i++;
                j++;
            }
            //...
            else if (j > 0){
                break;
            }
            //如果item前一个和keyword前一个不相等，则继续比较item的下一个
            else {
                i++;
            }
        }
        //满足循环的判断条件...，即都不能超出各自字符长度大小
        while (i < item.length() && j < keyword.length());
       //只有查询成功的，j == keyword.length(),返回为true
        return (j == keyword.length()) ? true : false;
//       return (item.equals(keyword)) ? true : false;
   }

}
