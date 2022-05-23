package notas.ClienteModule.dao;

import com.google.gson.Gson;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;
import notas.ClienteModule.dao.retrofit.ApiParte;
import notas.CommonModule.modelo.Parte;

import javax.inject.Inject;
import java.util.List;

@Log4j2
public class DaoParte extends DaoGenerics {

    private final ApiParte apiParte;

    @Inject
    public DaoParte(Gson gson, ApiParte apiParte) {
        super(gson);
        this.apiParte = apiParte;
    }

    public Single<Either<String, List<Parte>>> getAllPartes() {
        return safeSingleApicall(apiParte.getAllPartes());
    }

    public Single<Either<String, List<Parte>>> getPartesByUser(int idPadre) {
        return safeSingleApicall(apiParte.getPartesByUser(idPadre));
    }

    public Single<Either<String, String>> addParte(Parte parte) {
        return safeSingleApicall(apiParte.addParte(parte));
    }

    public Single<Either<String, String>> updateParte(int idParte, int estado) {
        return safeSingleApicall(apiParte.updateParte(idParte, estado));
    }
}
