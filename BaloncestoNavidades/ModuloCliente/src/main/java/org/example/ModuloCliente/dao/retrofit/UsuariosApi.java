package org.example.ModuloCliente.dao.retrofit;

import org.example.Common.EE.utils.ApiRespuesta;
import org.example.Common.modelo.Usuario;
import org.example.Common.modelo.UsuarioLoginDTO;
import org.example.Common.modelo.UsuarioRegistro;
import org.example.ModuloCliente.dao.utils.Constantes;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface UsuariosApi {

    @GET(Constantes.USUARIO_GET_ALL)
    Call<List<Usuario>> getUsuarios();

    @POST(Constantes.ADD_USUARIO_REGISTRO)
    Call<UsuarioRegistro> addUsuarioRegistro(@Body UsuarioRegistro u);

    @GET(Constantes.GET_USUARIO_LOGIN)
    Call<UsuarioLoginDTO> getUsuarioLogin(@Query(Constantes.USERNAME) String username);

    @GET(Constantes.MANDAR_MAIL)
    Call<String> mandarMail(@Query(Constantes.CORREO) String correo, @Query(Constantes.USERNAME) String username);

    @DELETE(Constantes.DELETE_USUARIO)
    Call<ApiRespuesta> deletePersona(@Query(Constantes.ID) String u);

    @GET(Constantes.DO_LOGIN)
    Call<Void> login(@Query(Constantes.USERNAME) String username, @Query(Constantes.PASSWORD) String password);

    @PUT(Constantes.USUARIO_GET_ALL)
    Call<ApiRespuesta> updateUsuario(@Body Usuario usuario);
}
