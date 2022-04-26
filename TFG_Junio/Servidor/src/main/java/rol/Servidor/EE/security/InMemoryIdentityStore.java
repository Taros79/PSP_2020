package rol.Servidor.EE.security;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.security.enterprise.identitystore.IdentityStore;
import rol.Common.modelo.Usuario;
import rol.Servidor.servicios.ServiciosUsuario;
import rol.Servidor.utils.Constantes;

import java.util.Collections;

import static jakarta.security.enterprise.identitystore.CredentialValidationResult.INVALID_RESULT;

@ApplicationScoped
public class InMemoryIdentityStore implements IdentityStore {

    @Inject
    private ServiciosUsuario serviciosUsuario;

    @Override
    public int priority() {
        return 10;
    }

    @Override
    public CredentialValidationResult validate(Credential credential) {

        if (credential instanceof UsernamePasswordCredential) {
            UsernamePasswordCredential user = UsernamePasswordCredential
                    .class.cast(credential);

            Usuario usuario = serviciosUsuario.getUsuarioByCorreo(user.getCaller(), user.getPasswordAsString());


            switch (usuario.getTipo_Usuario()) {
                case 1:
                    return new CredentialValidationResult(usuario.getCorreo(), Collections.singleton(Constantes.MASTER));
                case 2:
                    return new CredentialValidationResult(usuario.getCorreo(), Collections.singleton(Constantes.JUGADOR));
                case 3:
                    return new CredentialValidationResult(usuario.getCorreo(), Collections.singleton(Constantes.ADMIN));
                default:
                    return INVALID_RESULT;
            }

        }
        return INVALID_RESULT;
    }

}
