package org.example.ModuloCliente.dao.retrofit;

import org.example.Common.EE.utils.ApiRespuesta;
import org.example.Common.modelo.Equipo;
import org.example.Common.modelo.Jornada;
import org.example.Common.modelo.Partido;
import org.example.ModuloCliente.dao.utils.Constantes;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface JornadasApi {

    @GET(Constantes.JORNADA_GET_ALL)
    Call<List<Jornada>> getJornadas();

    @POST(Constantes.JORNADA_GET_ALL)
    Call<Jornada> addJornada(@Body Jornada jornada);

    @DELETE(Constantes.JORNADA_GET_ALL)
    Call<ApiRespuesta> deleteJornada(@Query(Constantes.ID) String id);

    @PUT(Constantes.JORNADA_GET_ALL)
    Call<ApiRespuesta> updateJornada(@Body Jornada jornada);
}
