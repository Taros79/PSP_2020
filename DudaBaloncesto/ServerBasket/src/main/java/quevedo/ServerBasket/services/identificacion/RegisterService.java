package quevedo.ServerBasket.services.identificacion;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import quevedo.ServerBasket.dao.RegisterDao;
import quevedo.ServerBasket.dao.model.UserDatabase;

public class RegisterService {


    private final RegisterDao registerDao;

    @Inject
    public RegisterService(RegisterDao registerDao) {
        this.registerDao = registerDao;
    }


    public Either<String, Boolean> addUser(UserDatabase user) {
        return registerDao.addUser(user);
    }

    public Either<String, Boolean> activar(String codActivacion) {
        return registerDao.activarUser(codActivacion);
    }

    public Either<String, Boolean> comprobarFecha(String codActivacion) {
        return registerDao.comprobarFecha(codActivacion);
    }

    public Either<String, Boolean> cambiarCodActivacion(String codNuevo, String codActivacion) {
        return registerDao.cambiarCodActivacion(codNuevo, codActivacion);
    }

}
