package com.example.demo.LimpiezaBase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class DatabaseCleanupService {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    public void clearDatabase() {
        jdbcTemplate.execute("TRUNCATE TABLE Usuario");
    }
}