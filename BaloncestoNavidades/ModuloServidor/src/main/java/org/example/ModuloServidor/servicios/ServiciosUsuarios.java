package org.example.ModuloServidor.servicios;

import io.vavr.control.Either;
import org.example.Common.EE.errores.ApiError;
import org.example.Common.modelo.Usuario;
import org.example.ModuloServidor.dao.DaoUsuario;
import jakarta.inject.Inject;
import jakarta.validation.Validator;

import java.util.List;


public class ServiciosUsuarios {


    private Validator validator;


    private DaoUsuario dao;


    @Inject
    public ServiciosUsuarios(Validator validator, DaoUsuario dao) {
        this.validator = validator;
        this.dao = dao;
    }

    public Either<ApiError, List<Usuario>> getUsuarios() { return dao.getUsuarios();}
}
