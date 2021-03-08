package com.github.bjlhx15.common.guava.eg02collections;

import com.google.common.base.CharMatcher;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.Lists;
import org.junit.Assert;
import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CollectionTest {

    // jdk
    @Test
    public void testJDKImmutable(){
        List<String> list=new ArrayList<String>();
        list.add("a");
        list.add("b");
        list.add("c");

        //通过list创建一个不可变的unmodifiableList集合
        List<String> unmodifiableList= Collections.unmodifiableList(list);
        System.out.println(unmodifiableList);

        //通过list添加元素
        list.add("ddd");
        System.out.println("往list添加一个元素:"+list);
        System.out.println("通过list添加元素之后的unmodifiableList:"+unmodifiableList);

        //通过unmodifiableList添加元素
        unmodifiableList.add("eee");
        System.out.println("往unmodifiableList添加一个元素:"+unmodifiableList);
    }
    @Test
    public void testGuavaImmutable(){
        List<String> list=new ArrayList<String>();
        list.add("a");
        list.add("b");
        list.add("c");

        //ImmutableList.copyOf
        ImmutableList<String> imlist=ImmutableList.copyOf(list);
        System.out.println("imlist："+imlist);

        //ImmutableList.of
        ImmutableList<String> imOflist=ImmutableList.of("peida","jerry","harry");
        System.out.println("imOflist："+imOflist);

        ImmutableSortedSet<String> imSortList=ImmutableSortedSet.of("a", "b", "c", "a", "d", "b");
        System.out.println("imSortList："+imSortList);

        list.add("baby");
        //关键看这里是否imlist也添加新元素了
        System.out.println("list添加新元素之后看imlist:"+imlist);

        ImmutableSet<Color> imColorSet =
                ImmutableSet.<Color>builder()
                        .add(new Color(0, 255, 255))
                        .add(new Color(0, 191, 255))
                        .build();

        System.out.println("imColorSet:"+imColorSet);
    }


    @Test
    public void testCotyOf(){
        ImmutableSet<String> imSet=ImmutableSet.of("peida","jerry","harry","lisa");
        System.out.println("imSet："+imSet);

        //set直接转list
        ImmutableList<String> imlist=ImmutableList.copyOf(imSet);
        System.out.println("imlist："+imlist);

        //list直接转SortedSet
        ImmutableSortedSet<String> imSortSet=ImmutableSortedSet.copyOf(imSet);
        System.out.println("imSortSet："+imSortSet);

        List<String> list=new ArrayList<String>();
        for(int i=0;i<=10;i++){
            list.add(i+"x");
        }
        System.out.println("list："+list);

        //截取集合部分元素
        ImmutableList<String> imInfolist=ImmutableList.copyOf(list.subList(2, 8));
        System.out.println("imInfolist："+imInfolist);
    }
}
