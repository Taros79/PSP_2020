package rol.Cliente.dao.retrofit;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.*;
import rol.Common.constantes.ConstantesRest;
import rol.Common.modelo.Partida;

import java.util.List;

public interface ApiPartida {

    @GET(ConstantesRest.PATH_PARTIDAS)
    Single<List<Partida>> getAllPartidas();

    @POST(ConstantesRest.PATH_PARTIDAS)
    Single<String> addPartida(@Body Partida p);

    @DELETE(ConstantesRest.PATH_PARTIDAS)
    Single<String> delPartida(@Query(ConstantesRest.ID) int id);

    @PUT(ConstantesRest.PATH_PARTIDAS)
    Single<String> updatePartida(@Body Partida p);
}
