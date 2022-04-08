package rol.Cliente.dao;

import com.google.gson.Gson;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;
import rol.Cliente.dao.retrofit.ApiPersonaje;
import rol.Common.modelo.Personaje;
import rol.Common.modeloAO.PersonajeBBDD;

import javax.inject.Inject;
import java.util.List;

@Log4j2
public class DaoPersonaje extends DaoGenerics {


    private final ApiPersonaje apiPersonaje;


    @Inject
    public DaoPersonaje(Gson gson, ApiPersonaje apiPersonaje) {
        super(gson);
        this.apiPersonaje = apiPersonaje;
    }

    public Single<Either<String, List<Personaje>>> getAllPersonajes() {
        return safeSingleApicall(apiPersonaje.getAllPersonajes());
    }

    public Single<Either<String, String>> addPersonaje(PersonajeBBDD p) {
        return safeSingleApicall(apiPersonaje.addPersonaje(p));
    }

    public Single<Either<String, String>> delPersonaje(int id_Personaje, int id_Estadistica) {
        return safeSingleApicall(apiPersonaje.delPersonaje(id_Personaje, id_Estadistica));
    }

    public Single<Either<String, String>> updatePersonaje(Personaje p) {
        return safeSingleApicall(apiPersonaje.updatePersonaje(p));
    }

    public Single<Either<String, List<Personaje>>> getPersonajesByIdUsuario(int id) {
        return safeSingleApicall(apiPersonaje.getPersonajesByIdUsuario(id));
    }
}
