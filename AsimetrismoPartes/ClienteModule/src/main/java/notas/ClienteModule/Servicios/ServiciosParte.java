package notas.ClienteModule.Servicios;

import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import notas.ClienteModule.dao.DaoParte;
import notas.CommonModule.modelo.Parte;
import notas.CommonModule.modelo.ParteProfesorPadre;

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

    public Single<Either<String, String>> addParte(ParteProfesorPadre parte) {
        return daoParte.addParte(parte);
    }

    public Either<String, Integer> addParteTO(Parte parte) {
        return daoParte.addParteTO(parte);
    }

    public Single<Either<String, String>> addParteCompartido(int idUsuario, int idParte) {
        return daoParte.addParteCompartido(idUsuario, idParte);
    }

    public Single<Either<String, String>> updateParte(int idParte, int estado) {
        return daoParte.updateParte(idParte, estado);
    }
}
