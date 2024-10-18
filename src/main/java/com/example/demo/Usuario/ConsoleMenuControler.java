package com.example.demo.Menu;

import com.example.demo.Usuario.Usuario;
import com.example.demo.Usuario.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/menu")
public class ConsoleMenuControler {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/login")
    public String login(@RequestParam String nombre, @RequestParam String contraseña) {
        Optional<Usuario> usuario = usuarioService.login(nombre, contraseña);
        if (usuario.isPresent()) {
            return "redirect:/index.html"; // Redirige a index.html
        } else {
            return "Invalid username or password.";
        }
    }

    @PostMapping("/register")
    public String register(@RequestParam String nombre, @RequestParam String contraseña) {
        Usuario newUser = new Usuario(nombre, contraseña, false);
        usuarioService.createUsuario(newUser);
        return "redirect:/index.html"; // Redirige a index.html después del registro
    }
}