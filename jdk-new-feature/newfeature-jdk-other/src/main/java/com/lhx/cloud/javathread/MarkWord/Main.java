package com.lhx.cloud.javathread.MarkWord;

/**
 * @author lihongxu
 * @desc @link(https://github.com/bjlhx15/common-study)
 * @since 2019/3/20 下午6:03
 */
public class Main  {
    public static void main(String[] args) {
        System.out.println("start");
        Test test = new Test();
        test.fn();
        System.out.println("end");
    }
}
