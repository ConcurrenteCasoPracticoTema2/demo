// src/main/java/com/example/demo/hilo/IQDataProcessingTask.java
package com.example.demo.IQ;

import java.util.List;

public class IQDataProcessingTask implements Runnable {
    private final IQDataRepository iqDataRepository;
    private final List<IQData> iqDataList;

    public IQDataProcessingTask(IQDataRepository iqDataRepository, List<IQData> iqDataList) {
        this.iqDataRepository = iqDataRepository;
        this.iqDataList = iqDataList;
    }

    @Override
    public void run() {
        List<IQData> iqDataListFromDB = iqDataRepository.findAll();
        synchronized (iqDataList) {
            iqDataList.addAll(iqDataListFromDB);
        }
    }
}