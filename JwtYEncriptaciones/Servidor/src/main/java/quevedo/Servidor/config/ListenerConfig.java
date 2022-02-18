package quevedo.Servidor.config;

import jakarta.inject.Inject;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener()
public class ListenerConfig implements ServletContextListener {

    ConfigYaml configYaml;

    @Inject
    public ListenerConfig(ConfigYaml configYaml) {
        this.configYaml = configYaml;
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        configYaml.cargar();
    }

}
