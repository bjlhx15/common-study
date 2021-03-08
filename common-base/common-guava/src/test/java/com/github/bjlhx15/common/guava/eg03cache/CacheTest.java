package com.github.bjlhx15.common.guava.eg03cache;

import com.google.common.cache.*;
import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class CacheTest {
    class Person {
        private String name;

        public Person(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }

    public com.google.common.cache.CacheLoader<String, Person> createCacheLoader() {
        return new com.google.common.cache.CacheLoader<String, Person>() {
            @Override
            public Person load(String key) throws Exception {
                System.out.println("加载创建key:" + key);
                return new Person(key + ":ddd");
            }
        };
    }

    @Test
    public void testCreateCacheLoader() throws ExecutionException {
        LoadingCache<String, Person> cache = CacheBuilder.newBuilder()
                .maximumSize(1000)
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .build(createCacheLoader());
        cache.put("aa", new Person("aaa"));
        Person aa = cache.get("aa");
        System.out.println(aa);//Person{name='aaa'}


        Person bb = cache.get("bb");
        System.out.println(bb); //加载创建key:bb   Person{name='bb:ddd'}
    }


    public Person doThingsTheHardWay(String key) {
        System.out.println(key);
        return new Person("");
    }

    @Test
    public void testCreateCallable() throws Exception {
        Cache<String, Person> cache = CacheBuilder.newBuilder()
                .maximumSize(1000)
                .build(); // look Ma, no CacheLoader

        try {
            cache.put("aa", new Person("aaaa"));
            // If the key wasn't in the "easy to compute" group, we need to
            // do things the hard way.
            Person aa = cache.get("aa", new Callable<Person>() {
                @Override
                public Person call() throws Exception {
                    return new Person("defalut");
//                    return doThingsTheHardWay(key);
                }
            });
            System.out.println(aa);//Person{name='aaaa'}
        } catch (Exception e) {
            throw new Exception(e.getCause());
        }

        Person bb = cache.get("bb", () -> new Person("defalut"));
        System.out.println(bb); //Person{name='defalut'}
    }


    @Test
    public void testWeight() throws Exception {
        LoadingCache<String, Person> cache = CacheBuilder.newBuilder()
                .maximumWeight(5)
                .weigher((Weigher<String, Person>) (s, person) -> {
                    //权重计算器
                    int weight = person.name.length();
                    System.out.println("key:" + s);
                    return weight;
                })
                .build(new CacheLoader<String, Person>() {
                    @Override
                    public Person load(String key) {
                        System.out.println("加载创建key:" + key);
                        return new Person(key + ":default");
                    }
                });

        cache.put("a", new Person("aaaaaaa1"));
        cache.put("b", new Person("bbbbbb1"));
        cache.put("c", new Person("cc1"));

        Person a = cache.get("a");
        System.out.println(a);
        Person b = cache.get("b");
        System.out.println(b);
        Person c = cache.get("c");
        System.out.println(c);

        //缓存只有 一个 c
        System.out.println(cache.asMap());
    }


    @Test
    public void testEvictionByAccessTime() throws ExecutionException, InterruptedException {
        LoadingCache<String, Person> cache = CacheBuilder.newBuilder()
                .expireAfterAccess(2, TimeUnit.SECONDS)
                .build(createCacheLoader());
        cache.getUnchecked("wangji");
        TimeUnit.SECONDS.sleep(3);
        Person employee = cache.getIfPresent("wangji"); //不会重新加载创建cache
        System.out.println("被销毁：" + (employee == null ? "是的" : "否"));
        cache.getUnchecked("guava");

        TimeUnit.SECONDS.sleep(1);
        employee = cache.getIfPresent("guava"); //会重新加载创建cache
        System.out.println(employee);
        System.out.println("被销毁：" + (employee == null ? "是的" : "否"));

        TimeUnit.SECONDS.sleep(2);
        employee = cache.getIfPresent("guava"); //不会重新加载创建cache
        System.out.println("被销毁：" + (employee == null ? "是的" : "否"));

        TimeUnit.SECONDS.sleep(2);
        employee = cache.getIfPresent("guava"); //不会重新加载创建cache
        System.out.println("被销毁：" + (employee == null ? "是的" : "否"));

        TimeUnit.SECONDS.sleep(2);
        employee = cache.getIfPresent("guava"); //不会重新加载创建cache
        System.out.println("被销毁：" + (employee == null ? "是的" : "否"));
    }

    @Test
    public void testWeakKey() throws ExecutionException, InterruptedException {
        LoadingCache<String, Person> cache = CacheBuilder.newBuilder()
//                .weakValues()
                .weakKeys()
                .softValues()
                .build(createCacheLoader());
        cache.getUnchecked("guava");
        cache.getUnchecked("wangji");

        System.gc();
        TimeUnit.MILLISECONDS.sleep(100);
        Person employee = cache.getIfPresent("guava"); //不会重新加载创建cache
        System.out.println("被销毁：" + (employee == null ? "是的" : "否"));
    }


    @Test
    public void testCacheRemovedNotification() {
        CacheLoader<String, String> loader = CacheLoader.from(String::toUpperCase);
        RemovalListener<String, String> listener = notification -> {
            if (notification.wasEvicted()) {
                RemovalCause cause = notification.getCause();
                System.out.println("remove cause is :" + cause.toString());
                System.out.println("key:" + notification.getKey() + "value:" + notification.getValue());
            }
        };
        LoadingCache<String, String> cache = CacheBuilder.newBuilder()
                .maximumSize(3)
                .removalListener(listener)// 添加删除监听
                .build(loader);
        cache.getUnchecked("lhx");
        cache.getUnchecked("wangwang");
        cache.getUnchecked("guava");
        cache.getUnchecked("test");
        cache.getUnchecked("test1");
    }

    @Test
    public void testCacheRefresh() throws InterruptedException {
        AtomicInteger counter = new AtomicInteger(0);
        CacheLoader<String, Long> cacheLoader = CacheLoader.from(k -> {
            counter.incrementAndGet();
            System.out.println("创建 key :" + k);
            return System.currentTimeMillis();
        });
        LoadingCache<String, Long> cache = CacheBuilder.newBuilder()
                .refreshAfterWrite(2, TimeUnit.SECONDS) // 2s后重新刷新
                .build(cacheLoader);


        Long result1 = cache.getUnchecked("guava");
        TimeUnit.SECONDS.sleep(3);
        Long result2 = cache.getUnchecked("guava");
        System.out.println(result1.longValue() != result2.longValue() ? "是的" : "否");
    }
}
