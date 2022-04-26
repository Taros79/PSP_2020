package rol.Cliente.dao.retrofit;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.http.*;
import rol.Common.constantes.ConstantesRest;
import rol.Common.modelo.Objeto;
import rol.Common.modeloAO.RelacionId;

import java.util.List;

public interface ApiObjeto {

    @GET(ConstantesRest.PATH_OBJETOS)
    Single<List<Objeto>> getAllObjetos();

    @POST(ConstantesRest.PATH_OBJETOS)
    Call<String> addObjeto(@Body Objeto o);

    @DELETE(ConstantesRest.PATH_OBJETOS)
    Call<String> delObjeto(@Query(ConstantesRest.ID) int id);

    @PUT(ConstantesRest.PATH_OBJETOS)
    Single<String> updateObjeto(@Body Objeto o);

    @GET(ConstantesRest.PATH_OBJETOS + ConstantesRest.PATH_OBJETOS_BY_ID_PERSONAJE)
    Single<List<Objeto>> getObjetosByIdPersonaje(@Query(ConstantesRest.ID) int id);

    @POST(ConstantesRest.PATH_OBJETOS + ConstantesRest.PATH_OBJETO_ADD_TO_PERSONAJE)
    Single<String> addObjetoToPersonaje(@Body RelacionId r);

    @DELETE(ConstantesRest.PATH_OBJETOS + ConstantesRest.PATH_DEL_OBJETO_TO_PERSONAJE)
    Single<String> delObjetoToPersonaje(@Query("id_Objeto") int id_Objeto, @Query("id2_Personaje") int id2_Personaje);

}
