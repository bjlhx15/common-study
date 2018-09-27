package com.lhx.cloud;

import java.util.function.Function;
import java.util.function.Predicate;

public class TestPreInterface {
    public static void main(String[] args) {
        String tempStr="Spring is A";
        Predicate<String> length5=p->p.length()>=5;
        Predicate<String> containA=p->p.contains("A");
        Predicate<String> containB=p->p.contains("B");
        // 长度大于5 true
        System.out.println(length5.test(tempStr));
        // 包含A  true
        System.out.println(containA.test(tempStr));

        // 长度大于5 and 包含A  true
        System.out.println(length5.and(containA).test(tempStr));
        // 长度大于5 and 包含B  false
        System.out.println(length5.and(containB).test(tempStr));
        // 长度大于5 or 包含B  false
        System.out.println(length5.or(containB).test(tempStr));
        // 长度大于5 取反  false
        System.out.println(length5.negate().test(tempStr));
        // 长度大于5 取反  false
        System.out.println(!length5.test(tempStr));

    }
}

