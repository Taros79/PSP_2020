package org.example.ModuloServidor.EE.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
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


@ApplicationScoped
@Log4j2
public class JWTAuth implements HttpAuthenticationMechanism {

    private InMemoryIdentityStore identity;
    private final Key key;


    @Inject
    public JWTAuth(InMemoryIdentityStore identity, @Named("JWT") Key key) {
        this.identity = identity;
        this.key = key;
    }

    @Override
    public AuthenticationStatus validateRequest(HttpServletRequest httpServletRequest,
                                                HttpServletResponse httpServletResponse
            , HttpMessageContext httpMessageContext) throws AuthenticationException {
        CredentialValidationResult c = CredentialValidationResult.INVALID_RESULT;


        String header = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
        if (header != null) {
            String[] valores = header.split(" ");

            if (valores[0].equalsIgnoreCase("Basic")) {
                String userPass = new String(Base64.getUrlDecoder().decode(valores[1]));
                String[] userPassSeparado = userPass.split(":");
                c = identity.validate(new UsernamePasswordCredential(userPassSeparado[0], userPassSeparado[1]));

                if (c.getStatus() == CredentialValidationResult.Status.VALID) {
                    httpServletRequest.getSession().setAttribute("LOGIN", c);

                    // clave aleatoria
                    Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

                    //Clave personal
                    MessageDigest digest = null;
                    try {
                        digest = MessageDigest.getInstance("SHA-512");
                    } catch (NoSuchAlgorithmException e) {
                        log.error(e.getMessage(), e);
                    }
                    digest.update("clave".getBytes(StandardCharsets.UTF_8));
                    final SecretKeySpec key2 = new SecretKeySpec(
                            digest.digest(), 0, 64, "AES");
                    SecretKey keyConfig = Keys.hmacShaKeyFor(key2.getEncoded());


                    String jws = Jwts.builder()
                            .setSubject("Joe")
                            .setIssuer("yo")

                            .setExpiration(Date
                                    .from(LocalDateTime.now().plusSeconds(1200).atZone(ZoneId.systemDefault())
                                            .toInstant()))
                            .claim("user", c.getCallerPrincipal().getName())
                            .claim("group", c.getCallerGroups())
                            .signWith(key).compact();

                    Response.ok(Base64.getUrlEncoder().encodeToString(key.getEncoded()))
                            .header(HttpHeaders.AUTHORIZATION, jws).build();

                    //añadir al response
                    httpServletResponse.setContentType("application/json");
                    httpServletResponse.setCharacterEncoding(Base64.getUrlEncoder().encodeToString(key.getEncoded()));
                    httpServletResponse.setHeader(HttpHeaders.AUTHORIZATION, jws);
                }
            } else if (valores[0].equalsIgnoreCase("Bearer")) {
                System.out.println("tonto");
            }

        } else {
            if (httpServletRequest.getSession().getAttribute("LOGIN") != null)
                c = (CredentialValidationResult) httpServletRequest.getSession().getAttribute("LOGIN");
        }

        if (c.getStatus().equals(CredentialValidationResult.Status.INVALID)) {
            return httpMessageContext.doNothing();
        }
        return httpMessageContext.notifyContainerAboutLogin(c);
    }
}
