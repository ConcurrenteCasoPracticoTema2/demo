// src/main/java/com/example/demo/hilo/ConsoleMenuLauncher.java
package com.example.demo.hilo;

import com.example.demo.Menu.ConsoleMenu;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.logging.Logger;

@Component
public class ConsoleMenuLauncher {

    private static final Logger logger = Logger.getLogger(ConsoleMenuLauncher.class.getName());

    @Autowired
    private ConsoleMenu consoleMenu;

    @Autowired
    private ExecutorService singleThreadExecutor;

    @PostConstruct
    public void launchConsoleMenu() {
        logger.info("Launching Console Menu...");
        singleThreadExecutor.submit(consoleMenu);
        openBrowser();
    }

    private void openBrowser() {
        String url = "http://localhost:8080/"; // Cambiar la URL para que apunte a la ruta correcta
        Runtime runtime = Runtime.getRuntime();
        try {
            if (System.getProperty("os.name").toLowerCase().contains("win")) {
                runtime.exec("rundll32 url.dll,FileProtocolHandler " + url);
            } else if (System.getProperty("os.name").toLowerCase().contains("mac")) {
                runtime.exec("open " + url);
            } else if (System.getProperty("os.name").toLowerCase().contains("nix") ||
                    System.getProperty("os.name").toLowerCase().contains("nux")) {
                runtime.exec("xdg-open " + url);
            } else {
                logger.warning("Unsupported operating system. Cannot open browser.");
            }
        } catch (IOException e) {
            logger.severe("Failed to open browser: " + e.getMessage());
            e.printStackTrace();
        }
    }
}