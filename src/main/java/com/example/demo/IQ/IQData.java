package com.example.demo.IQ;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "iq_data")
public class IQData {

    @Id
    private Long id;

    @Column(name = "`rank`")
    private int rank;
    private String country;
    private int IQ;
    private float educationExpenditure;
    private float avgIncome;
    private float avgTemp;
}