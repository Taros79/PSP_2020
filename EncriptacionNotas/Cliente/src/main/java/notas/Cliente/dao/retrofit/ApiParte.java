package notas.Cliente.dao.retrofit;

import io.reactivex.rxjava3.core.Single;
import notas.Common.modelo.Parte;
import retrofit2.Call;
import retrofit2.http.*;
import notas.Common.constantes.ConstantesRest;

import java.util.List;

public interface ApiParte {

    @GET(ConstantesRest.PATH_PARTES)
    Single<List<Parte>> getAllPartes();

    @GET(ConstantesRest.PATH_PARTES + ConstantesRest.PATH_PARTES_ALUMNOS_BY_USUARIO)
    Single <List<Parte>> getPartesByUser(@Query("idPadre") int idPadre);

    @POST(ConstantesRest.PATH_PARTES)
    Single<String> addParte(@Body Parte p);

    @PUT(ConstantesRest.PATH_PARTES)
    Single<String> updateParte(@Query("idParte") int idParte, @Query("estado") int estado);
}
