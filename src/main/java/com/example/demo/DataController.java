package com.example.demo;

import com.example.demo.hilo.ExecutorServiceTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataController {

    @Autowired
    private ExecutorServiceTask executorServiceTask;

    @GetMapping("/iqdata/stream")
    public SseEmitter streamIQData() {
        // Crear un emisor SSE con un tiempo de espera largo (por ejemplo, 30 minutos)
        SseEmitter emitter = new SseEmitter(30 * 60 * 1000L); // 30 minutos

        // Ejecutar el método que envía los datos IQ con el emisor
        new Thread(() -> {
            try {
                executorServiceTask.printIQDataWithEmitter(emitter);
            } catch (Exception e) {
                emitter.completeWithError(e);
            }
        }).start();

        return emitter;
    }
@GetMapping("/countries-ranks/stream")
public SseEmitter streamCountriesAndRanks() {
    SseEmitter emitter = new SseEmitter(30 * 60 * 1000L); // 30 minutos

    new Thread(() -> {
        try {
            executorServiceTask.printCountriesAndRanksWithEmitter(emitter);
        } catch (Exception e) {
            emitter.completeWithError(e);
        }
    }).start();

    return emitter;
}
}
