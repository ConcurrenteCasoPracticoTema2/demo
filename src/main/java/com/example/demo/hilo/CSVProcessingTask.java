// src/main/java/com/example/demo/hilo/CSVProcessingTask.java
package com.example.demo.hilo;

import java.io.IOException;

public class CSVProcessingTask implements Runnable {
    private final CSVReaderService csvReaderService;

    public CSVProcessingTask(CSVReaderService csvReaderService) {
        this.csvReaderService = csvReaderService;
    }

    @Override
    public void run() {
        try {
            String line = csvReaderService.readLine();
            if (line != null) {
                System.out.println(Thread.currentThread().getName() + ": " + line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}