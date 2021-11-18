package dao;

import EE.errores.ApiError;
import EE.errores.CustomException;
import dao.modelo.Usuario;
import io.vavr.control.Either;
import jakarta.ws.rs.core.Response;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


/***
 *
 * edte emeetodo
 * @author
 *
 */
public class DaoUsuario {

    private static List<Usuario> usuarios = new ArrayList<>();

    static {
        usuarios.add(new Usuario("1", "nombre", LocalDateTime.now()));
    }


    public DaoUsuario() {


    }

    public Either<ApiError, Usuario> dameUno(String id) {
        Usuario u = usuarios.stream()
                .filter(usuario -> usuario.getId().equals(id))
                .findFirst().orElse(null);
        if (u != null) {
            return Either.right(u);
        } else {
            return Either.left(new ApiError("error not found", LocalDateTime.now()));
        }
    }

    public List<Usuario> dameTodos() {
        if (usuarios.size() == 0) {
            throw new CustomException("lista vacia", Response.Status.NOT_FOUND);
        }
        return usuarios;
    }

    public Usuario addUser(Usuario user) {
        user.setId("" + (usuarios.size() + 1));
        usuarios.add(user);
        return user;
    }

    public boolean borrar(String id) {
        return usuarios.remove(usuarios.stream()
                .filter(usuario -> usuario.getId().equals(id))
                .findFirst().orElse(null));
    }
}
