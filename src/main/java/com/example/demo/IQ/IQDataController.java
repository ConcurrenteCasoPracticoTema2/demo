package com.example.demo.IQ;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IQDataController {

    @Autowired
    private IQDataService iqDataService;

    @PostMapping("/IQ_level_limpio.csv")
    public String uploadCSV() {
        iqDataService.saveCSVData();
        return "File uploaded and data saved successfully!";
    }
}