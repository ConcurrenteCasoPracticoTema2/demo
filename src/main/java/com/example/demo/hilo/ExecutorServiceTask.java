// src/main/java/com/example/demo/hilo/ExecutorServiceTask.java
package com.example.demo.hilo;

import com.example.demo.IQ.IQData;
import com.example.demo.IQ.IQDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ExecutorServiceTask {

    @Autowired
    private IQDataRepository iqDataRepository;

    @Autowired
    private ExecutorService fixedThreadPool;

    public void printIQData(SseEmitter emitter) {
        List<IQData> iqDataList = iqDataRepository.findAll();
        AtomicInteger index = new AtomicInteger(0);

        emitter.onCompletion(() -> System.out.println("SseEmitter completed"));
        emitter.onTimeout(() -> {
            emitter.complete();
            System.out.println("SseEmitter timed out");
        });
        emitter.onError((e) -> {
            emitter.completeWithError(e);
            System.out.println("SseEmitter error: " + e.getMessage());
        });

        fixedThreadPool.submit(() -> {
            try {
                while (index.get() < iqDataList.size()) {
                    IQData data = iqDataList.get(index.getAndIncrement());
                    emitter.send(data);
                }
                emitter.complete();
            } catch (Exception e) {
                emitter.completeWithError(e);
            }
        });
    }
}