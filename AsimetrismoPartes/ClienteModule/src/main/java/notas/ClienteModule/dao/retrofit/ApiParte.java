package notas.ClienteModule.dao.retrofit;

import io.reactivex.rxjava3.core.Single;
import notas.CommonModule.constantes.ConstantesRest;
import notas.CommonModule.modelo.Parte;
import retrofit2.http.*;

import java.util.List;

public interface ApiParte {

    @GET(ConstantesRest.PATH_PARTES)
    Single<List<Parte>> getAllPartes();

    @GET(ConstantesRest.PATH_PARTES + ConstantesRest.PATH_PARTES_ALUMNOS_BY_USUARIO)
    Single<List<Parte>> getPartesByUser(@Query("idPadre") int idPadre);

    @POST(ConstantesRest.PATH_PARTES)
    Single<Integer> addParte(@Body Parte p);

    @POST(ConstantesRest.PATH_PARTES + ConstantesRest.PATH_ADD_PARTE_COMPARTIDO)
    Single<String> addParteCompartido(@Query("username")String username, @Query("idParte")int idParte);

    @PUT(ConstantesRest.PATH_PARTES)
    Single<String> updateParte(@Query("idParte") int idParte, @Query("estado") int estado);
}
