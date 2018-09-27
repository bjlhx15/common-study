package com.lhx.cloud;

import java.util.function.Function;

public class TestFunInterface {
    public static void main(String[] args) {
        Function<Integer, Integer> times2 = i -> i*2;
        Function<Integer, Integer> squared = i -> i*i;

        System.out.println(times2.apply(4));
        System.out.println(squared.apply(4));

        //先4×4然后16×2,先执行apply(4)，在times2的apply(16),先执行参数，再执行调用者。
        System.out.println(times2.compose(squared).apply(4));  //32

        // 先4×2,然后8×8,先执行times2的函数，在执行squared的函数。
        System.out.println(times2.andThen(squared).apply(4));  //64

        // 取出 Function.identity().compose(squared) 的 squared执行，结果 4x4
        System.out.println(Function.identity().compose(squared).apply(4));   //16
        // 取出 Function.identity().apply(4) 的 4执行，结果 4
        System.out.println(Function.identity().apply(4));   //4

    }
}

//
//// 错误：no target method found
//@FunctionalInterface
//public interface Func{
//}
//
//// 正确
//@FunctionalInterface
//public interface Func1{
//    void run();
//}
//
//// 错误：含有多个抽象方法
//@FunctionalInterface
//public interface Func2{
//    void run();
//    void foo();
//}
//
//// 错误：no target method found，equals 方法签名是Object类的public方法
//@FunctionalInterface
//public interface Func3{
//    boolean equals(Object obj);
//}
//
//// 正确
//@FunctionalInterface
//public interface Func4{
//    boolean equals(Object obj);
//    void run();
//}
//// 错误：可以是Object的public方法，而clone是protected的，这里相当于有两个抽象方法
//@FunctionalInterface
//public interface Func5{
//    Object clone();
//    void run();
//}