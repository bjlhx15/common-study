package com.lhx.cloud;

import java.util.function.Consumer;

public class TestConsumer {
    public static void modifyTheValue3(int value,String a, Consumer<Integer> consumer) {
        System.out.println("aaa"+a);
        consumer.accept(value);
    }

    public static void main(String[] args) {
        // (x) -> System.out.println(x * 2)接受一个输入参数x
        // 直接输出，并没有返回结果
        // 所以该lambda表达式可以实现Consumer接口
        modifyTheValue3(3,"sss", (x) -> System.out.println(x * 2));
    }
}
