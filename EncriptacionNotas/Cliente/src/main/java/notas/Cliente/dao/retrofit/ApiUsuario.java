package notas.Cliente.dao.retrofit;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.*;
import notas.Common.constantes.ConstantesRest;
import notas.Common.modelo.Usuario;

import java.util.List;

public interface ApiUsuario {

    @GET(ConstantesRest.PATH_USUARIOS)
    Single<List<Usuario>> getAllUsuarios();

    @GET(ConstantesRest.PATH_LOGIN)
    Single<Usuario> hacerLogin(@Query("nombre") String nombre, @Query("pass") String pass);

    @GET(ConstantesRest.PATH_LOGIN + ConstantesRest.PATH_REGISTRO_LOGOUT)
    Single<String> hacerLogout();
}
