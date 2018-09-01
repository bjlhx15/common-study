package com.lhx.cloud;

import com.lhx.cloud.entity.*;
import com.thoughtworks.xstream.XStream;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 将java对象转换成JSON字符串，也可以将JSON字符串转换成java对象
 */
@SuppressWarnings({"deprecation", "unchecked"})
public class TestBeanToXml {
    private XStream xstream = null;
    private ObjectOutputStream out = null;
    private ObjectInputStream in = null;

    private Student bean = null;

    /**
     * 初始化资源准备
     */
    @Before
    public void init() {
        try {
            xstream = new XStream();
            //xstream = new XStream(new DomDriver()); // 需要xpp3 jar
        } catch (Exception e) {
            e.printStackTrace();
        }
        bean = new Student();
        bean.setAddress("china");
        bean.setEmail("jack@email.com");
        bean.setId(1);
        bean.setName("jack");
        Birthday day = new Birthday();
        day.setBirthday("2010-11-22");
        bean.setBirthday(day);
    }

    /**
     * 释放对象资源
     */
    @After
    public void destory() {
        xstream = null;
        bean = null;
        try {
            if (out != null) {
                out.flush();
                out.close();
            }
            if (in != null) {
                in.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.gc();
    }

    public final void fail(String string) {
        System.out.println(string);
    }

    public final void failRed(String string) {
        System.err.println(string);
    }
    /**
     * 1、Java对象转换成XML字符串
     */
    @Test
    public void writeBean2XML() {
        try {
            fail("------------Bean->XML------------");
            fail(xstream.toXML(bean));
            fail("重命名后的XML");
            //类重命名
            //xstream.alias("account", Student.class);
            //xstream.alias("生日", Birthday.class);
            //xstream.aliasField("生日", Student.class, "birthday");
            //xstream.aliasField("生日", Birthday.class, "birthday");
            //fail(xstream.toXML(bean));
            //属性重命名
            xstream.aliasField("邮件", Student.class, "email");
            //包重命名
            xstream.aliasPackage("hoo", "com.hoo.entity");
            fail(xstream.toXML(bean));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 2、将Java的List集合转换成XML对象
     */
    @Test
    public void writeList2XML() {
        try {
            //修改元素名称
            xstream.alias("beans", ListBean.class);
            xstream.alias("student", Student.class);
            fail("----------List-->XML----------");
            ListBean listBean = new ListBean();
            listBean.setName("this is a List Collection");

            List<Object> list = new ArrayList<Object>();
            list.add(bean);
            list.add(bean);//引用bean
            //list.add(listBean);//引用listBean，父元素

            bean = new Student();
            bean.setAddress("china");
            bean.setEmail("tom@125.com");
            bean.setId(2);
            bean.setName("tom");
            Birthday day = new Birthday("2010-11-22");
            bean.setBirthday(day);

            list.add(bean);
            listBean.setList(list);

            //将ListBean中的集合设置空元素，即不显示集合元素标签
            //xstream.addImplicitCollection(ListBean.class, "list");

            //设置reference模型
            //xstream.setMode(XStream.NO_REFERENCES);//不引用
            xstream.setMode(XStream.ID_REFERENCES);//id引用
            //xstream.setMode(XStream.XPATH_ABSOLUTE_REFERENCES);//绝对路径引用

            //将name设置为父类（Student）的元素的属性
            xstream.useAttributeFor(Student.class, "name");
            xstream.useAttributeFor(Birthday.class, "birthday");
            //修改属性的name
            xstream.aliasAttribute("姓名", "name");
            xstream.aliasField("生日", Birthday.class, "birthday");

            fail(xstream.toXML(listBean));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 3、
     */
    @Test
    public void writeList2XML4Annotation() {
        try {
            failRed("---------annotation Bean --> XML---------");
            Student stu = new Student();
            stu.setName("jack");
            Classes c = new Classes("一班", bean, stu);
            c.setNumber(2);
            //对指定的类使用Annotation
            //xstream.processAnnotations(Classes.class);
            //启用Annotation
            //xstream.autodetectAnnotations(true);
            xstream.alias("student", Student.class);
            fail(xstream.toXML(c));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 4、Java Map集合转XML
     */
    @Test
    public void writeMap2XML() {
        try {
            failRed("---------Map --> XML---------");
            Map<String, Student> map = new HashMap<String, Student>();
            map.put("No.1", bean);//put

            bean = new Student();
            bean.setAddress("china");
            bean.setEmail("tom@125.com");
            bean.setId(2);
            bean.setName("tom");
            Birthday day = new Birthday("2010-11-22");
            bean.setBirthday(day);
            map.put("No.2", bean);//put

            bean = new Student();
            bean.setName("jack");
            map.put("No.3", bean);//put

            xstream.alias("student", Student.class);
            xstream.alias("key", String.class);
            xstream.useAttributeFor(Student.class, "id");
            xstream.useAttributeFor("birthday", String.class);
            fail(xstream.toXML(map));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 5、用OutStream输出流写XML
     */
    @Test
    public void writeXML4OutStream() {
        try {
            out = xstream.createObjectOutputStream(System.out);
            Student stu = new Student();
            stu.setName("jack");
            Classes c = new Classes("一班", bean, stu);
            c.setNumber(2);
            failRed("---------ObjectOutputStream # JavaObject--> XML---------");
            out.writeObject(stu);
            out.writeObject(new Birthday("2010-05-33"));
            out.write(22);//byte
            out.writeBoolean(true);
            out.writeFloat(22.f);
            out.writeUTF("hello");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

