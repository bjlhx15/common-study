package com.github.bjlhx15.common.guava.eg01basic;

import com.google.common.base.CharMatcher;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class StringTest {
    // jdk
    @Test
    public void testJdkJoin() {
        List<String> strList = Lists.newArrayList("1", "2", null, "3", "4");
        //字符串拼接，无法跳过空字符串
        String str = String.join(",", strList);
        Assert.assertEquals("1,2,3,4", str);
    }
    // guava
    @Test
    public void testGuavaJoin() {
        List<String> strList = Lists.newArrayList("1", "2", null, "3", "4");
        //字符串拼接，跳过空字符串
        String str = Joiner.on(",").skipNulls().join(strList);
        Assert.assertEquals("1,2,3,4", str);
    }

    @Test
    public void testJdkSplit(){
        String str = "1,2,   3  ,,4,";
        //trimResults():去除空格，omitEmptyStrings()：删除空数组
        String[] strList = str.split(",");
        //  List<String> strList = Splitter.on(",").trimResults().omitEmptyStrings().splitToList(str);
        for (int i = 0; i < strList.length; i++) {
            String s = strList[i];
            System.out.println(s);
        }
        Assert.assertEquals(4,strList.length);
    }

    @Test
    public void testGuavaSplit(){
        String str = "1,2,   3  ,,4,";
        //trimResults():去除空格，omitEmptyStrings()：删除空数组
        List<String> strList = Splitter.on(",").trimResults().omitEmptyStrings().splitToList(str);
        strList.forEach(s -> System.out.println(s));
        Assert.assertEquals(4,strList.size());
    }

    @Test
    public void testSearch(){
        String str = "abcdef";
        //包含字符串cd
        Assert.assertEquals(true,str.contains("cd"));
        //查找以abc开头的字符串
        Assert.assertEquals(true,str.startsWith("abc"));
        //查找以def结尾的字符串
        Assert.assertEquals(true,str.endsWith("def"));
    }

    @Test
    public void testConversion(){
        String str = "abcdef";
        String newStr = str.replace("bcde","hello");
        System.out.println(newStr);
        Assert.assertEquals("ahellof",newStr);
        String str2 = "ABC    abc    123";
        //打印数字
        System.out.println(CharMatcher.digit().retainFrom(str2));
        //打印小写字母
        System.out.println(CharMatcher.javaLowerCase().retainFrom(str2));
        //打印大写字母
        System.out.println(CharMatcher.javaUpperCase().retainFrom(str2));
        //打印所有字母
        System.out.println(CharMatcher.javaLetter().retainFrom(str2));
        //将多余的空格替换成一个空格
        System.out.println(CharMatcher.whitespace().trimAndCollapseFrom(str2,' '));
        //统计出现的字母
        System.out.println(String.valueOf(CharMatcher.javaLetter().countIn(str2)));
    }
}
