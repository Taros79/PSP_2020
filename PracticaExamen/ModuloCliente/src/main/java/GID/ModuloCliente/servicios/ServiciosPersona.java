package GID.ModuloCliente.servicios;

import GID.Commons.dao.modelo.Persona;
import io.vavr.control.Either;

import java.util.List;

public interface ServiciosPersona {

    Either<String, List<Persona>> getAllPersona();

    Either<String, Persona> getDatosByNombre(String id);

    Either<String, Persona> addPersona(Persona p);

    String deletePersona(String id);

    Either<String, Persona> actualizarPersona(Persona p);
}
