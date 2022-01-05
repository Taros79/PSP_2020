package org.example.ModuloCliente.dao.retrofit;

import org.example.Common.modelo.Partido;
import org.example.Common.modelo.UsuarioRegistro;
import org.example.ModuloCliente.dao.utils.Constantes;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

import java.util.List;

public interface PartidosApi {

    @GET(Constantes.PARTIDO_GET_ALL)
    Call<List<Partido>> getPartidos();

    @POST(Constantes.ADD_PARTIDO)
    Call<Partido> addPartido(@Body Partido p);
}
