package com.example.demo.Menu;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "login"; // Devuelve el nombre de la plantilla sin la extensión .html
    }
} // Fin de la clase HomeController