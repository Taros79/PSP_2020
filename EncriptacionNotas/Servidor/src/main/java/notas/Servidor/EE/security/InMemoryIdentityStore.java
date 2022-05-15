package notas.Servidor.EE.security;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.security.enterprise.identitystore.IdentityStore;
import notas.Common.modelo.Usuario;
import notas.Servidor.servicios.ServiciosUsuario;
import notas.Servidor.utils.Constantes;

import java.util.Collections;

import static jakarta.security.enterprise.identitystore.CredentialValidationResult.INVALID_RESULT;

@Singleton
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

            Usuario usuario;
            if (serviciosUsuario.getUsuarioByCorreoCredentials(user.getCaller(), user.getPasswordAsString()).isLeft()) {
                return INVALID_RESULT;
            } else {
                usuario = serviciosUsuario.getUsuarioByNombre(user.getCaller(), user.getPasswordAsString());
            }

            switch (usuario.getIdTipo_Usuario()) {
                case 1:
                    return new CredentialValidationResult(usuario.getNombre(), Collections.singleton(Constantes.JEFE_DE_ESTUDIOS));
                case 2:
                    return new CredentialValidationResult(usuario.getNombre(), Collections.singleton(Constantes.PROFESOR));
                case 3:
                    return new CredentialValidationResult(usuario.getNombre(), Collections.singleton(Constantes.PADRE));
                default:
                    return INVALID_RESULT;
            }

        }
        return INVALID_RESULT;
    }

}
