package rol.Cliente.dao.retrofit;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.http.*;
import rol.Common.constantes.ConstantesRest;
import rol.Common.modelo.Dote;
import rol.Common.modeloAO.RelacionId;

import java.util.List;

public interface ApiDote {

    @GET(ConstantesRest.PATH_DOTES)
    Single<List<Dote>> getAllDotes();

    @POST(ConstantesRest.PATH_DOTES)
    Call<String> addDote(@Body Dote d);

    @DELETE(ConstantesRest.PATH_DOTES)
    Call<String> delDote(@Query("id") int id);

    @PUT(ConstantesRest.PATH_DOTES)
    Single<String> updateDote(@Body Dote d);

    @GET(ConstantesRest.PATH_DOTES + ConstantesRest.PATH_DOTES_BY_ID_PERSONAJE)
    Single<List<Dote>> getDotesByIdPersonaje(@Query("id") int id);

    @POST(ConstantesRest.PATH_DOTES + ConstantesRest.PATH_DOTE_ADD_TO_PERSONAJE)
    Single<String> addDoteToPersonaje(@Body RelacionId r);

    @DELETE(ConstantesRest.PATH_DOTES + ConstantesRest.PATH_DEL_DOTE_TO_PERSONAJE)
    Single<String> delDoteToPersonaje(@Query("id_Dote") int id_Dote, @Query("id2_Personaje") int id2_Personaje);
}
