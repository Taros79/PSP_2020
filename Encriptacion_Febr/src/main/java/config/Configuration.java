package config;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.yaml.snakeyaml.Yaml;

import javax.inject.Singleton;
import java.util.Map;


@Getter
@Singleton
@Log4j2
public class Configuration {

    private String path;
    private String user;
    private String passw;
    private String driver;

    void cargar() {

        try {
            Yaml yaml = new Yaml();
            Iterable<Object> it = null;

            it = yaml
                    .loadAll(this.getClass().getClassLoader().getResourceAsStream(ConstantesParametros.RUTA_CONFIG));

            Map<String, String> m = (Map) it.iterator().next();
            this.path = m.get(ConstantesParametros.PATH);
            this.user = m.get(ConstantesParametros.USER);
            this.passw = m.get(ConstantesParametros.PASSW);
            this.driver = m.get(ConstantesParametros.DRIVER);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
    }
}
