package com.lhx.cloud;

interface Formula {
    double calculate(int a);
    default double sqrt(int a) {
        return Math.sqrt(a);
    }
}
public class ExtendMethod {
    public static void main(String[] args) {
        Formula formula = new Formula() {
            @Override
            public double calculate(int a) {
                return sqrt(a * 100);
            }
        };

        //Formula formula2 = (a) -> calculate( a * 100); //报错
        //Formula formula2 = (a) -> sqrt( a * 100); //报错

        System.out.println(formula.calculate(100));     // 100.0
        System.out.println(formula.sqrt(16));           // 4.0

    }
}
