package com.example.administrator.text1.ui.testContentProvider;

/**
 * @author HuangMing on 2018/11/29.
 */

public class PersonBean {

    private int id;
    private String name;
    private String phone;

    public PersonBean(int id, String name, String phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
