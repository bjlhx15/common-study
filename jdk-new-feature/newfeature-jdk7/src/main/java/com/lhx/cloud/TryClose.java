package com.lhx.cloud;

public class TryClose implements AutoCloseable {
    @Override
    public void close() throws Exception {
        System.out.println(" Custom close method … close resources ");
    }
}
