package com.lhx.cloud.threadlock;

import org.junit.Test;

/**
 * @author lihongxu
 * @desc @link(https://github.com/bjlhx15/common-study)
 * @since 2019/3/26 下午4:32
 */
public class SampleQueueTest {

    @Test
    public void add() throws InterruptedException {
        SampleQueue<String> queue=new SampleQueue<>(5);
        queue.add("111");
        queue.add("222");
        queue.add("333");
        queue.add("444");
        queue.add("555");
        queue.add("666");
    }

    @Test
    public void remove() throws InterruptedException {
        SampleQueue<String> queue=new SampleQueue<>(5);
        queue.add("111");
        queue.add("222");
        queue.add("333");
        queue.add("444");
        for (int i = 0; i <=4; i++) {
            String remove = queue.remove();
            System.out.println(remove);
        }
        String remove = queue.remove();
        System.out.println(remove);

    }
}