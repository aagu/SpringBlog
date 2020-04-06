package com.aagu.blog.util;

import java.util.concurrent.atomic.AtomicInteger;

public class CountUtil {

    private static final class Int {
        private static final AtomicInteger ATOMIC_INTEGER = new AtomicInteger();
    }

    public static AtomicInteger getInstance() {
        return Int.ATOMIC_INTEGER;
    }

    public synchronized static void plus() {
        Int.ATOMIC_INTEGER.incrementAndGet();
    }

    public synchronized static void sub() {
        Int.ATOMIC_INTEGER.decrementAndGet();
    }
}
