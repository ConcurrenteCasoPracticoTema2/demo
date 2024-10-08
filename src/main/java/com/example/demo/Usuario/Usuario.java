package com.example.demo.Usuario;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 50)
    private String nombre;

    @Column(nullable = false)
    private String contraseña;

    @Column(nullable = false)
    private boolean isAdmin;

    public Usuario() {
    }

    public Usuario(String nombre, String contraseña, boolean isAdmin) {
        this.nombre = nombre;
        this.contraseña = contraseña;
        this.isAdmin = isAdmin;
    }
}