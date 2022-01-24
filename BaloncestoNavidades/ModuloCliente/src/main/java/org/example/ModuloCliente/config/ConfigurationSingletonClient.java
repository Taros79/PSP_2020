package org.example.ModuloCliente.config;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;

@Getter
@Setter
@Log4j2
public class ConfigurationSingletonClient {
    private String pathbase;

    public ConfigurationSingletonClient(){
        try {
            Yaml yaml = new Yaml();
            Iterable<Object> it;
            it = yaml.loadAll(new FileInputStream(ConstantesConfig.CONFIG_YAML));

            Map<String, String> mp = (Map) it.iterator().next();
            this.pathbase = mp.get(ConstantesConfig.PATH_BASE);
        } catch (FileNotFoundException ex) {
            log.error(ex.getMessage());
        }
    }
    private void setPathBase(String pathBase) {
        this.pathbase = pathBase;
    }
}
