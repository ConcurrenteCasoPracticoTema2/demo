package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import  com.example.demo.hilo.ExecutorServiceTask;

@SpringBootApplication
public class DemoApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(DemoApplication.class, args);
        ExecutorServiceTask executorServiceTask = context.getBean(ExecutorServiceTask.class);
        executorServiceTask.printIQData();
        executorServiceTask.printCountries();
    }
}
