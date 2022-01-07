package org.example.ModuloCliente.servicios;

import io.vavr.control.Either;
import org.example.Common.EE.errores.ApiError;
import org.example.Common.EE.utils.ApiRespuesta;
import org.example.Common.modelo.Usuario;
import org.example.Common.modelo.UsuarioLoginDTO;
import org.example.Common.modelo.UsuarioRegistro;
import org.example.ModuloCliente.dao.DaoUsuario;

import javax.inject.Inject;
import java.util.List;

public class ServiciosUsuario {

    private DaoUsuario dao;

    @Inject
    public ServiciosUsuario(DaoUsuario dao) {
        this.dao = dao;
    }


    public Either<ApiError, List<Usuario>> getAllUsuario() {
        return dao.getAllUsuario();
    }

    public Either<ApiError, UsuarioRegistro> addUsuarioRegistro(UsuarioRegistro u) {
        return dao.addUsuarioRegistro(u);
    }

    public Either<ApiError, UsuarioLoginDTO> getUsuarioLogin(String u) {
        return dao.getUsuarioLogin(u);
    }

    public String mandarMail(String correo, String username) {
        return dao.mandarMail(correo, username);
    }

    public Either<ApiError, ApiRespuesta> deleteUsuario(String u) {
        return dao.deleteUsuario(u);
    }

    public Either<ApiError, ApiRespuesta> updateUsuario(Usuario u) {
        return dao.updateUsuario(u);
    }

    public String login(String username, String password) {
        return dao.login(username, password);
    }
}
