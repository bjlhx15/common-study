package com.lhx.cloud;

import com.lhx.cloud.entity.Birthday;
import com.lhx.cloud.entity.Student;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.xml.XMLSerializer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * <b>function:</b> 用json-lib转换java对象到JSON字符串
 * 读取json字符串到java对象，序列化jsonObject到xml
 */
@SuppressWarnings({ "deprecation", "unchecked" })
public class JsonlibTestXmlToBean {
    private JSONArray jsonArray = null;
    private JSONObject jsonObject = null;

    private Student bean = null;

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
     * <b>function:</b>转换xml文档到java对象
     */
    @Test
    public void readXML2Object() {
        XMLSerializer xmlSerializer = new XMLSerializer();
        fail("============== XML >>>> Java String Array ==================");
        String[] sa = {"a", "b", "c"};
        jsonArray = (JSONArray) xmlSerializer.read(xmlSerializer.write(JSONArray.fromObject(sa)));
        fail(jsonArray.toString());

        String[] s = (String[]) JSONArray.toArray(jsonArray, String.class);
        fail(s[0].toString());

        fail("============== XML >>>> Java boolean Array ==================");
        boolean[] bo = { true, false, true };
        jsonArray = (JSONArray) xmlSerializer.read(xmlSerializer.write(JSONArray.fromObject(bo)));
        bo = (boolean[]) JSONArray.toArray(jsonArray, boolean.class);
        fail(bo.toString());
        System.out.println(bo[0]);

        jsonArray = (JSONArray) xmlSerializer.read(xmlSerializer.write(JSONSerializer.toJSON(bo)));
        bo = (boolean[]) JSONArray.toArray(jsonArray, boolean.class);
        fail(bo.toString());
        System.out.println(bo[0]);

        fail("==============Java Object Array >>> JSON Array ==================");
        Object[] o = { 1, "a", true, 'A', sa, bo };
        jsonArray = (JSONArray) xmlSerializer.read(xmlSerializer.write(JSONArray.fromObject(o)));
        System.out.println(jsonArray.getInt(0));
        System.out.println(jsonArray.get(1));
        System.out.println(jsonArray.getBoolean(2));
        jsonArray = (JSONArray) xmlSerializer.read(xmlSerializer.write(JSONSerializer.toJSON(o)));
        System.out.println(jsonArray.get(4));
        System.out.println(jsonArray.getJSONArray(5).get(0));
        System.out.println(jsonArray.get(5));

        fail("==============Java String >>> JSON ==================");
        jsonArray = (JSONArray) xmlSerializer.read(xmlSerializer.write(JSONArray.fromObject("['json','is','easy']")).toString());
        s = (String[]) JSONArray.toArray(jsonArray, String.class);
        fail(s[0].toString());
        jsonObject = (JSONObject) xmlSerializer.read(xmlSerializer.write(JSONObject.fromObject("{'json':'is easy'}")).toString());
        Object obj = JSONObject.toBean(jsonObject);
        System.out.println(obj);
        jsonArray = (JSONArray) xmlSerializer.read(xmlSerializer.write(JSONSerializer.toJSON("['json','is','easy']")).toString());
        s = (String[]) JSONArray.toArray(jsonArray, String.class);
        fail(s[1].toString());
    }

    /**
     * 将xml的字符串内容，转换成Java的Array对象
     */
    @Test
    public void testReadXml2Array() {
        String str = "<a class=\"array\">" +
                "<e type=\"function\" params=\"i,j\">" +
                "return matrix[i][j];" +
                "</e>" +
                "</a>";
        JSONArray json = (JSONArray) new XMLSerializer().read(str);
        fail(json.toString());
    }
}

