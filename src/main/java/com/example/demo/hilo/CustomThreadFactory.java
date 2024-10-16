// src/main/java/com/example/demo/hilo/CustomThreadFactory.java
package com.example.demo.hilo;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class CustomThreadFactory implements ThreadFactory {
    private final AtomicInteger counter = new AtomicInteger(0);

    @Override
    public Thread newThread(Runnable r) {
        return new Thread(r, "poolThread-" + counter.getAndIncrement());
    }
}