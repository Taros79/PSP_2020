package rol.Cliente.dao.utils;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class AuthorizationInterceptor implements Interceptor {


    private CacheAuthorization ca ;

    public AuthorizationInterceptor(CacheAuthorization ca) {
        this.ca = ca;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        Request request ;

        if (ca.getToken() == null) {
            request = original.newBuilder()
                    .header("Authorization", Credentials.basic(ca.getUser(), ca.getPass())).build();
        } else {
            //Manda llamada sin cabezera
            request = original.newBuilder().build();

        }

        Response response = chain.proceed(request);

        if (request.header("Authorization") != null)
            ca.setToken(request.header("Authorization"));

        return response;
    }
}
