package com.github.bjlhx15.common.thread.juc.collection;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class UnsafeTest3 {
    public static void main(String[] args) throws Exception {
        unsafeAllocateMemory();
    }
    private static Unsafe getUnsafe(){
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            Unsafe unsafe = (Unsafe) field.get(null);
            return unsafe;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void unsafeAllocateMemory() throws Exception {
        Unsafe unsafe = getUnsafe();
        int BYTE = 1;

        long address = unsafe.allocateMemory(BYTE);
        unsafe.putByte(address, (byte) 10);
        byte num = unsafe.getByte(address);

        System.out.println(num);

        unsafe.freeMemory(address);
    }
}
