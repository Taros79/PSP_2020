package notas.ClienteModule.Servicios;

import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import notas.ClienteModule.dao.DaoParte;
import notas.CommonModule.modelo.Parte;

import javax.inject.Inject;
import java.util.List;

public class ServiciosParte {

    private DaoParte daoParte;

    @Inject
    public ServiciosParte(DaoParte daoParte) {
        this.daoParte = daoParte;
    }

    public Single<Either<String, List<Parte>>> getAllPartes() {
        return daoParte.getAllPartes();
    }

    public Single<Either<String, List<Parte>>> getPartesByUser(int idPadre) {
        return daoParte.getPartesByUser(idPadre);
    }

    public Single<Either<String, Integer>> addParte(Parte parte) {
        return daoParte.addParte(parte);
    }

    public Single<Either<String, String>> addParteCompartido(String username, int idParte) {
        return daoParte.addParteCompartido(username, idParte);
    }

    public Single<Either<String, String>> updateParte(int idParte, int estado) {
        return daoParte.updateParte(idParte, estado);
    }
}
