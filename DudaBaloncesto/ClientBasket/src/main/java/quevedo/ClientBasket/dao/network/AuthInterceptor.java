package quevedo.ClientBasket.dao.network;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import quevedo.ClientBasket.dao.modelo.UserCached;
import quevedo.ClientBasket.utils.ConstantesParametros;
import quevedo.common.utils.StringsCommon;

import javax.inject.Inject;
import java.io.IOException;

public class AuthInterceptor implements Interceptor {


    private final UserCached userCached;

    @Inject
    public AuthInterceptor(UserCached userCached) {
        this.userCached = userCached;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        Request request;


        if (userCached.getPass() != null || userCached.getUser() != null) {
            if (userCached.getJwt() == null) {
                request = original.newBuilder()
                        .header(StringsCommon.AUTHORIZATION_HEADER,
                                Credentials.basic(userCached.getUser(), userCached.getPass())).build();
            } else {

                request = original.newBuilder()
                        .header(StringsCommon.AUTHORIZATION_HEADER,
                                ConstantesParametros.BEARER_ESPACIO + userCached.getJwt()).build();

            }
        } else {
//            Manda llamada sin cabezera
            request = original.newBuilder().build();
        }


        Response response = chain.proceed(request);

        if (response.header(StringsCommon.AUTHORIZATION_HEADER) != null)
            userCached.setJwt(response.header(StringsCommon.AUTHORIZATION_HEADER));
        if (!response.isSuccessful()) {

            //Mirar Header del error de token y solo si es el de expirado reintentar
            if (response.header(ConstantesParametros.EXPIRES_HEADER) != null) {
                response.close();
                userCached.setJwt(null);
                for (int i = 0; i < 5; i++) {
                    if (userCached.getJwt() == null) {
                        request = original.newBuilder()
                                .header(StringsCommon.AUTHORIZATION_HEADER,
                                        Credentials.basic(userCached.getUser(), userCached.getPass())).build();
                        response = chain.proceed(request);
                        if (response.header(StringsCommon.AUTHORIZATION_HEADER) != null)
                            userCached.setJwt(response.header(StringsCommon.AUTHORIZATION_HEADER));
                    }
                }
            }

        }


        return response;
    }
}
