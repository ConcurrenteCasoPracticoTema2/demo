package com.example.demo.hilo;

import com.example.demo.Menu.ConsoleMenu;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.concurrent.ExecutorService;

@Component
public class ConsoleMenuLauncher {

    @Autowired
    private ConsoleMenu consoleMenu;

    @Autowired
    private ExecutorService singleThreadExecutor;

    @PostConstruct
    public void launchConsoleMenu() {
        singleThreadExecutor.submit(consoleMenu);
    }
}