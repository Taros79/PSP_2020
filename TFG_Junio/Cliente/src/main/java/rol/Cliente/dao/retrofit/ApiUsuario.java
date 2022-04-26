package rol.Cliente.dao.retrofit;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.*;
import rol.Common.constantes.ConstantesRest;
import rol.Common.modelo.Usuario;

import java.util.List;

public interface ApiUsuario {

    @GET(ConstantesRest.PATH_USUARIOS)
    Single<List<Usuario>> getAllUsuarios();

    @POST(ConstantesRest.PATH_USUARIOS)
    Single<String> addUsuario(@Body Usuario u);

    @DELETE(ConstantesRest.PATH_USUARIOS)
    Single<String> delUsuario(@Query(ConstantesRest.ID) int id);

    @PUT(ConstantesRest.PATH_USUARIOS)
    Single<String> updateUsuario(@Body Usuario u);

    @GET(ConstantesRest.PATH_REGISTRO)
    Single<Usuario> hacerLoging(@Header(ConstantesRest.AUTHORIZATION) String Authorization);

    @GET(ConstantesRest.PATH_REGISTRO + ConstantesRest.PATH_REGISTRO_LOGOUT)
    Single<String> hacerLogout();
}
