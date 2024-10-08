package com.example.demo.hilo;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

@Service
public class AsyncService {

    private final Semaphore semaphore = new Semaphore(1); // Permite a 5 hilos acceder a la vez

    @Async("taskExecutor")
    public void printData() {
        try {
            CSVReaderService csvReaderService = new CSVReaderService("src/main/resources/IQ_level_limpio.csv");
            ExecutorService executor = Executors.newFixedThreadPool(30);

            for (int i = 0; i < 109; i++) {
                executor.submit(() -> {
                    try {
                        semaphore.acquire(); // Adquirir un permiso antes de ejecutar la tarea
                        new CSVProcessingTask(csvReaderService).run();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    } finally {
                        semaphore.release(); // Liberar el permiso después de ejecutar la tarea
                    }
                });
            }

            executor.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}