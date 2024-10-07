package com.example.demo.IQ;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IQDataRepository extends JpaRepository<IQData, Long> {
    @Query(value = "TRUNCATE TABLE iq_data", nativeQuery = true)
    void truncateTable();
}