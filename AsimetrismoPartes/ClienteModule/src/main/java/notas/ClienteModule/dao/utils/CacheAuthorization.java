package notas.ClienteModule.dao.utils;

import lombok.Data;

import javax.inject.Singleton;

@Data
@Singleton
public class CacheAuthorization {

    private String nombre;
    private String contraseña;
    private String token;

    public CacheAuthorization() {
    }

    public CacheAuthorization(String nombre, String contraseña) {
        this.nombre = nombre;
        this.contraseña = contraseña;
    }
}
