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

    @POST(ConstantesRest.PATH_PARTES)
    Single<String> addParte(@Body Parte p);
}
