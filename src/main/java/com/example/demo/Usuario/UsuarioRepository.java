package com.example.demo.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    @Query(value = "TRUNCATE TABLE Usuario", nativeQuery = true)
    void truncateTable();
}