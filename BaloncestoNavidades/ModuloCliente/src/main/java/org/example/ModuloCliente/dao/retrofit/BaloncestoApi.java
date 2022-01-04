package org.example.ModuloCliente.dao.retrofit;

import org.example.Common.EE.utils.ApiRespuesta;
import org.example.Common.modelo.Usuario;
import org.example.Common.modelo.UsuarioLoginDTO;
import org.example.Common.modelo.UsuarioRegistro;
import org.example.ModuloCliente.dao.utils.Constantes;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface BaloncestoApi {

    @GET(Constantes.USUARIO_GET_ALL)
    Call<List<Usuario>> getUsuarios();

    @POST(Constantes.ADD_USUARIO_REGISTRO)
    Call<UsuarioRegistro> addUsuarioRegistro(@Body UsuarioRegistro u);

    @GET(Constantes.GET_USUARIO_LOGIN)
    Call<UsuarioLoginDTO> getUsuarioLogin(@Query("username") String username);

    @GET(Constantes.MANDAR_MAIL)
    Call<String> mandarMail(@Query("correo") String correo, @Query("username") String username);

    /*@GET(Constantes.USUARIO_GET_ALL)
    Call<List<Usuario>> getUsuarios();

    @GET(Constantes.GET_PERSONA_ID)
    Call<Usuario> getRecursosUnaPersona(@Path("id") String id);

    @POST(Constantes.ADD_DELETE_UPDATE_PERSONA)
    Call<Usuario> addPersona(@Body Usuario p);

    @DELETE(Constantes.ADD_DELETE_UPDATE_PERSONA)
    Call<ApiRespuesta> deletePersona(@Query("id") String id);*/

}
