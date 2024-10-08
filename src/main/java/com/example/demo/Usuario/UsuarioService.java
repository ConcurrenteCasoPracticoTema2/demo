package com.example.demo.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostConstruct
    public void init() {
        clearDatabase();
        createAdminUser();
    }

    public void clearDatabase() {
        usuarioRepository.deleteAll();
        System.out.println("La tabla de usuario se ha vaciado.");
    }

    public void createAdminUser() {
        Usuario adminUser = new Usuario("admin", "admin", true);
        usuarioRepository.save(adminUser);
        System.out.println("Usuario administrador creado: admin");
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