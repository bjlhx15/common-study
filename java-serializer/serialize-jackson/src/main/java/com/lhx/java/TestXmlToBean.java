package com.lhx.java;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.lhx.java.entity.AccountBean;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * 将java对象转换成JSON字符串，也可以将JSON字符串转换成java对象
 */
@SuppressWarnings({"deprecation", "unchecked"})
public class TestXmlToBean {
    private JsonGenerator jsonGenerator = null;
    private ObjectMapper objectMapper = null;
    private AccountBean bean = null;

    @Before
    public void init() {
        bean = new AccountBean();
        bean.setAddress("china-Guangzhou");
        bean.setEmail("hoojo_@126.com");
        bean.setId(1);
        bean.setName("hoojo");

        objectMapper = new ObjectMapper();
        try {
            jsonGenerator = objectMapper.getJsonFactory().createJsonGenerator(System.out, JsonEncoding.UTF8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @After
    public void destory() {
        try {
            if (jsonGenerator != null) {
                jsonGenerator.flush();
            }
            if (!jsonGenerator.isClosed()) {
                jsonGenerator.close();
            }
            jsonGenerator = null;
            objectMapper = null;
            bean = null;
            System.gc();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param xmlFile
     * @param cls
     * @param <T>
     * @return
     * @throws IOException
     */
    public static <T> T xmlToBean(File xmlFile, Class<T> cls) throws IOException {
        XmlMapper xml = new XmlMapper();
        T obj = xml.readValue(xmlFile, cls);
        return obj;
    }

    /**
     * 1、java对象转换成xml文档
     */
    @Test
    public void writeXml2Object() {
        System.out.println("XmlMapper");
        String xml="<AccountBean><id>1</id><name>hoojo</name><email>hoojo_@126.com</email>" +
                "<address>china-Guangzhou</address><birthday/></AccountBean>";
        try {
            XmlMapper xmlMapper = new XmlMapper();
            AccountBean accountBean = xmlMapper.readValue(xml, AccountBean.class);
            System.out.println(accountBean.toString());
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

