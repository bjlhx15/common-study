package com.lhx.cloud;

import com.lhx.cloud.entity.Birthday;
import com.lhx.cloud.entity.Classes;
import com.lhx.cloud.entity.ListBean;
import com.lhx.cloud.entity.Student;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 将java对象转换成JSON字符串，也可以将JSON字符串转换成java对象
 */
@SuppressWarnings({"deprecation", "unchecked"})
public class TestBeanToJson {
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
     * 1、XStream结合JettisonMappedXmlDriver驱动，转换Java对象到JSON
     * 需要添加jettison jar
     */
    @Test
    public void writeEntity2JETTSON() {
        failRed("=======JettisonMappedXmlDriver===JavaObject >>>> JaonString=========");
        xstream = new XStream(new JettisonMappedXmlDriver());
        xstream.setMode(XStream.NO_REFERENCES);
        xstream.alias("student", Student.class);
        fail(xstream.toXML(bean));
    }
    /**
     * 2、用XStream结合JsonHierarchicalStreamDriver驱动
     * 转换java对象为JSON字符串
     */
    @Test
    public void writeEntiry2JSON() {
        failRed("======JsonHierarchicalStreamDriver====JavaObject >>>> JaonString=========");
        xstream = new XStream(new JsonHierarchicalStreamDriver());
        //xstream.setMode(XStream.NO_REFERENCES);
        xstream.alias("student", Student.class);
        failRed("-------Object >>>> JSON---------");
        fail(xstream.toXML(bean));

        //failRed("========JsonHierarchicalStreamDriver==删除根节点=========");
        //删除根节点
        xstream = new XStream(new JsonHierarchicalStreamDriver() {
            public HierarchicalStreamWriter createWriter(Writer out) {
                return new JsonWriter(out, JsonWriter.DROP_ROOT_MODE);
            }
        });
        //xstream.setMode(XStream.NO_REFERENCES);
        xstream.alias("student", Student.class);
        fail(xstream.toXML(bean));
    }

    /**
     * 3、 将List集合转换成JSON字符串
     */

    @Test
    public void writeList2JSON() {
        failRed("======JsonHierarchicalStreamDriver====JavaObject >>>> JaonString=========");
        JsonHierarchicalStreamDriver driver = new JsonHierarchicalStreamDriver();
        xstream = new XStream(driver);
        //xstream = new XStream(new JettisonMappedXmlDriver());//转换错误
        //xstream.setMode(XStream.NO_REFERENCES);
        xstream.alias("student", Student.class);

        List<Student> list = new ArrayList<Student>();
        list.add(bean);//add

        bean = new Student();
        bean.setAddress("china");
        bean.setEmail("tom@125.com");
        bean.setId(2);
        bean.setName("tom");
        Birthday day = new Birthday("2010-11-22");
        bean.setBirthday(day);
        list.add(bean);//add

        bean = new Student();
        bean.setName("jack");
        list.add(bean);//add

        fail(xstream.toXML(list));

        //failRed("========JsonHierarchicalStreamDriver==删除根节点=========");
        //删除根节点
        xstream = new XStream(new JsonHierarchicalStreamDriver() {
            public HierarchicalStreamWriter createWriter(Writer out) {
                return new JsonWriter(out, JsonWriter.DROP_ROOT_MODE);
            }
        });
        xstream.alias("student", Student.class);
        fail(xstream.toXML(list));
    }

    /**
     * 4、 Map转换json
     */

    @Test
    public void writeMap2JSON() {
        failRed("======JsonHierarchicalStreamDriver==== Map >>>> JaonString=========");
        xstream = new XStream(new JsonHierarchicalStreamDriver());
        //xstream = new XStream(new JettisonMappedXmlDriver());
        xstream.alias("student", Student.class);

        Map<String, Student> map = new HashMap<String, Student>();
        map.put("No.1", bean);//put

        bean = new Student();
        bean.setAddress("china");
        bean.setEmail("tom@125.com");
        bean.setId(2);
        bean.setName("tom");
        bean.setBirthday(new Birthday("2010-11-21"));
        map.put("No.2", bean);//put

        bean = new Student();
        bean.setName("jack");
        map.put("No.3", bean);//put

        fail(xstream.toXML(map));

        //failRed("========JsonHierarchicalStreamDriver==删除根节点=========");
        //删除根节点
        xstream = new XStream(new JsonHierarchicalStreamDriver() {
            public HierarchicalStreamWriter createWriter(Writer out) {
                return new JsonWriter(out, JsonWriter.DROP_ROOT_MODE);
            }
        });
        xstream.alias("student", Student.class);
        fail(xstream.toXML(map));
    }
}

