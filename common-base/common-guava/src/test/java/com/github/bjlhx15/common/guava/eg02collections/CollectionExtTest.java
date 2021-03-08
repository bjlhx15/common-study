package com.github.bjlhx15.common.guava.eg02collections;

import com.google.common.collect.*;
import org.junit.Test;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class CollectionExtTest {

    class ListWithDefault<E> extends ForwardingList<E> {
        final E defaultValue;
        final List<E> delegate;

        ListWithDefault(List<E> delegate, E defaultValue) {
            this.delegate = delegate;
            this.defaultValue = defaultValue;
        }

        @Override
        protected List delegate() {
            return delegate;
        }

        @Override
        public E get(int index) {
            E v = super.get(index);
            return (v == null ? defaultValue : v);
        }

        @Override
        public Iterator<E> iterator() {
            final Iterator<E> iter = super.iterator();
            return new ForwardingIterator<E>() {
                @Override
                protected Iterator<E> delegate() {
                    return iter;
                }

                @Override
                public E next() {
                    E v = super.next();
                    return (v == null ? defaultValue : v);
                }
            };
        }
    }

    @Test
    public void testListWithDefault() {
        List<String> names = new ListWithDefault<>(
                Arrays.asList("Alice", null, "Bob", "Carol", null),
                "UNKNOWN"
        );
        names.forEach(p -> System.out.print(p + ",")); //Alice,UNKNOWN,Bob,Carol,UNKNOWN,
        System.out.println();
        System.out.println(names);//[Alice, null, Bob, Carol, null]
    }


    @Test
    public void testpeekingIterator() {
        List<String> source = Lists.newArrayList("a", "b", "b", "c", "a");
        List<String> result = Lists.newArrayList();

        System.out.println(source);//[a, b, b, c, a]
        PeekingIterator<String> iter = Iterators.peekingIterator(source.iterator());

        while (iter.hasNext()) {
            String current = iter.next();
            while (iter.hasNext() && iter.peek().equals(current)) {
                //跳过重复的元素
                iter.next();
            }
            result.add(current);
        }
        System.out.println(result);//[a, b, c, a]
    }

    public Iterator<String> skipNulls(final Iterator<String> in) {
        return new AbstractIterator<String>() {
            protected String computeNext() {
                while (in.hasNext()) {
                    String s = in.next();
                    if (s != null) {
                        return s;
                    }
                }
                return endOfData();
            }
        };
    }

    @Test
    public void testAbstractIterator() {
        List<String> list = Lists.newArrayList("0", "a", null);
        Iterator<String> iterator = skipNulls(list.iterator());
        iterator.forEachRemaining(p -> System.out.print(p + ","));//0,a,
    }


    @Test
    public void testAbstractSequentialIterator() {
        Iterator<Integer> powersOfTwo = new AbstractSequentialIterator<Integer>(1) { // 注意初始值1!
            protected Integer computeNext(Integer previous) {
                return (previous == 1 << 12) ? null : previous * 2;
            }
        };

        powersOfTwo.forEachRemaining(p -> System.out.print(p + ","));//1,2,4,8,16,32,64,128,256,512,1024,2048,4096,
    }
}
