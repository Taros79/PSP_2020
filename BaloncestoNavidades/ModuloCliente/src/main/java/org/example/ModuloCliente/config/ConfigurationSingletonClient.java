package org.example.ModuloCliente.config;

import lombok.Getter;
import lombok.Setter;
import org.yaml.snakeyaml.Yaml;

import javax.inject.Singleton;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;

@Getter
@Setter
@Singleton
public class ConfigurationSingletonClient {
    private final String pathbase;

    public ConfigurationSingletonClient() throws FileNotFoundException {
        Yaml yaml = new Yaml();
        Iterable<Object> it;
        it = yaml.loadAll(new FileInputStream(ConstantesConfig.CONFIG_YAML));

        Map<String, String> mp = (Map) it.iterator().next();
        this.pathbase = mp.get(ConstantesConfig.PATH_BASE);

    }
}
