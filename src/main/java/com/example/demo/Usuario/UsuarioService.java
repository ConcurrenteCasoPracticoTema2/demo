// src/main/java/com/example/demo/Usuario/UsuarioService.java
package com.example.demo.Usuario;

import com.example.demo.IQ.IQDataService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private IQDataService iqDataService;

    public Optional<Usuario> login(String nombre, String contraseña) {
        return usuarioRepository.findByNombreAndContraseña(nombre, contraseña);
    }

    @PostConstruct
    public void init() {
        clearDatabase();
        iqDataService.deleteAll();
        iqDataService.saveCSVData();
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

    public Usuario registerUsuario(String nombre, String contraseña) {
        Usuario newUser = new Usuario(nombre, contraseña, false);
        return usuarioRepository.save(newUser);
    }
}