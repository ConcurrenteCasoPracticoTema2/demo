package com.example.demo.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.util.UUID;

@Component
public class RandomUserGenerator {

    @Autowired
    private UsuarioService usuarioService;

    @PostConstruct
    public void generateRandomUser() {
        String randomUsername = "user_" + UUID.randomUUID().toString().substring(0, 8);
        String randomPassword = UUID.randomUUID().toString().substring(0, 12);

        Usuario randomUser = new Usuario(randomUsername, randomPassword);
        usuarioService.createUsuario(randomUser);

        System.out.println("Random user created: " + randomUsername + " with password: " + randomPassword);
    }
}