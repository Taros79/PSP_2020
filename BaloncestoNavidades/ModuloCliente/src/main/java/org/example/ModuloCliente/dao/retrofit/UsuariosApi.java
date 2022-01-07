package org.example.ModuloCliente.dao.retrofit;

import org.example.Common.EE.utils.ApiRespuesta;
import org.example.Common.modelo.Usuario;
import org.example.Common.modelo.UsuarioLoginDTO;
import org.example.Common.modelo.UsuarioRegistro;
import org.example.ModuloCliente.dao.utils.Constantes;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.*;

import java.util.List;

public interface UsuariosApi {

    @GET(Constantes.USUARIO_GET_ALL)
    Call<List<Usuario>> getUsuarios();

    @POST(Constantes.ADD_USUARIO_REGISTRO)
    Call<UsuarioRegistro> addUsuarioRegistro(@Body UsuarioRegistro u);

    @GET(Constantes.GET_USUARIO_LOGIN)
    Call<UsuarioLoginDTO> getUsuarioLogin(@Query("username") String username);

    @GET(Constantes.MANDAR_MAIL)
    Call<String> mandarMail(@Query("correo") String correo, @Query("username") String username);

    @DELETE("api/usuarios")
    Call<ApiRespuesta> deletePersona(@Query("id") String u);

    @GET("doLogin")
    Call<Void> login(@Query("username") String username, @Query("password") String password);


}
