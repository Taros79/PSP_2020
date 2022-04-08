package rol.Cliente.Servicios;

import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import rol.Cliente.dao.DaoObjeto;
import rol.Common.modelo.Objeto;
import rol.Common.modeloAO.RelacionId;

import javax.inject.Inject;
import java.util.List;

public class ServiciosObjeto {

    private DaoObjeto daoObjeto;
    ;

    @Inject
    public ServiciosObjeto(DaoObjeto daoObjeto) {
        this.daoObjeto = daoObjeto;
    }

    public Single<Either<String, List<Objeto>>> getAllObjetos() {
        return daoObjeto.getAllObjetos();
    }

    public Either<String, String> addObjeto(Objeto o) {
        return daoObjeto.addObjeto(o);
    }

    public Either<String, String> delObjeto(int id) {
        return daoObjeto.delObjeto(id);
    }

    public Single<Either<String, String>> updateObjeto(Objeto o) {
        return daoObjeto.updateObjeto(o);
    }

    public Single<Either<String, List<Objeto>>> getObjetosByIdPersonaje(int id) {
        return daoObjeto.getObjetosByIdPersonaje(id);
    }

    public Single<Either<String, String>> addObjetoToPersonaje(RelacionId r) {
        return daoObjeto.addObjetoToPersonaje(r);
    }

    public Single<Either<String, String>> delObjetoToPersonaje(int id_Objeto, int id2_Personaje) {
        return daoObjeto.delObjetoToPersonaje(id_Objeto, id2_Personaje);
    }
}
