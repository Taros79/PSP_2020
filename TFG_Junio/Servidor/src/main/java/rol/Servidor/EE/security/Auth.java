package rol.Servidor.EE.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.security.enterprise.AuthenticationStatus;
import jakarta.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import jakarta.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.core.HttpHeaders;
import lombok.extern.log4j.Log4j2;
import rol.Servidor.utils.Constantes;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;
import java.util.HashSet;
import java.util.List;


@Singleton
@Log4j2
public class Auth implements HttpAuthenticationMechanism {

    private final InMemoryIdentityStore inMemoryIdentityStore;
    private final Key key;

    @Inject
    public Auth(InMemoryIdentityStore inMemoryIdentityStore, @Named("JWT_KEY") Key key) {
        this.inMemoryIdentityStore = inMemoryIdentityStore;
        this.key = key;
    }


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
                c = inMemoryIdentityStore.validate(new UsernamePasswordCredential(userPassSeparado[0]
                        , userPassSeparado[1]));

                if (c.getStatus() == CredentialValidationResult.Status.VALID) {

                    //Crear token
                    String token = generateToken(c);
                    httpServletResponse.setHeader(HttpHeaders.AUTHORIZATION, token);
                }
            } else if (valores[0].equalsIgnoreCase("Bearer")) {

                try {
                    Jws<Claims> jws = Jwts.parserBuilder()
                            .setSigningKey(key)
                            .build()
                            .parseClaimsJws(valores[1]);

                    List<String> grupos = (List<String>) jws.getBody().get(Constantes.GROUP);


                    c = new CredentialValidationResult(
                            jws.getBody().get(Constantes.CORREO).toString(),
                            new HashSet<>(grupos));

                } catch (ExpiredJwtException expired) {
                    log.error(expired.getMessage(), expired);
                    httpServletResponse.setHeader(HttpHeaders.EXPIRES, Constantes.EL_TIEMPO_DEL_TOKEN_HA_EXPIRADO);
                } catch (MalformedJwtException modificado) {
                    log.error(modificado.getMessage(), modificado);
                    httpServletResponse.setHeader(Constantes.ERROR, Constantes.TOKEN_ERROR);
                } catch (SignatureException exception) {
                    log.error(exception.getMessage(), exception);
                    httpServletResponse.setHeader(Constantes.MODIFICADO, Constantes.EL_TIEMPO_DEL_TOKEN_HA_SIDO_MODIFIFCADO);

                }
            }

        } else {
            httpServletResponse.setHeader(Constantes.ERROR, Constantes.NO_HA_SIDO_ENVIADO_NINGUN_TOKEN);
        }

        if (c.getStatus().equals(CredentialValidationResult.Status.INVALID)) {

            return httpMessageContext.doNothing();
        }
        return httpMessageContext.notifyContainerAboutLogin(c);
    }

    private String generateToken(CredentialValidationResult credentialValidationResult) {

        return Jwts.builder()
                .setExpiration(Date.from(
                        LocalDateTime.now().plusMinutes(3).atZone(ZoneId.systemDefault())
                                .toInstant())
                ).claim(Constantes.CORREO, credentialValidationResult.getCallerPrincipal().getName())
                .claim(Constantes.GROUP, credentialValidationResult.getCallerGroups())
                .signWith(key).compact();
    }
}
