// src/main/java/com/example/demo/hilo/AsyncService.java
package com.example.demo.hilo;

import com.example.demo.Usuario.Usuario;
import com.example.demo.Usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class AsyncService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Async("taskExecutor")
    public void printData() {
        ExecutorService executor = Executors.newFixedThreadPool(30);
        List<Usuario> usuarios = Collections.synchronizedList(new ArrayList<>());

        // Submit only one task to fetch data
        executor.submit(new UsuarioProcessingTask(usuarioRepository, usuarios));

        executor.shutdown();
        while (!executor.isTerminated()) {
            // Wait for all threads to finish
        }

        // Print the users only once
        synchronized (usuarios) {
            for (Usuario usuario : usuarios) {
                System.out.println("ID: " + usuario.getId() + ", Nombre: " + usuario.getNombre() + ", Contraseña: " + usuario.getContraseña() + ", Admin: " + usuario.isAdmin());
            }
        }
    }
}