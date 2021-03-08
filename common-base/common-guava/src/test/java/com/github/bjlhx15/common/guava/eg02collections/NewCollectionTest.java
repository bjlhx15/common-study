package com.github.bjlhx15.common.guava.eg02collections;

import com.google.common.collect.*;
import org.junit.Test;

import java.util.Collection;
import java.util.Map;

public class NewCollectionTest {
    @Test
    public void testMultiSet() {
        Multiset<String> multiset = HashMultiset.create();
        multiset.add("aa");
        multiset.add("bb");
        multiset.add("cc", 2);
        System.out.println(multiset);//[aa, bb, cc x 2]
        System.out.println(multiset.size()); //4
        System.out.println(multiset.count("cc"));//2
        multiset.setCount("bb", 4);
        System.out.println(multiset);//[aa, bb x 4, cc x 2]
    }


    @Test
    public void testMultiMap() {
        Multimap<String, String> multimap = ArrayListMultimap.create();
        multimap.put("fruit", "bannana");
        multimap.put("fruit", "apple");//key可以重复
        multimap.put("fruit", "apple");//value可以重复,不会覆盖之前的
        multimap.put("fruit", "peach");
        multimap.put("fish", "crucian");//欧洲鲫鱼
        multimap.put("fish", "carp");//鲤鱼
        Collection<String> fruits = multimap.get("fruit");
        System.err.println(fruits);//[bannana, apple, apple, peach]

        //对比 HashMultimap
        Multimap<String, String> multimap2 = HashMultimap.create();
        multimap2.put("fruit2", "bannana");
        multimap2.put("fruit2", "apple");
        multimap2.put("fruit2", "apple");

        System.err.println(multimap2.size());//2
        System.err.println(multimap2.get("fruit2"));//[apple, bannana]     注意: 这里只有一个apple
    }


    @Test
    public void testBiMap() {
        BiMap<String, Integer> userId = HashBiMap.create();
        userId.put("lhx", 30);
        userId.put("zll", 28);
        String userForId = userId.inverse().get(30);
        System.out.println(userForId);//lhx

        userId.put("jm", 30);//报错
        String userForId2 = userId.inverse().get(30);
        System.out.println(userForId2);//lhx
    }


    @Test
    public void testTable() {
        Table<String, String, Integer> table = HashBasedTable.create();
        table.put("a", "b", 4);
        table.put("a", "c", 20);
        table.put("b", "c", 5);

        Map<String, Integer> a = table.row("a");// returns a Map mapping {b=4, c=20}
        System.out.println(a);

        Map<String, Integer> column = table.column("c");// returns a Map mapping {a=20, b=5}
        System.out.println(column);

        Integer integer = table.get("a", "c");
        System.out.println(integer); //20
    }


    class Person {
        private String name;

        public Person(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }

    @Test
    public void testClassToInstanceMap() {
        ClassToInstanceMap<Person> instanceMap = MutableClassToInstanceMap.create();
        instanceMap.putInstance(Person.class, new Person("lhx"));
        instanceMap.putInstance(Person.class, new Person("lhx2"));
        Person person = instanceMap.get(Person.class); // Person{name='lhx2'} 存储了 后一个
        System.out.println(person);
    }


    @Test
    public void testRangeSet() {
        RangeSet<Integer> rangeSet = TreeRangeSet.create();

        rangeSet.add(Range.closed(1, 10)); // {[1,10]}
        rangeSet.add(Range.closedOpen(11, 15));//不相连区间:{[1,10], [11,15)}
        rangeSet.add(Range.closedOpen(15, 20)); //相连区间; {[1,10], [11,20)}
        rangeSet.add(Range.openClosed(0, 0)); //空区间; {[1,10], [11,20)}
        rangeSet.remove(Range.open(5, 10)); //分割[1, 10]; {[1,5], [10,10], [11,20)}

        System.out.println(rangeSet);//[[1..5], [10..10], [11..20)]
    }

    @Test
    public void testRangeMap() {
        RangeMap<Integer, String> rangeMap = TreeRangeMap.create();

        rangeMap.put(Range.closed(1, 10), "foo"); //{[1,10] => "foo"}
        rangeMap.put(Range.open(3, 6), "bar"); //{[1,3] => "foo", (3,6) => "bar", [6,10] => "foo"}
        rangeMap.put(Range.open(10, 20), "foo"); //{[1,3] => "foo", (3,6) => "bar", [6,10] => "foo", (10,20) => "foo"}
        rangeMap.remove(Range.closed(5, 11)); //{[1,3] => "foo", (3,5) => "bar", (11,20) => "foo"}

        System.out.println(rangeMap);//[[1..3]=foo, (3..5)=bar, (11..20)=foo]
    }
}
