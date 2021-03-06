/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.example.ModuloServidor.config;

import jakarta.inject.Singleton;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.example.ModuloServidor.utils.Constantes;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Map;

@Getter
@Log4j2
@Singleton
public class Configuration {

    private String ruta;
    private String user;
    private String password;
    private String driver;
    private String host;
    private String userCorreo;
    private String passwCorreo;

    void cargar(InputStream file) {

        try {
            Yaml yaml = new Yaml();
            Iterable<Object> it = null;

            it = yaml
                    .loadAll(file);

            Map<String, String> m = (Map) it.iterator().next();
            this.ruta = m.get(Constantes.RUTA);
            this.password = m.get(Constantes.PASSWORD2);
            this.user = m.get(Constantes.USER_min);
            this.driver = m.get(Constantes.DRIVER);
            this.host = m.get(Constantes.HOST);
            this.userCorreo = m.get(Constantes.USER_MAIL);
            this.passwCorreo = m.get(Constantes.PASSW_MAIL);

        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
    }
}


