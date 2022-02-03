package quevedo.ServerBasket.services.identificacion;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import quevedo.ServerBasket.dao.LoginDao;
import quevedo.common.modelo.User;

public class LoginService {


    private final LoginDao loginDao;

    @Inject
    public LoginService(LoginDao loginDao) {
        this.loginDao = loginDao;
    }

    public Either<String, User> geUser(String correo) {
        return loginDao.getUser(correo);
    }


}
