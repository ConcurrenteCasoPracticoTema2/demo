package com.example.demo.IQ;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface IQDataRepository extends JpaRepository<IQData, Long> {
    @Query(value = "TRUNCATE TABLE iq_data", nativeQuery = true)
    void truncateTable();
    @Modifying
    @Query(value = "ALTER TABLE iq_data AUTO_INCREMENT = 1", nativeQuery = true)
    void resetAutoIncrement();
}