package rol.Cliente.dao.retrofit;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.*;
import rol.Common.constantes.ConstantesRest;
import rol.Common.modelo.Personaje;
import rol.Common.modeloAO.PersonajeBBDD;

import java.util.List;

public interface ApiPersonaje {

    @GET(ConstantesRest.PATH_PERSONAJES)
    Single<List<Personaje>> getAllPersonajes();

    @POST(ConstantesRest.PATH_PERSONAJES)
    Single<String> addPersonaje(@Body PersonajeBBDD p);

    @DELETE(ConstantesRest.PATH_PERSONAJES)
    Single<String> delPersonaje(@Query("id_Personaje") int id_Personaje, @Query("id_Estadistica") int id_Estadistica);

    @PUT(ConstantesRest.PATH_PERSONAJES)
    Single<String> updatePersonaje(@Body Personaje p);

    @GET(ConstantesRest.PATH_PERSONAJES + ConstantesRest.PATH_PERSONAJES_BY_ID_USUARIO)
    Single<List<Personaje>> getPersonajesByIdUsuario(@Query(ConstantesRest.ID) int id);

    @POST(ConstantesRest.PATH_PERSONAJES + ConstantesRest.PATH_PERSONAJE_ADD_TO_USUARIO)
    Single<String> addPersonajeToUsuario(@Body PersonajeBBDD p);
}
