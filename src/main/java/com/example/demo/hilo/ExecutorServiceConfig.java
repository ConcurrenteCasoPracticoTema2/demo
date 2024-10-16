package com.example.demo.hilo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class ExecutorServiceConfig {

    @Bean(name = "fixedThreadPool")
    public ExecutorService fixedThreadPool() {
        return Executors.newFixedThreadPool(5);  // Crea un pool fijo de 5 hilos
    }

    @Bean(name = "singleThreadExecutor")
    public ExecutorService singleThreadExecutor() {
        return Executors.newSingleThreadExecutor();  // Crea un pool de un solo hilo
    }

    @Bean(name = "customThreadPool")
    public ExecutorService customThreadPool() {
        return Executors.newCachedThreadPool();  // Crea un pool de hilos según demanda
    }
}
