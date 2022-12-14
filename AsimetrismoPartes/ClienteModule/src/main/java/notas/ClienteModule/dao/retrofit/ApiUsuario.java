package notas.ClienteModule.dao.retrofit;

import io.reactivex.rxjava3.core.Single;
import notas.CommonModule.constantes.ConstantesRest;
import notas.CommonModule.modelo.Usuario;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

import java.util.List;

public interface ApiUsuario {

    @GET(ConstantesRest.PATH_USUARIOS)
    Single<List<Usuario>> getAllUsuarios();

    @GET(ConstantesRest.PATH_LOGIN)
    Single<Usuario> hacerLogin(@Query("nombre") String nombre, @Query("pass") String pass);

    @GET(ConstantesRest.PATH_LOGIN + ConstantesRest.PATH_REGISTRO_LOGOUT)
    Single<String> hacerLogout();

    @POST(ConstantesRest.PATH_USUARIOS)
    Single<String> addUsuario(@Body Usuario u);
}
