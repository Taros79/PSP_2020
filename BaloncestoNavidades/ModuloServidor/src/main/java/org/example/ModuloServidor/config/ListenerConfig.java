package org.example.ModuloServidor.config;

import jakarta.inject.Inject;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener()
public class ListenerConfig implements ServletContextListener {
    Configuration config;

    @Inject
    public ListenerConfig(Configuration config) {
        this.config = config;
    }

    public void contextInitialized(ServletContextEvent sce) {
        config.cargar(sce.getServletContext().getResourceAsStream("/WEB-INF/config/config.yaml"));
    }

}
