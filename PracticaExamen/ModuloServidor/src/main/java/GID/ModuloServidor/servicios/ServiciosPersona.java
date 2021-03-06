package GID.ModuloServidor.servicios;

import GID.Commons.EE.utils.ApiRespuesta;
import GID.Commons.dao.modelo.Persona;
import GID.ModuloServidor.EE.errores.ApiError;
import GID.ModuloServidor.dao.DaoPersona;
import io.vavr.control.Either;
import jakarta.inject.Inject;

import java.util.List;

public class ServiciosPersona {

    private final DaoPersona dao;

    @Inject
    public ServiciosPersona(DaoPersona dao) {
        this.dao = dao;
    }

    public Either<ApiError, Persona> getPersona(String id) {
        return dao.getPersona(id);
    }

    public Either<ApiError, List<Persona>> getAll() {
        return dao.getAll();
    }

    public Either<ApiError, Persona> addPersona(Persona p) {
        return dao.addPersona(p);
    }

    public Either<ApiError, ApiRespuesta> borrarPersona(String id) {
        return dao.borrarPersona(id);
    }

    public Either<ApiError, ApiRespuesta> casamientoPareja(String idH, String idM) {
        return dao.casamientoPareja(idH, idM);
    }

    public Either<ApiError, ApiRespuesta> procrear(Persona p, String idPadres) {
        return dao.procrear(p, idPadres);
    }
}
