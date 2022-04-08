package rol.Cliente.dao.retrofit;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.http.*;
import rol.Common.constantes.ConstantesRest;
import rol.Common.modelo.Hechizo;
import rol.Common.modeloAO.RelacionId;

import java.util.List;

public interface ApiHechizo {

    @GET(ConstantesRest.PATH_HECHIZOS)
    Single<List<Hechizo>> getAllHechizos();

    @POST(ConstantesRest.PATH_HECHIZOS)
    Call<String> addHechizo(@Body Hechizo h);

    @DELETE(ConstantesRest.PATH_HECHIZOS)
    Call<String> delHechizo(@Query("id") int id);

    @PUT(ConstantesRest.PATH_HECHIZOS)
    Single<String> updateHechizo(@Body Hechizo h);

    @GET(ConstantesRest.PATH_HECHIZOS + ConstantesRest.PATH_HECHIZOS_BY_ID_PERSONAJE)
    Single<List<Hechizo>> getHechizosByIdPersonaje(@Query("id") int id);

    @POST(ConstantesRest.PATH_HECHIZOS + ConstantesRest.PATH_HECHIZO_ADD_TO_PERSONAJE)
    Single<String> addHechizoToPersonaje(@Body RelacionId r);

    @DELETE(ConstantesRest.PATH_HECHIZOS + ConstantesRest.PATH_DEL_HECHIZO_TO_PERSONAJE)
    Single<String> delHechizoToPersonaje(@Query("id_Hechizo") int id_Hechizo, @Query("id2_Personaje") int id2_Personaje);
}
