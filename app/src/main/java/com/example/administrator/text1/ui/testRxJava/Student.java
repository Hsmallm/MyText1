package com.example.administrator.text1.ui.testRxJava;

import java.util.List;

/**
 * Created by hzhm on 2017/2/27.
 */

public class Student {

    private String name;
    private List<Course> courses;

    public Student(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
}
