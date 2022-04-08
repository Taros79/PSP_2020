package rol.Cliente.dao;

import com.google.gson.Gson;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;
import rol.Cliente.dao.retrofit.ApiPartida;
import rol.Common.modelo.Partida;

import javax.inject.Inject;
import java.util.List;

@Log4j2
public class DaoPartida extends DaoGenerics {


    private final ApiPartida apiPartida;


    @Inject
    public DaoPartida(Gson gson, ApiPartida apiPartida) {
        super(gson);
        this.apiPartida = apiPartida;
    }

    public Single<Either<String, List<Partida>>> getAllPartidas() {
        return safeSingleApicall(apiPartida.getAllPartidas());
    }

    public Single<Either<String, String>> addPartida(Partida p) {
        return safeSingleApicall(apiPartida.addPartida(p));
    }

    public Single<Either<String, String>> delPartida(int id) {
        return safeSingleApicall(apiPartida.delPartida(id));
    }

    public Single<Either<String, String>> updatePartida(Partida p) {
        return safeSingleApicall(apiPartida.updatePartida(p));
    }
}
