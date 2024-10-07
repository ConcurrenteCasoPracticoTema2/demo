package com.example.demo.IQ;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class IQDataService {

    @Autowired
    private IQDataRepository iqDataRepository;

    public void saveCSVData() {
        try {
            ClassPathResource resource = new ClassPathResource("IQ_level_limpio.csv");

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {
                String line;
                List<IQData> iqDataList = new ArrayList<>();
                reader.readLine(); // Skip header
                while ((line = reader.readLine()) != null) {
                    String[] data = line.split(",");
                    IQData iqData = new IQData();
                    iqData.setRank(Integer.parseInt(data[0]));
                    iqData.setCountry(data[1]);
                    iqData.setIQ(Integer.parseInt(data[2]));
                    iqData.setEducationExpenditure(Float.parseFloat(data[3]));
                    iqData.setAvgIncome(Float.parseFloat(data[4]));
                    iqData.setAvgTemp(Float.parseFloat(data[5]));
                    iqDataList.add(iqData);
                }
                iqDataRepository.saveAll(iqDataList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}