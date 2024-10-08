package com.example.demo;

import com.example.demo.Usuario.Usuario;
import com.example.demo.Usuario.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Scanner;

@Component
public class ConsoleMenu implements CommandLineRunner {

    @Autowired
    private UsuarioService usuarioService;

    @Override
    public void run(String... args) throws Exception {
        // Create default user
        usuarioService.createDefaultUser();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Login");
            System.out.println("2. Exit");
            System.out.print("Choose an option: ");
            int option = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (option == 1) {
                System.out.print("Enter username: ");
                String nombre = scanner.nextLine();
                System.out.print("Enter password: ");
                String contraseña = scanner.nextLine();

                Optional<Usuario> usuario = usuarioService.login(nombre, contraseña);
                if (usuario.isPresent()) {
                    System.out.println("Login successful! Welcome, " + usuario.get().getNombre());
                } else {
                    System.out.println("Invalid username or password.");
                }
            } else if (option == 2) {
                System.out.println("Exiting...");
                break;
            } else {
                System.out.println("Invalid option. Please try again.");
            }
        }

        scanner.close();
    }
}