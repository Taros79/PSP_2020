package org.example.ModuloServidor.EE.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.security.enterprise.AuthenticationException;
import jakarta.security.enterprise.AuthenticationStatus;
import jakarta.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import jakarta.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import lombok.extern.log4j.Log4j2;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;
import java.util.HashSet;
import java.util.List;


@ApplicationScoped
@Log4j2
public class JWTAuth implements HttpAuthenticationMechanism {

    private InMemoryIdentityStore identity;
    private final Key key;


    @Inject
    public JWTAuth(InMemoryIdentityStore identity, @Named(ConstantesSecurity.JWT) Key key) {
        this.identity = identity;
        this.key = key;
    }

    @Override
    public AuthenticationStatus validateRequest(HttpServletRequest httpServletRequest,
                                                HttpServletResponse httpServletResponse
            , HttpMessageContext httpMessageContext) throws AuthenticationException {
        CredentialValidationResult credentialValidationResult = CredentialValidationResult.INVALID_RESULT;


        String header = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
        if (header != null) {
            String[] valores = header.split(" ");

            if (valores[0].equalsIgnoreCase(ConstantesSecurity.BASIC)) {
                String userPass = new String(Base64.getUrlDecoder().decode(valores[1]));
                String[] userPassSeparado = userPass.split(":");

                credentialValidationResult = identity.validate(
                        new UsernamePasswordCredential(userPassSeparado[0], userPassSeparado[1]));

                if (credentialValidationResult.getStatus() == CredentialValidationResult.Status.VALID) {
                    httpServletRequest.getSession().setAttribute(ConstantesSecurity.LOGIN, credentialValidationResult);

                    String token = Jwts.builder()
                            .setExpiration(Date
                                    .from(LocalDateTime.now().plusMinutes(1).atZone(ZoneId.systemDefault())
                                            .toInstant()))
                            .claim(ConstantesSecurity.USER_min, credentialValidationResult.getCallerPrincipal().getName())
                            .claim(ConstantesSecurity.GROUP, credentialValidationResult.getCallerGroups())
                            .signWith(key).compact();

                    httpServletResponse.setHeader(HttpHeaders.AUTHORIZATION, token);
                }
            } else if (valores[0].equalsIgnoreCase(ConstantesSecurity.BEARER)) {

               try{
                   Jws<Claims> jws = Jwts.parserBuilder()
                        .setSigningKey(key)
                        .build()
                        .parseClaimsJws(valores[1]);

                List<String> grupos = (List<String>) jws.getBody().get(ConstantesSecurity.GROUP);

                credentialValidationResult = new CredentialValidationResult(
                        jws.getBody().get(ConstantesSecurity.USER_min).toString(),
                        new HashSet<>(grupos));
            } catch (ExpiredJwtException expired) {
                log.error(expired.getMessage(), expired);
                httpServletResponse.setHeader(HttpHeaders.EXPIRES, ConstantesSecurity.EL_TIEMPO_DEL_TOKEN_HA_EXPIRADO);
            } catch (MalformedJwtException modificado) {
                log.error(modificado.getMessage(), modificado);
                httpServletResponse.setHeader(ConstantesSecurity.ERROR, ConstantesSecurity.TOKEN_ERROR);
            } catch (SignatureException exception) {
                log.error(exception.getMessage(), exception);
                httpServletResponse.setHeader(ConstantesSecurity.MODIFICADO, ConstantesSecurity.EL_TIEMPO_DEL_TOKEN_HA_SIDO_MODIFIFCADO);

            }

            }

        } else {
            if (httpServletRequest.getSession().getAttribute(ConstantesSecurity.LOGIN) != null)
                //credentialValidationResult = (CredentialValidationResult) httpServletRequest.getSession().getAttribute(ConstantesSecurity.LOGIN);
                System.out.println("hola");
        }

        if (credentialValidationResult.getStatus().equals(CredentialValidationResult.Status.INVALID)) {
            return httpMessageContext.doNothing();
        }
        return httpMessageContext.notifyContainerAboutLogin(credentialValidationResult);
    }
}