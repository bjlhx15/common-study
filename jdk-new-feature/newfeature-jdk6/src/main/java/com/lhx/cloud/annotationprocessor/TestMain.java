package com.lhx.cloud.annotationprocessor;

public class TestMain {
    public static void main(String[] args) throws Exception{
        System.out.println("success");
        test();
    }

    @TestAnno(value = "method is test")
    public static void test()throws Exception{

    }
}
