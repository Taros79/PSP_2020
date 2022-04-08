package rol.Cliente.Servicios;

import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import rol.Cliente.dao.DaoDote;
import rol.Common.modelo.Dote;
import rol.Common.modeloAO.RelacionId;

import javax.inject.Inject;
import java.util.List;

public class ServiciosDote {

    private DaoDote daoDote;

    @Inject
    public ServiciosDote(DaoDote daoDote) {
        this.daoDote = daoDote;
    }

    public Single<Either<String, List<Dote>>> getAllDotes() {
        return daoDote.getAllDotes();
    }

    public Either<String, String> addDote(Dote d) {
        return daoDote.addDote(d);
    }

    public Either<String, String> delDote(int id) {
        return daoDote.delDote(id);
    }

    public Single<Either<String, String>> updateDote(Dote d) {
        return daoDote.updateDote(d);
    }

    public Single<Either<String, List<Dote>>> getDotesByIdPersonaje(int id) {
        return daoDote.getDotesByIdPersonaje(id);
    }

    public Single<Either<String, String>> addDoteToPersonaje(RelacionId r) {
        return daoDote.addDoteToPersonaje(r);
    }

    public Single<Either<String, String>> delDoteToPersonaje(int id_Dote, int id2_Personaje) {
        return daoDote.delDoteToPersonaje(id_Dote, id2_Personaje);
    }
}
