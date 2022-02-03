package quevedo.ClientBasket.dao.retrofit;

import io.reactivex.rxjava3.core.Single;
import quevedo.ClientBasket.utils.ConstantesRutas;
import quevedo.common.modelo.Mensaje;
import quevedo.common.modelo.User;
import quevedo.common.modelo.UserLoged;
import quevedo.common.utils.PathRest;
import quevedo.common.utils.StringsCommon;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface IdentificacionApi {

    @POST(PathRest.REGISTRO_PATH)
    Single<Mensaje> registrarse(@Body User user);

    @GET(PathRest.LOGIN_REST)
    Single<UserLoged> login(@Query(StringsCommon.CORREO) String correo , @Query(StringsCommon.PASSW) String passw);


    @GET(PathRest.CAMBIO_PASSW)
    Single<Void> cambiarPassw(@Query(StringsCommon.CORREO) String correo);

    @POST(PathRest.REGISTRO_PATH + PathRest.ADMIN_REGISTRO_PATH)
    Single<Mensaje> registerAdmin(@Body User user);


}
