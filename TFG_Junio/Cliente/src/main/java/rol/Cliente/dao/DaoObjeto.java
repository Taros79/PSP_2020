package rol.Cliente.dao;

import com.google.gson.Gson;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;
import rol.Cliente.dao.retrofit.ApiObjeto;
import rol.Common.modelo.Objeto;
import rol.Common.modeloAO.RelacionId;

import javax.inject.Inject;
import java.util.List;

@Log4j2
public class DaoObjeto extends DaoGenerics {


    private final ApiObjeto apiObjeto;


    @Inject
    public DaoObjeto(Gson gson, ApiObjeto apiObjeto) {
        super(gson);
        this.apiObjeto = apiObjeto;
    }

    public Single<Either<String, List<Objeto>>> getAllObjetos() {
        return safeSingleApicall(apiObjeto.getAllObjetos());
    }

    public Either<String, String> addObjeto(Objeto o) {
        return construirEither(apiObjeto.addObjeto(o));
    }

    public Either<String, String> delObjeto(int id) {
        return construirEither(apiObjeto.delObjeto(id));
    }

    public Single<Either<String, String>> updateObjeto(Objeto o) {
        return safeSingleApicall(apiObjeto.updateObjeto(o));
    }

    public Single<Either<String, List<Objeto>>> getObjetosByIdPersonaje(int id) {
        return safeSingleApicall(apiObjeto.getObjetosByIdPersonaje(id));
    }

    public Single<Either<String, String>> addObjetoToPersonaje(RelacionId r) {
        return safeSingleApicall(apiObjeto.addObjetoToPersonaje(r));
    }

    public Single<Either<String, String>> delObjetoToPersonaje(int id_Objeto, int id2_Personaje) {
        return safeSingleApicall(apiObjeto.delObjetoToPersonaje(id_Objeto, id2_Personaje));
    }
}
