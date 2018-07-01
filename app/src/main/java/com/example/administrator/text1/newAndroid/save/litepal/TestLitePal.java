package com.example.administrator.text1.newAndroid.save.litepal;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.administrator.text1.R;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * @author HuangMing on 2017/11/27.
 *         功能描述：LitePal是一款开源的Android数据库框架，采用的是对象关系映射（ORM）模式，并将我们平时开发最常用的数据库功能进行了封装
 *         LitePal的配置：1.大部分开源项目都有提交到jcenter上，所以可以直接在app/build文件中引用开源库：compile:'org.litepal.android,core:1.4.1'
 *         2.右键app/src/main目录--》New-->Directory，创建一个assets目录下再创建一个litepal.xml文件
 *         3.最后一部就是配置LitePalApplication，org.litepal.LitePalApplication
 */

public class TestLitePal extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_sharedpreferences);

        //向表单中插入数据
        Button btn2 = (Button) findViewById(R.id.btn2);
        btn2.setText("AddTable");
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Book book = new Book();
                book.setName("The Famous HXM");
                book.setAuthor("DAVA");
                book.setPages(66);
                book.setPrice(18.8);
                book.save();
            }
        });

        //更新表单数据
        Button btn3 = (Button) findViewById(R.id.btn3);
        btn3.setText("UpdateTable");
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //a、最简单的一种方式，对已存储的对象重新赋值，然后再调用save
                Book book = new Book();
                book.setName("");
                book.setAuthor("");
                book.setPrice(88.88);
                book.save();
                book.setPrice(78.88);
                book.save();

                //b、使用updateAll()
                Book book2 = new Book();
                book2.setPrice(14.95);
                book2.setPages(10);
                book2.updateAll("name = ? author = ?", "The Lost Sysbol", "Dan");
            }
        });

        //删除表单数据
        Button btn4 = (Button) findViewById(R.id.btn4);
        btn4.setText("DeleteTable");
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataSupport.deleteAll(Book.class, "price < ?", "15");
            }
        });

        //查询表单数据
        Button btn5 = (Button) findViewById(R.id.btn5);
        btn5.setText("FindTable");
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Book> list = DataSupport.findAll(Book.class);
                for (Book book : list) {
                    Log.d("TestLitePal", "Table" + book.getAuthor());
                }

                //order()：指定结果的排序方式；表示查询的结果按照价格的降序排列
                List<Book> book1 = DataSupport.order("price desc").find(Book.class);

                //limit()：指定查询结果的数量；offset()：指定查询结果的偏移量
                //表示查询偏移一位之后查询前三条数据(查询第二、第三、第四这三条数据)
                List<Book> list2 = DataSupport.limit(3).offset(1).find(Book.class);

                //findBySQL()：原生的SQL语句查询，注：查询的返回结构为一个Cursor对象
                Cursor c = DataSupport.findBySQL("select * from Book weher pages > ? and price < ?", "400", "20");
            }
        });
    }
}
