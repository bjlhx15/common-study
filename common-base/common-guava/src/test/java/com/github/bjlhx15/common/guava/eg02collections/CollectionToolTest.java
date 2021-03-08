package com.github.bjlhx15.common.guava.eg02collections;

import com.google.common.collect.*;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.checkerframework.checker.units.qual.K;
import org.junit.Test;

import java.awt.*;
import java.time.Instant;
import java.util.*;
import java.util.List;
import java.util.function.Function;

public class CollectionToolTest {

    @Test
    public void testNewInstance() {
        //jdk 7 之前
        List<String> list1 = new ArrayList<String>();
        //jdk 7 之后
        List<String> list2 = new ArrayList<>();
        //Guava的静态工厂方法
        ArrayList<String> list3 = Lists.newArrayList();
        ArrayList<String> list4 = Lists.newArrayList("a", "b");
        ArrayList<String> list5 = Lists.newArrayListWithCapacity(100);
        // guava 集合创建
        Multiset<String> multiset = HashMultiset.create();
    }


    @Test
    public void testIterables() {
        ArrayList<String> ab = Lists.newArrayList("a", "b");
        ArrayList<String> bc = Lists.newArrayList("b", "c");
        Iterable<String> concat = Iterables.concat(ab, bc);
        System.out.println(concat);//[a, b, b, c]
        Iterable<List<String>> lists = Iterables.paddedPartition(ab, 1);
        System.out.println(lists);//[[a], [b]]
    }

    class Person {
        private String name;
        private Integer age;

        public Person(String name, Integer age) {
            this.name = name;
            this.age = age;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }

    @Test
    public void testMapsuniqueIndex() {
        ArrayList<Person> list = Lists.newArrayList(new Person("lhx", 22), new Person("zl", 20));

        // 得到以name为key，Person为值的一个map
        ImmutableMap<String, Person> uniqueIndex = Maps.uniqueIndex(list, p -> p.name);
        System.out.println(uniqueIndex);//{lhx=Person{name='lhx', age=22}, zl=Person{name='zl', age=20}}

        list.add(new Person("lhx", 32));
        // 报错 索引不唯一
        //ImmutableMap<String, Person> uniqueIndex2 = Maps.uniqueIndex(list, p -> p.name);
        //System.out.println(uniqueIndex2);
    }


    @Test
    public void testMapsdifference() {
        Map<String, Integer> left = ImmutableMap.of("a", 1, "b", 2, "c", 3);
        Map<String, Integer> right = ImmutableMap.of("a", 1, "b", 3, "e", 5);

        MapDifference<String, Integer> diff = Maps.difference(left, right);


        Map<String, Integer> stringIntegerMap;
        //两个Map中都有的映射项，包括匹配的键与值
        stringIntegerMap = diff.entriesInCommon();
        System.out.println(stringIntegerMap);//{a=1}

        //键相同但是值不同值映射项。
        Map<String, MapDifference.ValueDifference<Integer>> differing = diff.entriesDiffering();
        System.out.println(differing);//{b=(2, 3)}

        //键只存在于左边Map的映射项
        stringIntegerMap = diff.entriesOnlyOnLeft();
        System.out.println(stringIntegerMap);//{c=3}

        //键只存在于右边Map的映射项
        stringIntegerMap = diff.entriesOnlyOnRight();
        System.out.println(stringIntegerMap);//{e=5}

    }


}
