/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.example.ModuloServidor.config;

import jakarta.inject.Singleton;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.yaml.snakeyaml.Yaml;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * @author dam2
 */
@Getter
@Log4j2
@Singleton
public class Configuration {

    private String user;
    private String password;
    private String ruta;
    private String driver;
    private int maxPoolCon;

    private static Configuration config;

    private Configuration() {

    }

    void cargar(InputStream file) {

        try {
            Yaml yaml = new Yaml();

            Iterable<Object> it = null;

            it = yaml
                    .loadAll(file);

            Map<String, String> m = (Map) it.iterator().next();
            this.ruta = m.get("ruta");
            this.password = m.get("password");
            this.user = m.get("user");
            this.driver = m.get("driver");


        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
    }

    public static Configuration getInstance() {
        if (config == null) {
            try {
                Yaml yaml = new Yaml();
                InputStream in = Files.newInputStream(Paths.get("ModuloServidor/propertiesFiles/config.yaml"));
                // Parse the YAML document in a stream and produce the corresponding Java object.
                config = (Configuration) yaml.loadAs(in, Configuration.class);

            } catch (FileNotFoundException ex) {
                Logger.getLogger(Configuration.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Configuration.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return config;
    }


    public String getUser() {
        return user;
    }

    public void setUser(String User) {
        this.user = User;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String Pass) {
        this.password = Pass;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public int getMaxPoolCon() {
        return maxPoolCon;
    }

    public void setMaxPoolCon(int maxPoolCon) {
        this.maxPoolCon = maxPoolCon;
    }
}
