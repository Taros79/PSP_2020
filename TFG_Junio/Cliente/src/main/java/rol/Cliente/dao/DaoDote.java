package rol.Cliente.dao;

import com.google.gson.Gson;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;
import rol.Cliente.dao.retrofit.ApiDote;
import rol.Common.modelo.Dote;
import rol.Common.modeloAO.RelacionId;

import javax.inject.Inject;
import java.util.List;

@Log4j2
public class DaoDote extends DaoGenerics {


    private final ApiDote apiDote;

    @Inject
    public DaoDote(Gson gson, ApiDote apiDote) {
        super(gson);
        this.apiDote = apiDote;
    }

    public Single<Either<String, List<Dote>>> getAllDotes() {
        return safeSingleApicall(apiDote.getAllDotes());
    }

    public Either<String, String> addDote(Dote d) {
        return construirEither(apiDote.addDote(d));
    }

    public Either<String, String> delDote(int id) {
        return construirEither(apiDote.delDote(id));
    }

    public Single<Either<String, String>> updateDote(Dote d) {
        return safeSingleApicall(apiDote.updateDote(d));
    }

    public Single<Either<String, List<Dote>>> getDotesByIdPersonaje(int id) {
        return safeSingleApicall(apiDote.getDotesByIdPersonaje(id));
    }

    public Single<Either<String, String>> addDoteToPersonaje(RelacionId r) {
        return safeSingleApicall(apiDote.addDoteToPersonaje(r));
    }

    public Single<Either<String, String>> delDoteToPersonaje(int id_Dote, int id2_Personaje) {
        return safeSingleApicall(apiDote.delDoteToPersonaje(id_Dote, id2_Personaje));
    }
}
