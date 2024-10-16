package com.example.demo.hilo;

import com.example.demo.IQ.IQData;
import com.example.demo.IQ.IQDataRepository;
import com.example.demo.Usuario.Usuario;
import com.example.demo.Usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class AsyncService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private IQDataRepository iqDataRepository;

    @Autowired
    private ExecutorServiceFactory executorServiceFactory;

    Semaphore semaphore = new Semaphore(1);

    @Async("taskExecutor")
    public void printCountries() {
        List<IQData> iqDataList = iqDataRepository.findAll();
        ExecutorService executor = executorServiceFactory.createFixedThreadPool(5);
        for (IQData iqData : iqDataList) {
            executor.submit(() -> {
                System.out.println(Thread.currentThread().getName() + " - Country: " + iqData.getCountry());
            });
        }
        executor.shutdown();
    }

    @Async("taskExecutor")
    public void printRow56() {
        IQData iqData = iqDataRepository.findById(56L).orElse(null);
        if (iqData != null) {
            System.out.println(Thread.currentThread().getName() + " - ID: " + iqData.getId() + ", Rank: " + iqData.getRank() + ", Country: " + iqData.getCountry() + ", IQ: " + iqData.getIQ() + ", Education Expenditure: " + iqData.getEducationExpenditure() + ", Average Income: " + iqData.getAvgIncome() + ", Average Temperature: " + iqData.getAvgTemp());
        } else {
            System.out.println("No data found for ID 56");
        }
    }

    @Async("taskExecutor")
    public CompletableFuture<Void> printData() {
        ExecutorService executor = executorServiceFactory.createSingleThreadExecutor();
        List<Usuario> usuarios = Collections.synchronizedList(new ArrayList<>());

        // Submit only one task to fetch data
        executor.submit(new UsuarioProcessingTask(usuarioRepository, usuarios));

        executor.shutdown();
        while (!executor.isTerminated()) {
            // Wait for all threads to finish
        }

        // Print the users only once
        synchronized (usuarios) {
            for (Usuario usuario : usuarios) {
                System.out.println(Thread.currentThread().getName() + " - ID: " + usuario.getId() + ", Nombre: " + usuario.getNombre() + ", Contraseña: " + usuario.getContraseña() + ", Admin: " + usuario.isAdmin());
            }
        }

        return CompletableFuture.completedFuture(null);
    }

    @Async("taskExecutor")
    public CompletableFuture<Void> printIQData() {
        List<IQData> iqDataList = iqDataRepository.findAll();
        ExecutorService executor = executorServiceFactory.createCustomThreadPool(10);
        AtomicInteger index = new AtomicInteger(0);

        while (index.get() < iqDataList.size()) {
            executor.submit(() -> {
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

        executor.shutdown();
        while (!executor.isTerminated()) {
            // Wait for all threads to finish
        }

        return CompletableFuture.completedFuture(null);
    }
}
