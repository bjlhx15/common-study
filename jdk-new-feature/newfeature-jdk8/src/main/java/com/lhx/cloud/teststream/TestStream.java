package com.lhx.cloud.teststream;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

public class TestStream {
    public static void main(String[] args) {
        List<Person> list = new ArrayList<>();
        list.add(new Person("zhangsan", 10, true));
        list.add(new Person("zhangsan", 10, true));
        list.add(new Person("zhangsan2", 19, false));
        list.add(new Person("zhangsan3", 15, true));
        list.add(new Person("zhanglisi", 15, true));

        list.forEach(p -> System.out.println("原始人群：\r\n" + p));

        List<Person> result = list.stream()
                .filter(p -> p.getIsStudent())
                .collect(toList());

        result.forEach(p -> System.out.println("是学生人员：\r\n" + p));

        result = list.stream()
                .distinct()
                .collect(toList());
        result.forEach(p -> System.out.println("去除重复：\r\n" + p));

        result = list.stream()
                .sorted((a, b) -> a.getAge().compareTo(b.getAge()))
                .collect(toList());

        result = list.stream()
                .sorted(Comparator.comparing(Person::getAge))
                .collect(toList());
        result.forEach(p -> System.out.println("排序：\r\n" + p));

        long count = list.stream().count();
        Long count2 = list.stream().collect(Collectors.counting());
        System.out.println(count);

        Optional<Person> personOptionalMax = list.stream().max(Comparator.comparing(p -> p.getAge()));
        Optional<Person> personOptionalMax1 = list.stream().collect(maxBy(Comparator.comparing(Person::getAge)));


        Optional<Person> personOptionalMin = list.stream().min(Comparator.comparing(p -> p.getAge()));
        Optional<Person> personOptionalMin1 = list.stream().collect(minBy(Comparator.comparing(Person::getAge)));


        Integer sum = list.stream().collect(summingInt(Person::getAge));
        Double avg = list.stream().collect(averagingInt(Person::getAge));

        DoubleSummaryStatistics dss = list.stream().collect(Collectors.summarizingDouble(p -> p.getAge()));
        double average = dss.getAverage();
        double max = dss.getMax();
        double min = dss.getMin();
        double sum1 = dss.getSum();
        double count1 = dss.getCount();


        Map<Integer, List<Person>> collect = list.stream().collect(Collectors.groupingBy(p -> p.getAge(), Collectors.toList()));
        System.out.println(1);
        //先按照年龄分组，再分组内再次按照名称分组
        Map<Integer, Map<String, List<Person>>> collect1 = list.stream().collect(
                Collectors.groupingBy(p -> p.getAge(), Collectors.groupingBy(t -> t.getName())));

    }
}
