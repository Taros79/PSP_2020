package GID.ModuloServidor.servicios;

import GID.Commons.dao.modelo.Persona;
import GID.ModuloServidor.EE.errores.ApiError;
import GID.ModuloServidor.dao.DaoPersona;
import io.vavr.control.Either;
import jakarta.inject.Inject;

import java.util.List;

public class ServiciosPersona {

    @Inject
    private DaoPersona dao;

    public Either<ApiError, Persona> getPersona(String id)
    {
        return dao.getPersona(id);
    }

    public List<Persona> getAll()
    {
        return dao.getAll();
    }

}
