package org.example.ModuloServidor.servicios;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import org.example.Common.modelo.User;
import org.example.ModuloServidor.dao.DaoLogin;

public class ServicioLogin {


    private final DaoLogin daoLogin;

    @Inject
    public ServicioLogin(DaoLogin daoLogin) {
        this.daoLogin = daoLogin;
    }

    public Either<String, User> geUser(String correo) {
        return daoLogin.getUser(correo);
    }


}
