// src/main/java/com/example/demo/SEEController.java
package com.example.demo;

import com.example.demo.hilo.ExecutorServiceTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/events")
public class SEEController {

    @Autowired
    private ExecutorServiceTask executorServiceTask;

    @GetMapping("/stream-sse")
    public SseEmitter streamSse() {
        SseEmitter emitter = new SseEmitter();
        executorServiceTask.printIQData(emitter);
        return emitter;
    }
}