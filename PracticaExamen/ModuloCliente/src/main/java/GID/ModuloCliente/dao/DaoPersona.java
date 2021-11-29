package GID.ModuloCliente.dao;

import GID.Commons.EE.utils.ApiRespuesta;
import GID.Commons.dao.modelo.Persona;
import io.vavr.control.Either;

import java.util.List;

public interface DaoPersona {
    Either<String, List<Persona>> getAllPersona();

    Either<String, Persona> getDatosByNombre(String id);

    Either<String, Persona> addPersona(Persona p);

    ApiRespuesta deletePersona(String id);

     ApiRespuesta casamientoPareja (String idH, String idM);

    ApiRespuesta procrear (Persona p, String idPadres);
}
