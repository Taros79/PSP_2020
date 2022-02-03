package quevedo.ServerBasket.utils;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import quevedo.ServerBasket.utils.constantes.ConstantesParametros;

import java.security.Key;

public class KeyProvider {

    @Produces
    @Singleton
    @Named(ConstantesParametros.JWT)
    public Key key()
    {
        return Keys.secretKeyFor(SignatureAlgorithm.HS512);
    }
}
