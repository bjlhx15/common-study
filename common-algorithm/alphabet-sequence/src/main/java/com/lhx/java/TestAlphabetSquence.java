package com.lhx.java;

import java.nio.charset.Charset;
import java.util.List;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.hash.Hashing;
import org.junit.Test;

import static java.lang.Math.*;

public class TestAlphabetSquence {
    @Test
    public void testConsistentHash() {
        for (int i = 0; i < 1000; i++) {
            System.out.println("i="+i+":"+getString(i));

        }
    }

    private static String getString(int n) {
        char[] buf = new char[(int) floor(log(25 * (n + 1)) / log(26))];
        for (int i = buf.length - 1; i >= 0; i--) {
            n--;
            buf[i] = (char) ('a' + n % 26);
            n /= 26;
        }
        return new String(buf);
    }
}

