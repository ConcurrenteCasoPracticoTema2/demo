
package com.example.demo.hilo;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class AsyncService {

    @Async("taskExecutor")
    public void printData() {
        try {
            CSVReaderService csvReaderService = new CSVReaderService("src/main/resources/IQ_level_limpio.csv");
            ExecutorService executor = Executors.newFixedThreadPool(5);

            for (int i = 0; i < 10; i++) {
                executor.submit(new CSVProcessingTask(csvReaderService));
            }

            executor.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}