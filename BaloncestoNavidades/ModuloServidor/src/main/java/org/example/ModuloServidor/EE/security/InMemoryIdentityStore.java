package org.example.ModuloServidor.EE.security;

import io.vavr.control.Either;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.security.enterprise.identitystore.IdentityStore;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;
import org.example.Common.modelo.User;
import org.example.ModuloServidor.servicios.ServicioLogin;

import java.util.Collections;
import java.util.Set;

import static jakarta.security.enterprise.identitystore.CredentialValidationResult.INVALID_RESULT;

@ApplicationScoped
public class InMemoryIdentityStore implements IdentityStore {

    private final ServicioLogin servicioLogin;
    private final Pbkdf2PasswordHash passwordHash;

    @Inject
    public InMemoryIdentityStore(ServicioLogin servicioLogin, Pbkdf2PasswordHash passwordHash) {
        this.servicioLogin = servicioLogin;
        this.passwordHash = passwordHash;
    }

    @Override
    public int priority() {
        return 10;
    }

    @Override
    public CredentialValidationResult validate(Credential credential) {

        CredentialValidationResult credentialValidationResult = null;

        if (credential instanceof UsernamePasswordCredential) {
            UsernamePasswordCredential userCredential = (UsernamePasswordCredential) credential;


            Either<String, User> userDatabase = servicioLogin.geUser(userCredential.getCaller());

            if (userDatabase.isRight()) {
                if (passwordHash.verify(userCredential.getPasswordAsString().toCharArray(), userDatabase.get().getHashedPassword())) {
                    if (userDatabase.get().getTipoUsuario() == 1) {
                        credentialValidationResult = new CredentialValidationResult(userCredential.getCaller(), Set.of(ConstantesSecurity.ADMIN, ConstantesSecurity.USER));
                    } else {
                        credentialValidationResult = new CredentialValidationResult(userCredential.getCaller(), Collections.singleton(ConstantesSecurity.USER));
                    }

                } else {
//                Tratar contra incorrecta
                    credentialValidationResult = INVALID_RESULT;

                }
            } else {
//            Tratar Either para cuando no exista e user o de error la querie
                credentialValidationResult = INVALID_RESULT;

            }


        }
        return credentialValidationResult;
    }
}
