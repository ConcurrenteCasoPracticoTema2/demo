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

    public void printRow56() {
        IQData iqData = iqDataRepository.findById(56L).orElse(null);
        singleThreadExecutor.submit(() -> {
            if (iqData != null) {
                System.out.println(Thread.currentThread().getName() + " - ID: " + iqData.getId() + ", Rank: " + iqData.getRank() + ", Country: " + iqData.getCountry() + ", IQ: " + iqData.getIQ() + ", Education Expenditure: " + iqData.getEducationExpenditure() + ", Average Income: " + iqData.getAvgIncome() + ", Average Temperature: " + iqData.getAvgTemp());
            } else {
                System.out.println("No data found for ID 56");
            }
        });
    }

    public void printData() {
        List<Usuario> usuarios = Collections.synchronizedList(new ArrayList<>());

        singleThreadExecutor.submit(new UsuarioProcessingTask(usuarioRepository, usuarios));

        while (!singleThreadExecutor.isTerminated()) {
            // Esperar a que termine el hilo
        }

        synchronized (usuarios) {
            for (Usuario usuario : usuarios) {
                System.out.println(Thread.currentThread().getName() + " - ID: " + usuario.getId() + ", Nombre: " + usuario.getNombre() + ", Contraseña: " + usuario.getContraseña() + ", Admin: " + usuario.isAdmin());
            }
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