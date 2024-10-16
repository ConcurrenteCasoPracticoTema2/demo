// src/main/java/com/example/demo/hilo/UsuarioProcessingTask.java
package com.example.demo.Usuario;

import java.util.List;

public class UsuarioProcessingTask implements Runnable {
    private final UsuarioRepository usuarioRepository;
    private final List<Usuario> usuarios;

    public UsuarioProcessingTask(UsuarioRepository usuarioRepository, List<Usuario> usuarios) {
        this.usuarioRepository = usuarioRepository;
        this.usuarios = usuarios;
    }

    @Override
    public void run() {
        List<Usuario> usuariosList = usuarioRepository.findAll();
        synchronized (usuarios) {
            usuarios.addAll(usuariosList);
        }
    }
}