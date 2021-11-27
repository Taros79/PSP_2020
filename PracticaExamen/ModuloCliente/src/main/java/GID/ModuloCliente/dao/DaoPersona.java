package GID.ModuloCliente.dao;

import GID.Commons.dao.modelo.Persona;
import io.vavr.control.Either;

import java.util.List;

public interface DaoPersona {
    Either<String, List<Persona>> getAllPersona();

    Either<String, Persona> getDatosByNombre(String id);

    Either<String, Persona> addPersona(Persona p);

    Either<String, Persona> deletePersona(String id);

    Either<String, Persona> actualizarPersona(Persona p);
}
