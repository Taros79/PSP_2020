package rol.Cliente.dao;

import com.google.gson.Gson;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;
import rol.Cliente.dao.retrofit.ApiHechizo;
import rol.Common.modelo.Hechizo;
import rol.Common.modeloAO.RelacionId;

import javax.inject.Inject;
import java.util.List;

@Log4j2
public class DaoHechizo extends DaoGenerics {


    private final ApiHechizo apiHechizo;


    @Inject
    public DaoHechizo(Gson gson, ApiHechizo apiHechizo) {
        super(gson);
        this.apiHechizo = apiHechizo;
    }

    public Single<Either<String, List<Hechizo>>> getAllHechizos() {
        return safeSingleApicall(apiHechizo.getAllHechizos());
    }

    public Either<String, String> addHechizo(Hechizo h) {
        return construirEither(apiHechizo.addHechizo(h));
    }

    public Either<String, String> delHechizo(int id) {
        return construirEither(apiHechizo.delHechizo(id));
    }

    public Single<Either<String, String>> updateHechizo(Hechizo h) {
        return safeSingleApicall(apiHechizo.updateHechizo(h));
    }

    public Single<Either<String, List<Hechizo>>> getHechizosByIdPersonaje(int id) {
        return safeSingleApicall(apiHechizo.getHechizosByIdPersonaje(id));
    }

    public Single<Either<String, String>> addHechizoToPersonaje(RelacionId r) {
        return safeSingleApicall(apiHechizo.addHechizoToPersonaje(r));
    }

    public Single<Either<String, String>> delHechizoToPersonaje(int id_Hechizo, int id2_Personaje) {
        return safeSingleApicall(apiHechizo.delHechizoToPersonaje(id_Hechizo, id2_Personaje));
    }
}
