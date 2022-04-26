package rol.Cliente.Servicios;

import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import rol.Cliente.dao.DaoPersonaje;
import rol.Common.modelo.Personaje;
import rol.Common.modeloAO.PersonajeBBDD;

import javax.inject.Inject;
import java.util.List;

public class ServiciosPersonaje {

    private DaoPersonaje daoPersonaje;

    @Inject
    public ServiciosPersonaje(DaoPersonaje daoPersonaje) {
        this.daoPersonaje = daoPersonaje;
    }

    public Single<Either<String, List<Personaje>>> getAllPersonajes() {
        return daoPersonaje.getAllPersonajes();
    }

    public Single<Either<String, String>> addPersonaje(PersonajeBBDD p) {
        return daoPersonaje.addPersonaje(p);
    }

    public Single<Either<String, String>> delPersonaje(int id_Personaje, int id_Estadistica) {
        return daoPersonaje.delPersonaje(id_Personaje, id_Estadistica);
    }

    public Single<Either<String, String>> updatePersonaje(Personaje p) {
        return daoPersonaje.updatePersonaje(p);
    }

    public Single<Either<String, List<Personaje>>> getPersonajesByIdUsuario(int id) {
        return daoPersonaje.getPersonajesByIdUsuario(id);
    }

    public Single<Either<String, String>> addPersonajeToUsuario(PersonajeBBDD p) {
        return daoPersonaje.addPersonajeToUsuario(p);
    }
}
