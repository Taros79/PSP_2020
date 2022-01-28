package org.example.ModuloServidor.EE.security;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.security.enterprise.identitystore.IdentityStore;
import org.example.ModuloServidor.servicios.ServiciosUsuarios;

import java.util.Collections;

import static jakarta.security.enterprise.identitystore.CredentialValidationResult.INVALID_RESULT;

@ApplicationScoped
public class InMemoryIdentityStore implements IdentityStore {

    @Inject
    private ServiciosUsuarios su;

    @Override
    public int priority() {
        return 10;
    }

    @Override
    public CredentialValidationResult validate(Credential credential) {

        if (credential instanceof UsernamePasswordCredential) {
            UsernamePasswordCredential user = UsernamePasswordCredential
                    .class.cast(credential);

            if (su.login(user.getCaller(), user.getPasswordAsString())) {
                var usuario = su.getUsuario(user.getCaller());
                if (usuario.isRight()) {

                    switch (usuario.get().getTipoUsuario()) {
                        case 1:
                            return new CredentialValidationResult(user.getCaller(), Collections.singleton("ADMIN"));
                        case 2:
                            return new CredentialValidationResult(user.getCaller(), Collections.singleton("user"));
                        default:
                            return INVALID_RESULT;
                    }
                }
            }
        }
        return INVALID_RESULT;
    }

}
