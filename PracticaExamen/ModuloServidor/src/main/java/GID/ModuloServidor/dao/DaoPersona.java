package GID.ModuloServidor.dao;

import GID.Commons.dao.modelo.Persona;
import GID.ModuloServidor.EE.errores.ApiError;
import GID.ModuloServidor.EE.errores.CustomException;
import io.vavr.control.Either;
import jakarta.ws.rs.core.Response;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DaoPersona {

    private static List<Persona> personas = new ArrayList<>();

    static {
        personas.add(new Persona("1", "Carlos", "Soltero", "H",
                "Madrid", "2", LocalDateTime.now(),new ArrayList<>()));
    }

    public DaoPersona() {

    }

    public Either<ApiError, Persona> getPersona(String id) {
        Persona p = personas.stream()
                .filter(persona -> persona.getId().equals(id))
                .findFirst().orElse(null);
        if (p!=null) {
            return Either.right(p);
        } else {
            return Either.left(new ApiError("error not found", LocalDateTime.now()));
        }
    }

    public List<Persona> getAll() {
        if (personas.size()==0) {
            throw new CustomException("lista vacia", Response.Status.NOT_FOUND);
        }
        return personas;
    }

    public Persona addPersona(Persona p) {
        int id = Integer.parseInt(personas.get(personas.size() - 1).getId()) + 1;
        p.setId(String.valueOf(id));
        personas.add(p);
        return p;
    }

    public boolean borrarPersona(String id) {
        return personas.remove(personas.stream()
                .filter(persona -> persona.getId().equals(id))
                .findFirst().orElse(null));
    }

    public Persona actualizarPokemon(Persona p) {
        int id = personas.indexOf(p);
        return personas.set(id, p);
    }
}
