package com.example.administrator.text1.ui.testOther;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.administrator.text1.R;
import com.example.administrator.text1.adapter.CircleAdsAdapter;
import com.example.administrator.text1.common.base.BaseActivity;
import com.seaway.android.common.toast.Toast;

import java.util.EnumMap;
import java.util.EnumSet;

/**
 * 功能描述：
 * Created by hzhm on 2016/6/20.
 */
public class TestEnum extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text5);

        WeekDayEnum today = WeekDayEnum.Mon;
        News news = News.ShouHu;
        Content con = Content.Content1;

        //遍历出News里面的所有的枚举类型
        traverseAll();

        //枚举中EnumMap工具类的使用
        testEnumMap();

        //枚举中EnumSet工具类的使用
        testEnumSet();

        traverseAllContent();

        testEnumSetCon();



        //switch条件判断支持枚举类型
        switch (today) {
            case Mon:
                Toast.showToast(this,"Mon");
                break;

            case Tue:
                Toast.showToast(this, "Tue");
                break;

            case Wed:
                Toast.showToast(this, "Wed");
                break;

            case For:
                Toast.showToast(this, "For");
                break;

            case Fri:
                Toast.showToast(this, "Fri");
                break;

            case Sat:
                Toast.showToast(this, "Sat");
                break;

            case Sum:
                Toast.showToast(this, "Sum");
                break;
        }

        switch (con.title) {
            case 1:
                Toast.showToast(this, con.content);
                break;
            case 2:
                Toast.showToast(this, con.content);
                break;
            case 3:
                Toast.showToast(this, con.content);
                break;
        }

    }

    //遍历出News里面的所有的枚举类型
    private void traverseAll() {
        News[] mNews = News.values();
        for (News news1 : mNews) {
            Log.e("当前name", news1.name());
            Log.e("当前次序", news1.ordinal() + "");
            //注：只有当枚举实现了toString方法输出的news1才为“当前属性”
            Log.e("当前属性值", news1 + "");
        }
    }

    //枚举中EnumMap工具类的使用
    private void testEnumMap(){
        EnumMap<News, String> enumMap = new EnumMap<News, String>(News.class);
        enumMap.put(News.XinLang, "新浪");
        enumMap.put(News.TenXun, "腾讯");
        enumMap.put(News.ShouHu, "搜狐");
        for (News news1 : News.values()) {
            Log.e("key:" + news1.name(), "value:" + enumMap.get(news1));
        }
    }

    //枚举中EnumSet工具类的使用
    private void testEnumSet(){
        EnumSet<News> enumSet = EnumSet.allOf(News.class);
        for (News news1 : enumSet) {
            Log.e("EnumSet中的数据为：", news1 + "");
        }
    }

    private void traverseAllContent(){
        Content[] contents = Content.values();
        for (Content content : contents){
            Log.e("Content枚举的当前name",content.name());
            Log.e("Content枚举的当前次序",content.ordinal()+"");
            Log.e("Content枚举的当前属性",content+"");
        }
    }

    private void testEnumSetCon(){
        EnumSet<Content> enumSet = EnumSet.allOf(Content.class);
        for (Content content:enumSet){
            Log.e("Content枚举的当前EnumSet值",content+"");
        }
    }

    /**
     * 一般枚举类型
     */
    enum WeekDayEnum {
        Mon, Tue, Wed, For, Fri, Sat, Sum
    }

    /**
     * 自定义属性枚举类型
     */
    enum News {
        //通过构造函数进行初始化
        XinLang(11), TenXun(22), ShouHu(33);

        //自定义私有属性
        private int title;

        //自定义只能被private修饰的构造方法
        private News(int title) {
            this.title = title;
        }

        @Override
        public String toString() {
            return String.valueOf(this.title);
        }
    }

    enum Content {
        Content1(1, "this is content1"), Content2(2, "this is content2"), Content3(3, "this is content3");

        private int title;
        private String content;

        private Content(int title, String content) {
            this.title = title;
            this.content = content;
        }

        @Override
        public String toString() {
            return this.content;
        }
    }
}
