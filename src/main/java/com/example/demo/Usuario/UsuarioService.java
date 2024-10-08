package com.example.demo.Usuario;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Optional<Usuario> login(String nombre, String contraseña) {
        return usuarioRepository.findByNombreAndContraseña(nombre, contraseña);
    }

    @PostConstruct
    public void init() {
        clearDatabase();
        createAdminUser();
        createDefaultUser();
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

    public void createDefaultUser() {
        Optional<Usuario> existingUser = usuarioRepository.findByNombreAndContraseña("1", "1");
        if (existingUser.isEmpty()) {
            Usuario defaultUser = new Usuario("1", "1", false);
            usuarioRepository.save(defaultUser);
            System.out.println("Usuario por defecto creado: username=1, password=1");
        } else {
            System.out.println("El usuario por defecto ya existe.");
        }
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