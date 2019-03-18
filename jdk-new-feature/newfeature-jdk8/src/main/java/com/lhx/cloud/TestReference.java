package com.lhx.cloud;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@FunctionalInterface
interface Converter<F, T> {
    T convert(F from);
}
public class TestReference {
    public static void main(String[] args) {
        //静态引用
        Converter<String, Integer> converter = Integer::valueOf;
        Integer converted = converter.convert("123");
        System.out.println(converted);   // 123

        Converter<String, String> converter2 = String::toUpperCase;
        String converted2 = converter2.convert("java");
        System.out.println(converted2);    // "J"

        //指向任意类型实例方法的方法引用(这个实例长为方法的参数)
        List<String> list = Arrays.asList("av","asdf","sad","324","43");
        List<String> collect = list.stream().map(String::toUpperCase).collect(Collectors.toList());
        System.out.println(collect);

        //指向现有对象的实例方法的方法引用(这个实例为外部对象)
        List<String> list2 = Arrays.asList("av","asdf","sad","324","43");
        String a="avasdf";
        List<String> collect2 = list2.stream().filter(a::contains).collect(Collectors.toList());
        System.out.println(collect2);

        //传统Lambda表达式
        Consumer<String> consumer = (x) -> System.out.println(x);
        consumer.accept("Hi: 我是Lambda表达式实现的！");
        //方法引用实现
        consumer = System.out::println;
        consumer.accept("Hello : 我是使用方法引用实现的 ");


        //传统Lambda方式
        Supplier<Map> mapSupplier = ()-> new HashMap<String,String>();
        Map map = mapSupplier.get();
        System.out.println(map);

        //构造器引用
        mapSupplier = HashMap::new;
        map = mapSupplier.get();
        System.out.println(map);

        int num = 1;//等价于 final int num = 1;  固可读，不可修改
        Converter<Integer, String> stringConverter =
                (from) -> String.valueOf(from + num);
        stringConverter.convert(2);     // 3

    }
}
