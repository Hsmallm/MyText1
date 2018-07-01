package com.example.administrator.text1.newAndroid.save.litepal;

import org.litepal.crud.DataSupport;

/**
 * @author HuangMing on 2017/11/27.
 *         功能描述：LitePal采用的是一种对象关系映射模式：也就是将面向对象的语言和面向关系的数据库之间建立一种映射关系；
 *         强大功能：以面向对象的思维来操作数据库(例如：数据库的建表，就可以用面向对象的思维来建立一个Model)；继承DataSupport是因为要进行CRUD操作时，需要用到一些Api
 */

public class Book extends DataSupport {

    private int id;
    private String author;
    private double price;
    private int pages;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
