package quevedo.ServerBasket.EE.security;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.security.enterprise.identitystore.IdentityStore;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;
import quevedo.ServerBasket.services.identificacion.LoginService;
import quevedo.ServerBasket.utils.constantes.ConstantesParametros;
import quevedo.common.modelo.User;

import java.util.Collections;
import java.util.Set;

import static jakarta.security.enterprise.identitystore.CredentialValidationResult.INVALID_RESULT;

@Singleton
public class InMemoryIdentityStore implements IdentityStore {


    private final LoginService loginService;
    private final Pbkdf2PasswordHash passwordHash;

    @Inject
    public InMemoryIdentityStore(LoginService loginService, Pbkdf2PasswordHash passwordHash) {
        this.loginService = loginService;
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
            UsernamePasswordCredential user = (UsernamePasswordCredential) credential;


            Either<String, User> resultObtenerUser = loginService.geUser(user.getCaller());

            if (resultObtenerUser.isRight()) {
                if (passwordHash.verify(user.getPasswordAsString().toCharArray(), resultObtenerUser.get().getPassword())) {
                    if (resultObtenerUser.get().getTipoUser() == 1) {
                        credentialValidationResult = new CredentialValidationResult(user.getCaller(), Set.of(ConstantesParametros.ADMIN_ROLE, ConstantesParametros.USER));
                    } else {
                        credentialValidationResult = new CredentialValidationResult(user.getCaller(), Collections.singleton(ConstantesParametros.USER));
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
