package rol.Cliente.dao.utils;

import lombok.Data;

import javax.inject.Singleton;

@Data
@Singleton
public class CacheAuthorization {

    private String correo;
    private String pass;
    private String token;

    public CacheAuthorization() {
    }

    public CacheAuthorization(String correo, String pass) {
        this.correo = correo;
        this.pass = pass;
    }
}
