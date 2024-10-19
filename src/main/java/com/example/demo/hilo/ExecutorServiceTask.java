package com.example.demo.hilo;

import com.example.demo.IQ.IQData;
import com.example.demo.IQ.IQDataRepository;
import com.example.demo.Usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.concurrent.CountDownLatch;
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
    private ExecutorService fixedThreadPool;

    @Autowired
    private ExecutorService fixedThreadPool2;

    // Semáforo para controlar acceso a los datos entre hilos
    Semaphore semaphore = new Semaphore(1);

    /**
     * Método que procesa la lista de IQData y envía la información de los hilos
     * que están procesando los datos mediante SSE (Server-Sent Events).
     */
    public void printIQDataWithEmitter(SseEmitter emitter) {
        List<IQData> iqDataList = iqDataRepository.findAll();
        AtomicInteger index = new AtomicInteger(0);
        CountDownLatch latch = new CountDownLatch(iqDataList.size());

        // Ejecutar el procesamiento concurrente de IQData
        while (index.get() < iqDataList.size()) {
            fixedThreadPool2.submit(() -> {
                try {
                    // Adquirir semáforo para evitar que varios hilos accedan simultáneamente
                    semaphore.acquire();

                    if (index.get() < iqDataList.size()) {
                        // Obtener el siguiente dato de IQ
                        IQData iqData = iqDataList.get(index.getAndIncrement());

                        // Construir el mensaje con los datos de IQ y el hilo actual
                        String iqDataInfo = Thread.currentThread().getName() + " - ID: " + iqData.getId() +
                                ", Rank: " + iqData.getRank() + ", Country: " + iqData.getCountry() +
                                ", IQ: " + iqData.getIQ() + ", Education Expenditure: " + iqData.getEducationExpenditure() +
                                ", Average Income: " + iqData.getAvgIncome() + ", Average Temperature: " + iqData.getAvgTemp();

                        // Enviar la información al cliente a través de SSE
                        emitter.send(iqDataInfo);

                        // Imprimir la información en consola
                        System.out.println(iqDataInfo);
                    }

                    // Liberar el semáforo para que otros hilos puedan continuar
                    semaphore.release();

                    // Pausa breve entre la ejecución de los hilos
                    Thread.sleep(100);

                } catch (Exception e) {
                    // En caso de error, notificar al cliente y terminar la conexión
                    emitter.completeWithError(e);
                } finally {
                    latch.countDown();
                }
            });
        }

        // Esperar a que todos los hilos terminen antes de completar el emisor SSE
        try {
            latch.await();
            emitter.complete();
        } catch (InterruptedException e) {
            emitter.completeWithError(e);
        }
    }

    /**
     * Método auxiliar que imprime los países asociados a los IQData, ejecutado en paralelo.
     * No utiliza SSE, solo imprime en consola.
     */
    public void printCountries() {
        List<IQData> iqDataList = iqDataRepository.findAll();
        for (IQData iqData : iqDataList) {
            fixedThreadPool.submit(() -> {
                System.out.println(Thread.currentThread().getName() + " - Country: " + iqData.getCountry());
            });
        }
    }

    /**
     * Método para cerrar los ejecutores cuando sea necesario.
     * Debe ser invocado para liberar recursos.
     */
    public void shutdownExecutors() {
        fixedThreadPool.shutdown();
        fixedThreadPool2.shutdown();
    }
}