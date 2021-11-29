package GID.ModuloCliente.dao.retrofit;

import GID.Commons.dao.modelo.Persona;
import GID.ModuloCliente.dao.utils.Constantes;
import GID.Commons.EE.utils.ApiRespuesta;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface PersonaApi {

    @GET(Constantes.PERSONA_GET_ALL)
    Call<List<Persona>> getRecursosPersona();

    @GET(Constantes.GET_PERSONA_ID)
    Call<Persona> getRecursosUnaPersona(@Path("id") String id);

    @POST(Constantes.ADD_DELETE_UPDATE_PERSONA)
    Call<Persona> addPersona(@Body Persona p);

    @DELETE(Constantes.ADD_DELETE_UPDATE_PERSONA)
    Call<ApiRespuesta> deletePersona(@Query("id") String id);

    @PUT(Constantes.ADD_DELETE_UPDATE_PERSONA)
    Call<ApiRespuesta> casamientoPareja(@Query("idH") String idH, @Query("idM") String idM);

    @PUT("personas/agregarHijo")
    Call<ApiRespuesta> procrear(@Body Persona p, @Query("idPadres") String idPadres);
}
