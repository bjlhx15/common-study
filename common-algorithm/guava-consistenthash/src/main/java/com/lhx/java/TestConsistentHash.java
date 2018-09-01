package com.lhx.java;

import java.nio.charset.Charset;
import java.util.List;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.hash.Hashing;
import org.junit.Test;

public class TestConsistentHash {
    @Test
    public void testConsistentHash() {
        List<String> servers = Lists.newArrayList("server1", "server2", "server3", "server4", "server5");

        int bucket = Hashing.consistentHash(Hashing.md5().hashString("someId", Charset.defaultCharset()), servers.size());
        System.out.println("First time routed to: " + servers.get(bucket));

        // one of the back end servers is removed from the (middle of the) pool
//        servers.remove(1);
//        servers.set(1, servers.get(servers.size() - 1));
//        servers.remove(servers.size() - 1);
        //servers.add("server6");

        bucket = Hashing.consistentHash(Hashing.md5().hashString("someId", Charset.defaultCharset()), servers.size());
        System.out.println("Second time routed to: " + servers.get(bucket));
    }

    @Test
    public void testConsistentHash3() {
        List<String> servers = Lists.newArrayList("server1", "server2", "server3", "server4", "server5");
        for (int i = 0; i < 5; i++) {
            System.out.println(i);
            int bucket = Hashing.consistentHash(Hashing.md5().hashString("request" + i, Charset.defaultCharset())
                    , servers.size());
            System.out.println("First time routed to: " + servers.get(bucket));

//            servers.add("server61"+i);
            servers.set(1, servers.get(servers.size() - 1));
            servers.remove(servers.size() - 1);

            bucket = Hashing.consistentHash(Hashing.md5().hashString("request" + i, Charset.defaultCharset()),
                    servers.size());
            System.out.println("Second time routed to: " + servers.get(bucket));
        }
    }

    @Test
    public void testConsistentHash2() {
        List<String> ips = Lists.newArrayList("192.168.1.100",
                "192.168.1.110", "192.168.1.120");
        long ipHashCode1 = Hashing.md5().newHasher().putString(ips.get(0), Charsets.UTF_8).hash().asLong();
        long ipHashCode2 = Hashing.md5().newHasher().putString(ips.get(1), Charsets.UTF_8).hash().asLong();
        long ipHashCode3 = Hashing.md5().newHasher().putString(ips.get(2), Charsets.UTF_8).hash().asLong();

        System.out.println("ip1: " + Hashing.consistentHash(ipHashCode1, 3));
        System.out.println("ip2: " + Hashing.consistentHash(ipHashCode2, 3));
        System.out.println("ip3: " + Hashing.consistentHash(ipHashCode3, 3));
    }
}

