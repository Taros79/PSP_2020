package GID.ModuloCliente.dao.retrofit;

import GID.Commons.dao.modelo.Persona;
import GID.ModuloCliente.dao.utils.Constantes;
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
    Call<Persona> deletePersona(@Query("id") String id);

    @PUT(Constantes.ADD_DELETE_UPDATE_PERSONA)
    Call<Persona> actualizarPersona(@Body Persona p);

}
