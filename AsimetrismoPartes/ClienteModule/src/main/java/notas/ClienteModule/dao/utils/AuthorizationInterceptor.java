package notas.ClienteModule.dao.utils;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class AuthorizationInterceptor implements Interceptor {


    private CacheAuthorization ca;

    public AuthorizationInterceptor(CacheAuthorization ca) {
        this.ca = ca;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        Request request;

        if (ca.getContraseña() != null || ca.getNombre() != null) {
            if (ca.getToken() == null) {
                request = original.newBuilder()
                        .header(ConstantesParametros.AUTHORIZATION_HEADER, Credentials.basic(ca.getNombre(), ca.getContraseña())).build();
            } else {

                request = original.newBuilder()
                        .header(ConstantesParametros.AUTHORIZATION_HEADER,
                                ConstantesParametros.BEARER_ESPACIO + ca.getToken()).build();
            }

        } else {
            // Manda llamada sin cabezera
            request = original.newBuilder().build();
        }

        Response response = chain.proceed(request);

        if (response.header(ConstantesParametros.AUTHORIZATION_HEADER) != null)
            ca.setToken(response.header(ConstantesParametros.AUTHORIZATION_HEADER));

        if (!response.isSuccessful()) {
            //Mirar Header del error de token y solo si es el de expirado reintentar
            if (response.header(ConstantesParametros.EXPIRES_HEADER) != null) {
                response.close();
                ca.setToken(null);
                for (int i = 0; i < 5; i++) {
                    if (ca.getToken() == null) {
                        request = original.newBuilder()
                                .header(ConstantesParametros.AUTHORIZATION_HEADER,
                                        Credentials.basic(ca.getNombre(), ca.getContraseña())).build();
                        response = chain.proceed(request);
                        if (response.header(ConstantesParametros.AUTHORIZATION_HEADER) != null)
                            ca.setToken(response.header(ConstantesParametros.AUTHORIZATION_HEADER));
                    }
                }
            }
        }

        return response;
    }
}
