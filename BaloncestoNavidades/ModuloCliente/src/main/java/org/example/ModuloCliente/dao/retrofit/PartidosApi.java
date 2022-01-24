package org.example.ModuloCliente.dao.retrofit;

import org.example.Common.modelo.Equipo;
import org.example.Common.modelo.Jornada;
import org.example.Common.modelo.Partido;
import org.example.ModuloCliente.dao.utils.Constantes;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

import java.util.List;

public interface PartidosApi {

    @GET(Constantes.PARTIDO_GET_ALL)
    Call<List<Partido>> getPartidos();

    @GET(Constantes.EQUIPO_GET_ALL)
    Call<List<Equipo>> getEquipos();

    @GET(Constantes.JORNADA_GET_ALL)
    Call<List<Jornada>> getJornadas();

    @POST(Constantes.ADD_PARTIDO)
    Call<Partido> addPartido(@Body Partido partido);

    @POST(Constantes.ADD_EQUIPO)
    Call<Equipo> addEquipo(@Body Equipo equipo);

    @POST(Constantes.ADD_JORNADA)
    Call<Jornada> addJornada(@Body Jornada jornada);
}
