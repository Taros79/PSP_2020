package org.example.ModuloCliente.dao.retrofit;

import org.example.Common.EE.utils.ApiRespuesta;
import org.example.Common.modelo.Partido;
import org.example.ModuloCliente.dao.utils.Constantes;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface PartidosApi {

    @GET(Constantes.PARTIDO_GET_ALL)
    Call<List<Partido>> getPartidos();

    @POST(Constantes.PARTIDO_GET_ALL)
    Call<Partido> addPartido(@Body Partido partido);

    @DELETE(Constantes.PARTIDO_GET_ALL)
    Call<ApiRespuesta> deletePartido(@Query(Constantes.ID) String ip);

    @PUT(Constantes.PARTIDO_GET_ALL)
    Call<ApiRespuesta> updatePartido(@Body Partido partido);
}
