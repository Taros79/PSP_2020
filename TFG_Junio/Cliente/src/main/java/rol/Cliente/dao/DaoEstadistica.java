package rol.Cliente.dao;

import com.google.gson.Gson;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;
import rol.Cliente.dao.retrofit.ApiEstadistica;
import rol.Common.modelo.Estadistica;

import javax.inject.Inject;

@Log4j2
public class DaoEstadistica extends DaoGenerics {


    private final ApiEstadistica apiEstadistica;


    @Inject
    public DaoEstadistica(Gson gson, ApiEstadistica apiEstadistica) {
        super(gson);
        this.apiEstadistica = apiEstadistica;
    }

    public Single<Either<String, Estadistica>> getEstadisticaById(int id) {
        return safeSingleApicall(apiEstadistica.getEstadisticaById(id));
    }

    public Single<Either<String, String>> addEstadistica(Estadistica es) {
        return safeSingleApicall(apiEstadistica.addEstadistica(es));
    }

    public Single<Either<String, String>> delEstadistica(int id) {
        return safeSingleApicall(apiEstadistica.delEstadistica(id));
    }

    public Single<Either<String, String>> updateEstadistica(Estadistica es) {
        return safeSingleApicall(apiEstadistica.updateEstadistica(es));
    }
}
