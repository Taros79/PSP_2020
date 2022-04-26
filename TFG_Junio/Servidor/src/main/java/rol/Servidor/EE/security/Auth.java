package rol.Servidor.EE.security;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.security.enterprise.AuthenticationStatus;
import jakarta.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import jakarta.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.core.HttpHeaders;
import rol.Common.constantes.ConstantesRest;

import java.util.Base64;


@ApplicationScoped
public class Auth implements HttpAuthenticationMechanism {

    @Inject
    private InMemoryIdentityStore identity;


    @Override
    public AuthenticationStatus validateRequest(HttpServletRequest httpServletRequest,
                                                HttpServletResponse httpServletResponse
            , HttpMessageContext httpMessageContext) {
        CredentialValidationResult c = CredentialValidationResult.INVALID_RESULT;

        String header = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
        if (header != null) {
            String[] valores = header.split(" ");

            if (valores[0].equalsIgnoreCase("Basic")) {
                String userPass = new String(Base64.getUrlDecoder().decode(valores[1]));
                String[] userPassSeparado = userPass.split(":");
                c = identity.validate(new UsernamePasswordCredential(userPassSeparado[0]
                        , userPassSeparado[1]));

                if (c.getStatus() == CredentialValidationResult.Status.VALID) {
                    httpServletRequest.getSession().setAttribute(ConstantesRest.USUARIO_LOGIN, c);
                    //generar token


                    //añadir al response


                }
            }
            //  else del bearer, bearer, bearer,beaer Ijust conat to wiat to vb warer

            //vlidarlo

        }
        else
        {
            if (httpServletRequest.getSession().getAttribute(ConstantesRest.USUARIO_LOGIN)!=null)
                c = (CredentialValidationResult)httpServletRequest.getSession().getAttribute(ConstantesRest.USUARIO_LOGIN);
        }

        if (c.getStatus().equals(CredentialValidationResult.Status.INVALID))
        {

            return httpMessageContext.doNothing();
        }
        return httpMessageContext.notifyContainerAboutLogin(c);
    }
}
