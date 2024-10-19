package com.example.demo;

import com.example.demo.hilo.ExecutorServiceTask;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class DemoApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(DemoApplication.class, args);
        ExecutorServiceTask executorServiceTask = context.getBean(ExecutorServiceTask.class);

    }
}