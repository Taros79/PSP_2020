package org.example.ModuloServidor.servicios;

import io.vavr.control.Either;
import org.example.Common.EE.errores.ApiError;
import org.example.Common.modelo.Usuario;
import org.example.Common.modelo.UsuarioLoginDTO;
import org.example.Common.modelo.UsuarioRegistro;
import org.example.ModuloServidor.dao.DaoUsuario;
import jakarta.inject.Inject;

import java.util.List;


public class ServiciosUsuarios {

    private DaoUsuario dao;


    @Inject
    public ServiciosUsuarios(DaoUsuario dao) {
        this.dao = dao;
    }

    public Either<ApiError, List<Usuario>> getUsuarios() { return dao.getUsuarios();}

    public Either<ApiError, UsuarioLoginDTO> getUsuarioLogin(String username) {
        return  dao.getUsuarioLogin(username);}

    public String addUsuario(UsuarioRegistro u) {
        return dao.addUsuario(u);
    }


}
