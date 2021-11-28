/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GID.ModuloCliente.config;

import lombok.Getter;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Getter
public class ConfigurationSingletonClient {

    private static ConfigurationSingletonClient config;
    private String path_base;

    private ConfigurationSingletonClient() {

    }

    public static synchronized ConfigurationSingletonClient getInstance() {

        if (config == null) {
            try {
                Yaml yaml = new Yaml();
                Iterable<Object> it;

                it = yaml.loadAll(new FileInputStream("config/config.yaml"));

                Map<String, String> m = (Map) it.iterator().next();
                config = new ConfigurationSingletonClient();
                config.setPath_base(m.get("path_base"));
            } catch (FileNotFoundException ex) {
                Logger.getLogger(ConfigurationSingletonClient.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return config;
    }

    private void setPath_base(String path_base) {
        this.path_base = path_base;
    }
}
