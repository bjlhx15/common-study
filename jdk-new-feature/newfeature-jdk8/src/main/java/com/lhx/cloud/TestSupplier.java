package com.lhx.cloud;

import java.util.function.Supplier;

public class TestSupplier {
    public static String supplierTest(Supplier<String> supplier) {
        return supplier.get();
    }
    public static void main(String[] args) {
        String name = "测试";
        // () -> name.length() 无参数，返回一个结果（字符串长度）
        // 所以该lambda表达式可以实现Supplier接口
        System.out.println(supplierTest(() -> name.length() + ""));
    }
}
