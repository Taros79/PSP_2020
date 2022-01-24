package org.example.ModuloCliente.dao.retrofit;

import org.example.Common.EE.utils.ApiRespuesta;
import org.example.Common.modelo.Equipo;
import org.example.Common.modelo.Jornada;
import org.example.Common.modelo.Partido;
import org.example.Common.modelo.Usuario;
import org.example.ModuloCliente.dao.utils.Constantes;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface EquiposApi {

    @GET(Constantes.EQUIPO_GET_ALL)
    Call<List<Equipo>> getEquipos();

    @POST(Constantes.ADD_EQUIPO)
    Call<Equipo> addEquipo(@Body Equipo equipo);

    @DELETE(Constantes.EQUIPO_GET_ALL)
    Call<ApiRespuesta> deleteEquipo(@Query(Constantes.ID) String u);

    @PUT(Constantes.EQUIPO_GET_ALL)
    Call<ApiRespuesta> updateEquipo(@Body Equipo equipo);
}
