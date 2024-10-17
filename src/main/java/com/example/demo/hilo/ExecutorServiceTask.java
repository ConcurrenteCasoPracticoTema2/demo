package com.example.demo.hilo;

import com.example.demo.IQ.IQData;
import com.example.demo.IQ.IQDataRepository;
import com.example.demo.Usuario.Usuario;
import com.example.demo.Usuario.UsuarioProcessingTask;
import com.example.demo.Usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ExecutorServiceTask {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private IQDataRepository iqDataRepository;

    @Autowired
    private ExecutorServiceFactory executorServiceFactory;

    @Autowired
    private ExecutorService fixedThreadPool;
    @Autowired
    private ExecutorService fixedThreadPool2;

    @Autowired
    private ExecutorService singleThreadExecutor;

    @Autowired
    private ExecutorService customThreadPool;

    Semaphore semaphore = new Semaphore(1);

    public void printCountries() {
        List<IQData> iqDataList = iqDataRepository.findAll();
        for (IQData iqData : iqDataList) {
            fixedThreadPool.submit(() -> {
                System.out.println(Thread.currentThread().getName() + " - Country: " + iqData.getCountry());
            });
        }
    }

    public void printIQData() {
        List<IQData> iqDataList = iqDataRepository.findAll();
        AtomicInteger index = new AtomicInteger(0);

        while (index.get() < iqDataList.size()) {
            fixedThreadPool2.submit(() -> {
                try {
                    semaphore.acquire();
                    if (index.get() < iqDataList.size()) {
                        IQData iqData = iqDataList.get(index.getAndIncrement());
                        System.out.println(Thread.currentThread().getName() + " - ID: " + iqData.getId() + ", Rank: " + iqData.getRank() + ", Country: " + iqData.getCountry() + ", IQ: " + iqData.getIQ() + ", Education Expenditure: " + iqData.getEducationExpenditure() + ", Average Income: " + iqData.getAvgIncome() + ", Average Temperature: " + iqData.getAvgTemp());
                    }
                    semaphore.release();
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }
    }

    public void shutdownExecutors() {
        fixedThreadPool.shutdown();
        singleThreadExecutor.shutdown();
        fixedThreadPool2.shutdown();
    }
}