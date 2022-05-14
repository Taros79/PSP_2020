package notas.Cliente.dao;

import com.google.gson.Gson;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;
import notas.Cliente.dao.retrofit.ApiParte;
import notas.Common.modelo.Parte;

import javax.inject.Inject;
import java.util.List;

@Log4j2
public class DaoParte extends DaoGenerics{

    private final ApiParte apiParte;

    @Inject
    public DaoParte(Gson gson, ApiParte apiParte) {
        super(gson);
        this.apiParte = apiParte;
    }

    public Single<Either<String, List<Parte>>> getAllPartes() {
        return safeSingleApicall(apiParte.getAllPartes());
    }
}