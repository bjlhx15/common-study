package com.lhx.cloud;

import com.lhx.cloud.entity.Birthday;
import com.lhx.cloud.entity.Student;
import net.sf.json.*;
import net.sf.json.processors.JsonValueProcessor;
import net.sf.json.util.PropertyFilter;
import org.apache.commons.beanutils.PropertyUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * <b>function:</b> 用json-lib转换java对象到JSON字符串
 * 读取json字符串到java对象，序列化jsonObject到xml
 */
@SuppressWarnings({"deprecation", "unchecked"})
public class JsonlibTestJsonToBean {
    private JSONArray jsonArray = null;
    private JSONObject jsonObject = null;

    private Student bean = null;
    private String json = "{\"address\":\"chian\",\"birthday\":{\"birthday\":\"2010-11-22\"}," +
            "\"email\":\"email@123.com\",\"id\":22,\"name\":\"tom\"}";

    @Before
    public void init() {
        jsonArray = new JSONArray();
        jsonObject = new JSONObject();

        bean = new Student();
        bean.setAddress("address");
        bean.setEmail("email");
        bean.setId(1);
        bean.setName("haha");
        Birthday day = new Birthday();
        day.setBirthday("2010-11-22");
        bean.setBirthday(day);
    }

    @After
    public void destory() {
        jsonArray = null;
        jsonObject = null;
        bean = null;
        System.gc();
    }

    public final void fail(String string) {
        System.out.println(string);
    }

    public final void failRed(String string) {
        System.err.println(string);
    }

    /**
     * 1、将json字符串转化为java对象
     */
    @Test
    public void readJsonToBean() {
        fail("==============JSON Object String >>> Java Bean ==================");
        jsonObject = JSONObject.fromObject(json);
        Student stu = (Student) JSONObject.toBean(jsonObject, Student.class);
        fail(stu.toString());
    }

    /**
     * 2、将json字符串转换成动态Java对象(MorphDynaBean)
     * 转换后的对象Object是一个MorphDynaBean的动态JavaBean，通过PropertyUtils可以获得指定的属性的值。
     */
    @Test
    public void readJsonToDynaBean() {
        try {
            fail("==============JSON Object String >>> Java MorphDynaBean ==================");
            JSON jo = JSONSerializer.toJSON(json);
            Object o = JSONSerializer.toJava(jo);//MorphDynaBean
            fail(PropertyUtils.getProperty(o, "address").toString());
            jsonObject = JSONObject.fromObject(json);
            fail(jsonObject.getString("email"));
            o = JSONSerializer.toJava(jsonObject);//MorphDynaBean
            fail(PropertyUtils.getProperty(o, "name").toString());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    /**
     * 3、将JSON字符串转成Java的List集合
     */
    @Test
    public void readJSON2Array() {
        try {
            fail("==============JSON Arry String >>> Java Array ==================");
            json = "[" + json + "]";
            jsonArray = JSONArray.fromObject(json);
            fail("#%%%" + jsonArray.get(0).toString());
            Object[] os = jsonArray.toArray();
            System.out.println(os.length);

            fail(JSONArray.fromObject(json).join(""));
            fail(os[0].toString());
            Student[] stus = (Student[]) JSONArray.toArray(jsonArray, Student.class);
            System.out.println(stus.length);
            System.out.println(stus[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 4、将JSON字符串转成Java的List集合
     */
    @Test
    public void readJSON2List() {
        try {
            fail("==============JSON Arry String >>> Java List ==================");
            json = "[" + json + "]";
            jsonArray = JSONArray.fromObject(json);
            List<Student> list = JSONArray.toList(jsonArray, Student.class);
            System.out.println(list.size());
            System.out.println(list.get(0));

            list = JSONArray.toList(jsonArray);
            System.out.println(list.size());
            System.out.println(list.get(0));//MorphDynaBean
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 5、 将json字符串转换成Collection接口
     */
    @Test
    public void readJSON2Collection() {
        try {
            fail("==============JSON Arry String >>> Java Collection ==================");
            json = "[" + json + "]";
            jsonArray = JSONArray.fromObject(json);
            Collection<Student> con = JSONArray.toCollection(jsonArray, Student.class);
            System.out.println(con.size());
            Object[] stt = con.toArray();
            System.out.println(stt.length);
            fail(stt[0].toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 6、 将json字符串转换成Map集合
     */
    @Test
    public void readJSON2Map() {
        try {
            fail("==============JSON Arry String >>> Java Map ==================");
            json = "{\"arr\":[\"a\",\"b\"],\"A\":{\"address\":\"address\",\"birthday\":{\"birthday\":\"2010-11-22\"},"+
                    "\"email\":\"email\",\"id\":1,\"name\":\"jack\"},\"int\":1,"+
                    "\"B\":{\"address\":\"address\",\"birthday\":{\"birthday\":\"2010-11-22\"},"+
                    "\"email\":\"email\",\"id\":1,\"name\":\"jack\"},\"name\":\"json\",\"bool\":true}";
            jsonObject = JSONObject.fromObject(json);
            Map<String, Class<?>> clazzMap = new HashMap<String, Class<?>>();
            clazzMap.put("arr", String[].class);
            clazzMap.put("A", Student.class);
            clazzMap.put("B", Student.class);
            Map<String, ?> mapBean = (Map) JSONObject.toBean(jsonObject, Map.class, clazzMap);
            System.out.println(mapBean);

            Set<String> set = mapBean.keySet();
            Iterator<String> iter = set.iterator();
            while (iter.hasNext()) {
                String key = iter.next();
                fail(key + ":" + mapBean.get(key).toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

