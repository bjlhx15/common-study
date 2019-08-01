package com.lhx.cloud.javathread.MarkWord;

/**
 * @author lihongxu
 * @desc @link(https://github.com/bjlhx15/common-study)
 * @since 2019/3/20 下午6:03
 */
public class Test {
    static Test2 t1 = new Test2();
    Test2 t2 = new Test2();
    public void fn() {
        Test2 t3 = new Test2();
        System.out.println("-----fnfnfn-----");
    }
}
class Test2 {

}
