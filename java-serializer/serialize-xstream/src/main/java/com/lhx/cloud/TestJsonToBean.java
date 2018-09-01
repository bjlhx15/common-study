package com.lhx.cloud;

import com.lhx.cloud.entity.Birthday;
import com.lhx.cloud.entity.Student;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;
import org.codehaus.jettison.json.JSONException;
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
public class TestJsonToBean {
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
     * <b>function:</b>JsonHierarchicalStreamDriver可以将简单的json字符串转换成java对象，list、map转换不成功；
     * JsonHierarchicalStreamDriver读取JSON字符串到java对象出错
     * @author hoojo
     * @createDate Nov 27, 2010 1:22:26 PM
     * @throws JSONException
     */
    @Test
    public void readJSON2Object() throws JSONException {
        String json = "{\"student\": {" +
                "\"id\": 1," +
                "\"name\": \"haha\"," +
                "\"email\": \"email\"," +
                "\"address\": \"address\"," +
                "\"birthday\": {" +
                "\"birthday\": \"2010-11-22\"" +
                "}" +
                "}}";
        //JsonHierarchicalStreamDriver读取JSON字符串到java对象出错，但JettisonMappedXmlDriver可以
        xstream = new XStream(new JettisonMappedXmlDriver());
        xstream.alias("student", Student.class);
        fail(xstream.fromXML(json).toString());

        //JettisonMappedXmlDriver转换List集合出错，但JsonHierarchicalStreamDriver可以转换正确
        //JettisonMappedXmlDriver 转换的字符串 {"list":{"student":[{"id":1,"name":"haha","email":"email","address":"address","birthday":[{},"2010-11-22"]}]},"student":{"id":2,"name":"tom","email":"tom@125.com","address":"china","birthday":[{},"2010-11-22"]}}
        json = "{\"list\": [{" +
                "\"id\": 1," +
                "\"name\": \"haha\"," +
                "\"email\": \"email\"," +
                "\"address\": \"address\"," +
                "\"birthday\": {" +
                "\"birthday\": \"2010-11-22\"" +
                "}" +
                "},{" +
                "\"id\": 2," +
                "\"name\": \"tom\"," +
                "\"email\": \"tom@125.com\"," +
                "\"address\": \"china\"," +
                "\"birthday\": {" +
                "\"birthday\": \"2010-11-22\"" +
                "}" +
                "}]}";
        System.out.println(json);//用js转换成功
        List list = (List) xstream.fromXML(json);
        System.out.println(list.size());//0好像转换失败
    }
}

