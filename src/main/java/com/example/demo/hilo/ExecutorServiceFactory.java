package com.example.demo.hilo;

import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class ExecutorServiceFactory {

    // Método para crear un ExecutorService con un número fijo de hilos
    public ExecutorService createFixedThreadPool(int threadCount) {
        return Executors.newFixedThreadPool(threadCount);
    }

    // Método para crear un ExecutorService con un solo hilo
    public ExecutorService createSingleThreadExecutor() {
        return Executors.newSingleThreadExecutor();
    }

    // Método para crear un ExecutorService que maneje tareas de manera asíncrona
    public ExecutorService createCachedThreadPool() {
        return Executors.newCachedThreadPool();
    }

    // Método para crear un ExecutorService usando un custom ThreadFactory
    public ExecutorService createCustomThreadPool(int threadCount) {
        return Executors.newFixedThreadPool(threadCount, new CustomThreadFactory());
    }
}
