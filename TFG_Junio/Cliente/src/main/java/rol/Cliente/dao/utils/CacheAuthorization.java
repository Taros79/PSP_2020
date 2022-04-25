package rol.Cliente.dao.utils;

import lombok.Data;

import javax.inject.Singleton;

@Data
@Singleton
public class CacheAuthorization {

    private String user;
    private String pass;
    private String token;

    public CacheAuthorization() {
    }

    public CacheAuthorization(String user, String pass) {
        this.user = user;
        this.pass = pass;
    }
}
