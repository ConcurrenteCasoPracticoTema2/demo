package com.example.demo;

import com.example.demo.Usuario.UsuarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

@Component
public class CleanupListener implements ApplicationListener<ContextClosedEvent> {

    private static final Logger log = LoggerFactory.getLogger(CleanupListener.class);

    @Autowired
    private UsuarioService usuarioService;

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        // Vaciar las bases de datos al detener la aplicación
        usuarioService.clearDatabase();

        log.info("La base de datos se ha cerrado y se ha vaciado la tabla de usuario.");
    }
}