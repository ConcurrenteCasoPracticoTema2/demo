package com.example.demo;

import com.example.demo.hilo.AsyncService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class DemoApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(DemoApplication.class, args);
        AsyncService asyncService = context.getBean(AsyncService.class);
        asyncService.printData();
    }
}