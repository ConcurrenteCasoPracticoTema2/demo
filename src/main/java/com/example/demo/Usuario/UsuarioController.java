package com.example.demo.Usuario;

import com.example.demo.LimpiezaBase.DatabaseCleanupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    private DatabaseCleanupService databaseCleanupService;
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/vaciar")
    public void vaciarTabla() {
        usuarioService.clearDatabase();
    }

    @GetMapping
    public List<Usuario> getAllUsuarios() {
        return usuarioService.getAllUsuarios();
    }

    @GetMapping("/{id}")
    public Optional<Usuario> getUsuarioById(@PathVariable Integer id) {
        return usuarioService.getUsuarioById(id);
    }

    @PostMapping
    public Usuario createUsuario(@RequestBody Usuario usuario) {
        return usuarioService.createUsuario(usuario);
    }

    @DeleteMapping("/{id}")
    public void deleteUsuario(@PathVariable Integer id) {
        usuarioService.deleteUsuario(id);
    }

    @PostMapping("/login")
    public ResponseEntity<Usuario> login(@RequestParam String nombre, @RequestParam String contraseña) {
        Optional<Usuario> usuario = usuarioService.login(nombre, contraseña);
        if (usuario.isPresent()) {
            return ResponseEntity.ok(usuario.get());
        } else {
            return ResponseEntity.status(401).build();
        }
    }
}