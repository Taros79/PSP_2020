package rol.Cliente.Servicios;

import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import rol.Cliente.dao.DaoPartida;
import rol.Common.modelo.Partida;

import javax.inject.Inject;
import java.util.List;

public class ServiciosPartida {

    private DaoPartida daoPartida;

    @Inject
    public ServiciosPartida(DaoPartida daoPartida) {
        this.daoPartida = daoPartida;
    }

    public Single<Either<String, List<Partida>>> getAllPartidas() {
        return daoPartida.getAllPartidas();
    }

    public Single<Either<String, String>> addPartida(Partida p) {
        return daoPartida.addPartida(p);
    }

    public Single<Either<String, String>> delPartida(int id) {
        return daoPartida.delPartida(id);
    }

    public Single<Either<String, String>> updatePartida(Partida p) {
        return daoPartida.updatePartida(p);
    }
}
