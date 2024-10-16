// src/main/java/com/example/demo/hilo/CSVReaderService.java
package com.example.demo.hilo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CSVReaderService {
    private final BufferedReader reader;
    private final Lock lock = new ReentrantLock();

    public CSVReaderService(String filePath) throws IOException {
        this.reader = new BufferedReader(new FileReader(filePath));
    }

    public String readLine() throws IOException {
        lock.lock();
        try {
            return reader.readLine();
        } finally {
            lock.unlock();
        }
    }
}