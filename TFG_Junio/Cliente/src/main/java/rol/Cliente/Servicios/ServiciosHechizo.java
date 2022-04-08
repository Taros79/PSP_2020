package rol.Cliente.Servicios;

import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import rol.Cliente.dao.DaoHechizo;
import rol.Common.modelo.Hechizo;
import rol.Common.modeloAO.RelacionId;

import javax.inject.Inject;
import java.util.List;

public class ServiciosHechizo {

    private DaoHechizo daoHechizo;

    @Inject
    public ServiciosHechizo(DaoHechizo daoHechizo) {
        this.daoHechizo = daoHechizo;
    }

    public Single<Either<String, List<Hechizo>>> getAllHechizos() {
        return daoHechizo.getAllHechizos();
    }

    public Either<String, String> addHechizo(Hechizo h) {
        return daoHechizo.addHechizo(h);
    }

    public Either<String, String> delHechizo(int id) {
        return daoHechizo.delHechizo(id);
    }

    public Single<Either<String, String>> updateHechizo(Hechizo h) {
        return daoHechizo.updateHechizo(h);
    }

    public Single<Either<String, List<Hechizo>>> getHechizosByIdPersonaje(int id) {
        return daoHechizo.getHechizosByIdPersonaje(id);
    }

    public Single<Either<String, String>> addHechizoToPersonaje(RelacionId r) {
        return daoHechizo.addHechizoToPersonaje(r);
    }

    public Single<Either<String, String>> delHechizoToPersonaje(int id_Hechizo, int id2_Personaje) {
        return daoHechizo.delHechizoToPersonaje(id_Hechizo, id2_Personaje);
    }
}
