package notas.Cliente.Servicios;

import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import notas.Cliente.dao.DaoParte;
import notas.Common.modelo.Parte;

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

    public Single<Either<String, String>> addParte(Parte parte) {
        return daoParte.addParte(parte);
    }
}
