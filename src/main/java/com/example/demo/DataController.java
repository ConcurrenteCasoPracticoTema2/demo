package com.example.demo;

import com.example.demo.hilo.ExecutorServiceTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataController {

    @Autowired
    private ExecutorServiceTask executorServiceTask;

    @GetMapping("/iqdata/stream")
    public SseEmitter streamIQData() {
        SseEmitter emitter = new SseEmitter(30 * 60 * 1000L); // 30 minutes

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
        SseEmitter emitter = new SseEmitter(30 * 60 * 1000L); // 30 minutes

        new Thread(() -> {
            try {
                executorServiceTask.printCountriesAndRanksWithEmitter(emitter);
            } catch (Exception e) {
                emitter.completeWithError(e);
            }
        }).start();

        return emitter;
    }

    @GetMapping("/countries-iq-temp/stream")
    public SseEmitter streamCountriesIQAndTemp() {
        SseEmitter emitter = new SseEmitter(30 * 60 * 1000L); // 30 minutes

        new Thread(() -> {
            try {
                executorServiceTask.printCountriesIQAndTempWithEmitter(emitter);
            } catch (Exception e) {
                emitter.completeWithError(e);
            }
        }).start();

        return emitter;
    }

    @GetMapping("/countries-iq-additional/stream")
    public SseEmitter streamCountriesIQAndAdditionalData() {
        SseEmitter emitter = new SseEmitter(30 * 60 * 1000L); // 30 minutes

        new Thread(() -> {
            try {
                executorServiceTask.printCountriesIQAndAdditionalDataWithEmitter(emitter);
            } catch (Exception e) {
                emitter.completeWithError(e);
            }
        }).start();

        return emitter;
    }

    @PostMapping("/shutdown-executors")
    public void shutdownExecutors() {
        executorServiceTask.shutdownExecutors();
    }
}