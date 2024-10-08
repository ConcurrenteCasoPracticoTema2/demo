package com.example.demo.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, Model model) {
        Optional<Usuario> usuario = usuarioService.login(username, password);
        if (usuario.isPresent()) {
            Usuario user = usuario.get();
            model.addAttribute("usuario", user);
            if (user.isAdmin()) {
                return "admin";
            } else {
                return "user";
            }
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "login";
        }
    }

    @GetMapping("/create-user-form")
    public String showCreateUserForm() {
        return "create-user";
    }

    @PostMapping("/create-user")
    public String createUser(@RequestParam String newUsername, @RequestParam String newPassword, Model model) {
        Usuario newUser = new Usuario(newUsername, newPassword, false);
        usuarioService.createUsuario(newUser);
        model.addAttribute("message", "User created successfully");
        return "login";
    }

    @GetMapping("/usuarios")
    public String getAllUsuarios(Model model) {
        model.addAttribute("usuarios", usuarioService.getAllUsuarios());
        return "usuarios";
    }
}