package com.example.demo.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import jakarta.annotation.PostConstruct;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostConstruct
    public void clearDatabase() {
        usuarioRepository.deleteAll(); // Método para vaciar la tabla de usuario
        System.out.println("La tabla de usuario se ha vaciado.");
    }

    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> getUsuarioById(Integer id) {
        return usuarioRepository.findById(id);
    }

    public Usuario createUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public void deleteUsuario(Integer id) {
        usuarioRepository.deleteById(id);
    }
}