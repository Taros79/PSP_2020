package rol.Cliente.Servicios;

import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import rol.Cliente.dao.DaoEstadistica;
import rol.Common.modelo.Estadistica;

import javax.inject.Inject;

public class ServiciosEstadistica {

    private DaoEstadistica daoEstadistica;

    @Inject
    public ServiciosEstadistica(DaoEstadistica daoEstadistica) {
        this.daoEstadistica = daoEstadistica;
    }

    public Single<Either<String, Estadistica>> getEstadisticaById(int id) {
        return daoEstadistica.getEstadisticaById(id);
    }

    public Single<Either<String, String>> addEstadistica(Estadistica es) {
        return daoEstadistica.addEstadistica(es);
    }

    public Single<Either<String, String>> delEstadistica(int id) {
        return daoEstadistica.delEstadistica(id);
    }

    public Single<Either<String, String>> updateEstadistica(Estadistica es) {
        return daoEstadistica.updateEstadistica(es);
    }
}
