package quevedo.ClientBasket.config;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.yaml.snakeyaml.Yaml;
import quevedo.ClientBasket.utils.ConstantesParametros;
import quevedo.ClientBasket.utils.ConstantesRutas;

import javax.inject.Singleton;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;


@Getter
@Singleton
@Log4j2
public class ConfigSingletonClient {


    private String pathBase;
    private String pathBaseRest;


    public ConfigSingletonClient() {
        try {
            Yaml yaml = new Yaml();
            Iterable<Object> it = null;
            it = yaml.loadAll(new FileInputStream(ConstantesRutas.CONFIG_CONFIG_YAML_PATH));
            Map<String, String> m = (Map) it.iterator().next();
            this.setpathBase(m.get(ConstantesParametros.PATH_BASE_REST));
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }

    }

    private void setpathBase(String pathBase) {
        this.pathBase = pathBase;
    }


}
