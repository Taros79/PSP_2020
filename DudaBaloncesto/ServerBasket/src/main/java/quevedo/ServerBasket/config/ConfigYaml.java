package quevedo.ServerBasket.config;


import jakarta.inject.Singleton;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.yaml.snakeyaml.Yaml;
import quevedo.ServerBasket.utils.constantes.ConstanteRutas;
import quevedo.ServerBasket.utils.constantes.ConstantesParametros;

import java.io.InputStream;
import java.util.Map;

@Getter
@Log4j2
@Singleton
public class ConfigYaml {

    private String user;
    private String passw;
    private String path;
    private String driver;
    private String host;
    private String userCorreo;
    private String passwCorreo;

    void cargar() {

        try {
            Yaml yaml = new Yaml();
            Iterable<Object> it = null;

            it = yaml
                    .loadAll(this.getClass().getClassLoader().getResourceAsStream(ConstanteRutas.CONFIG_PATH));

            Map<String, String> m = (Map) it.iterator().next();
            this.path = m.get(ConstantesParametros.PATH);
            this.passw = m.get(ConstantesParametros.PASSW);
            this.user = m.get(ConstantesParametros.USER);
            this.driver = m.get(ConstantesParametros.DRIVER);
            this.host = m.get(ConstantesParametros.HOST);
            this.userCorreo = m.get(ConstantesParametros.USER_MAIL);
            this.passwCorreo = m.get(ConstantesParametros.PASSW_MAIL);


        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
    }


}
