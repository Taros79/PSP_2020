package notas.ServidorModule.config;


import jakarta.inject.Singleton;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.yaml.snakeyaml.Yaml;

import java.util.Map;

@Getter
@Log4j2
@Singleton
public class ConfigYaml {

    private String user;
    private String passw;
    private String path;
    private String driver;
    private String passKeyStore;

    public void cargar() {

        try {
            Yaml yaml = new Yaml();
            Iterable<Object> it = null;

            it = yaml
                    .loadAll(this.getClass().getClassLoader().getResourceAsStream(ConstantesConfig.CONFIG_PATH));

            Map<String, String> m = (Map) it.iterator().next();
            this.path = m.get(ConstantesConfig.PATH);
            this.passw = m.get(ConstantesConfig.PASSW);
            this.user = m.get(ConstantesConfig.USER);
            this.driver = m.get(ConstantesConfig.DRIVER);
            this.passKeyStore = m.get(ConstantesConfig.PASSKEYSTORE);

        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
    }


}
