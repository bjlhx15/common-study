package com.lhx.cloud;

import com.lhx.cloud.entity.Birthday;
import com.lhx.cloud.entity.Classes;
import com.lhx.cloud.entity.ListBean;
import com.lhx.cloud.entity.Student;
import com.thoughtworks.xstream.XStream;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StringReader;
import java.util.*;

/**
 * 将java对象转换成JSON字符串，也可以将JSON字符串转换成java对象
 */
@SuppressWarnings({"deprecation", "unchecked"})
public class TestXmlToBean {
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
     * 1、用InputStream将XML文档转换成java对象
     */
    @Test
    public void readXML4InputStream() {
        try {
            String s = "<object-stream><com.lhx.cloud.entity.Student><id>0</id><name>jack</name>" +
                    "</com.lhx.cloud.entity.Student><com.lhx.cloud.entity.Birthday><birthday>2010-05-33</birthday>" +
                    "</com.lhx.cloud.entity.Birthday><byte>22</byte><boolean>true</boolean><float>22.0</float>" +
                    "<string>hello</string></object-stream>";
            failRed("---------ObjectInputStream## XML --> javaObject---------");
            StringReader reader = new StringReader(s);
            in = xstream.createObjectInputStream(reader);
            Student stu = (Student) in.readObject();
            Birthday b = (Birthday) in.readObject();
            byte i = in.readByte();
            boolean bo = in.readBoolean();
            float f = in.readFloat();
            String str = in.readUTF();
            System.out.println(stu);
            System.out.println(b);
            System.out.println(i);
            System.out.println(bo);
            System.out.println(f);
            System.out.println(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 2、将XML字符串转换成Java对象
     */
    @Test
    public void readXml2Object() {
        try {
            failRed("-----------Xml >>> Bean--------------");
            Student stu = (Student) xstream.fromXML(xstream.toXML(bean));
            fail(stu.toString());

            List<Student> list = new ArrayList<Student>();
            list.add(bean);//add

            Map<String, Student> map = new HashMap<String, Student>();
            map.put("No.1", bean);//put

            bean = new Student();
            bean.setAddress("china");
            bean.setEmail("tom@125.com");
            bean.setId(2);
            bean.setName("tom");
            Birthday day = new Birthday("2010-11-22");
            bean.setBirthday(day);
            list.add(bean);//add
            map.put("No.2", bean);//put

            bean = new Student();
            bean.setName("jack");
            list.add(bean);//add
            map.put("No.3", bean);//put

            failRed("==========XML >>> List===========");
            List<Student> studetns = (List<Student>) xstream.fromXML(xstream.toXML(list));
            fail("size:" + studetns.size());//3
            for (Student s : studetns) {
                fail(s.toString());
            }

            failRed("==========XML >>> Map===========");
            Map<String, Student> maps = (Map<String, Student>) xstream.fromXML(xstream.toXML(map));
            fail("size:" + maps.size());//3
            Set<String> key = maps.keySet();
            Iterator<String> iter = key.iterator();
            while (iter.hasNext()) {
                String k = iter.next();
                fail(k + ":" + map.get(k));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

