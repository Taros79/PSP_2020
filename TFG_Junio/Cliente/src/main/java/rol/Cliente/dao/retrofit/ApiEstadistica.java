package rol.Cliente.dao.retrofit;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.*;
import rol.Common.constantes.ConstantesRest;
import rol.Common.modelo.Estadistica;

public interface ApiEstadistica {

    @GET(ConstantesRest.PATH_ESTADISTICAS)
    Single<Estadistica> getEstadisticaById(@Query("id") int id);

    @POST(ConstantesRest.PATH_ESTADISTICAS)
    Single<String> addEstadistica(@Body Estadistica e);

    @DELETE(ConstantesRest.PATH_ESTADISTICAS)
    Single<String> delEstadistica(@Query("id") int id);

    @PUT(ConstantesRest.PATH_ESTADISTICAS)
    Single<String> updateEstadistica(@Body Estadistica e);
}
