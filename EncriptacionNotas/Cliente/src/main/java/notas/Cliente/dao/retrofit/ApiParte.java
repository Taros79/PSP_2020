package notas.Cliente.dao.retrofit;

import io.reactivex.rxjava3.core.Single;
import notas.Common.modelo.Parte;
import retrofit2.Call;
import retrofit2.http.*;
import notas.Common.constantes.ConstantesRest;

import java.util.List;

public interface ApiParte {

    /*@GET(ConstantesRest.PATH_DOTES)
    Single<List<Dote>> getAllDotes();

    @POST(ConstantesRest.PATH_DOTES)
    Call<String> addDote(@Body Dote d);

    @DELETE(ConstantesRest.PATH_DOTES)
    Call<String> delDote(@Query(ConstantesRest.ID) int id);

    @PUT(ConstantesRest.PATH_DOTES)
    Single<String> updateDote(@Body Dote d);

    @GET(ConstantesRest.PATH_DOTES + ConstantesRest.PATH_DOTES_BY_ID_PERSONAJE)
    Single<List<Dote>> getDotesByIdPersonaje(@Query(ConstantesRest.ID) int id);

    @POST(ConstantesRest.PATH_DOTES + ConstantesRest.PATH_DOTE_ADD_TO_PERSONAJE)
    Single<String> addDoteToPersonaje(@Body RelacionId r);

    @DELETE(ConstantesRest.PATH_DOTES + ConstantesRest.PATH_DEL_DOTE_TO_PERSONAJE)
    Single<String> delDoteToPersonaje(@Query("id_Dote") int id_Dote, @Query("id2_Personaje") int id2_Personaje);
    */

    @GET("ConstantesRest.PATH_PARTES")
    Single<List<Parte>> getAllPartes();

}
