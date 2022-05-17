/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package notas.Cliente.config;

import lombok.Getter;
import notas.Cliente.dao.utils.ConstantesParametros;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;

@Getter
public class ConfigurationSingleton_Client {

    private String pathBase;


    public ConfigurationSingleton_Client() {
        try {
            Yaml yaml = new Yaml();
            Iterable<Object> it = null;
            it = yaml.loadAll(new FileInputStream("config/config.yaml"));
            Map<String, String> m = (Map) it.iterator().next();
            this.setpathBase(m.get(ConstantesParametros.PATH_BASE));
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }

    }

    private void setpathBase(String pathBase) {
        this.pathBase = pathBase;
    }
}
