// src/main/java/com/example/demo/LimpiezaBase/CleanupListener.java
package com.example.demo.LimpiezaBase;

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
    private DatabaseCleanupService databaseCleanupService;

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        // Vaciar las bases de datos al detener la aplicación
        databaseCleanupService.clearDatabase();
        log.info("La base de datos se ha cerrado y se ha vaciado la tabla de usuario.");
    }
}
