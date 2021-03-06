package com.lhx.cloud.entity;

import com.thoughtworks.xstream.annotations.*;

import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

@XStreamAlias("class")
public class Classes {

    /*
     * 设置属性显示
     */
    @XStreamAsAttribute
    @XStreamAlias("名称")
    private String name;

    /*
     * 忽略
     */
    @XStreamOmitField
    private int number;

    @XStreamImplicit(itemFieldName = "Students")
    private List<Student> students;

    @SuppressWarnings("unused")
    @XStreamConverter(SingleValueCalendarConverter.class)
    private Calendar created = new GregorianCalendar();


    public Classes(){}
    public Classes(String name, Student... stu) {
        this.name = name;
        this.students = Arrays.asList(stu);
    }
    //getter、setter

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public Calendar getCreated() {
        return created;
    }

    public void setCreated(Calendar created) {
        this.created = created;
    }
}
