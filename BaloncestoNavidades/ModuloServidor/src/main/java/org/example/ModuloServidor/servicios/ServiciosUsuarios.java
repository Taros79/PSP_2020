package org.example.ModuloServidor.servicios;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import org.example.Common.EE.errores.ApiError;
import org.example.Common.EE.utils.ApiRespuesta;
import org.example.Common.EE.utils.HashPassword;
import org.example.Common.modelo.Usuario;
import org.example.Common.modelo.UsuarioLoginDTO;
import org.example.Common.modelo.UsuarioRegistro;
import org.example.ModuloServidor.dao.DaoUsuarios;

import java.time.LocalDateTime;
import java.util.List;


public class ServiciosUsuarios {

    private DaoUsuarios dao;
    private HashPassword hash;


    @Inject
    public ServiciosUsuarios(DaoUsuarios dao) {
        this.dao = dao;
    }

    public Either<ApiError, List<Usuario>> getUsuarios() {
        return dao.getUsuarios();
    }

    public Either<ApiError, UsuarioLoginDTO> getUsuarioLogin(String username) {
        return dao.getUsuarioLogin(username);
    }

    public Either<ApiError, Usuario> getUsuario(String username) {
        return dao.getUsuario(username);
    }

    public String addUsuario(UsuarioRegistro u) {
        return dao.addUsuario(u);
    }

    public String updateUsuario(String codActivacion, int isActivo, LocalDateTime fechaAlta, String username) {
        return dao.updateUsuario(codActivacion, isActivo, fechaAlta, username);
    }

    public Either<ApiError, ApiRespuesta> delUsuario(String u) {
        return dao.delUsuario(u);
    }


    public boolean login(String username, String password) {

        boolean loginOk = false;

        var resultado = dao.getUsuario(username);

        if (resultado.isRight()) {
            if (password.equals(resultado.get().getHashedPassword())) {
                loginOk = true;
            }
        }
        return loginOk;
    }
}
