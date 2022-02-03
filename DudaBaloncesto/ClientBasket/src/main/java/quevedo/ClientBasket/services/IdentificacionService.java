package quevedo.ClientBasket.services;

import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import quevedo.ClientBasket.dao.IdentificacionDao;
import quevedo.common.modelo.Mensaje;
import quevedo.common.modelo.User;
import quevedo.common.modelo.UserLoged;

import javax.inject.Inject;

public class IdentificacionService {

    private final IdentificacionDao identificacionDao;

    @Inject
    public IdentificacionService(IdentificacionDao identificacionDao) {
        this.identificacionDao = identificacionDao;
    }

    public Single<Either<String, Mensaje>> registrarse(User user) {
        return identificacionDao.register(user);
    }

    public Single<Either<String, UserLoged>> login(User user) {
        return identificacionDao.login(user);
    }

    public Single<Either<String, Void>> cambiarPassw(String correo) {
        return identificacionDao.cambiarPassw(correo);
    }

    public Single<Either<String, Mensaje>> registrarAdmin(User user) {
        return identificacionDao.addAdmin(user);
    }

}
