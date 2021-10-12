/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config;

import lombok.Getter;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Getter
public class ConfigurationSingleton_Client {

    private static ConfigurationSingleton_Client config;

    private ConfigurationSingleton_Client() {

    }

    public static synchronized ConfigurationSingleton_Client getInstance() {

        if (config == null) {
            try {
                Yaml yaml = new Yaml();
                Iterable<Object> it = null;

                it = yaml.loadAll(new FileInputStream("config/config.yaml"));

                Map<String, String> m = (Map) it.iterator().next();
                config = new ConfigurationSingleton_Client();
                config.setPath_base(m.get("path_base"));
            } catch (FileNotFoundException ex) {
                Logger.getLogger(ConfigurationSingleton_Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return config;
    }

    private void setPath_base(String path_base) {
        this.path_base = path_base;
    }

    private String path_base;
}
