package dao;

import EE.errores.ApiError;
import dao.modelo.Usuario;
import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class DaoUsuario {

    private static final List<Usuario> usuarios = new ArrayList<>();

    static {
        usuarios.add(new Usuario("1","Admin","Admin"));
    }


    public DaoUsuario() {
    }

    public Usuario addUser(Usuario user)
    {
        user.setId("" + (usuarios.size()+1));
        usuarios.add(user);
        return user;
    }

    public Either<ApiError, Usuario> dameUsuarioPorNombre(String nombre)
    {
        Usuario u = usuarios.stream()
                .filter(usuario -> usuario.getName().equals(nombre))
                .findFirst().orElse(null);
        if (u!=null) {
            return Either.right(u);
        }
        else
        {
            return Either.left(new ApiError("error not found", LocalDateTime.now()));
        }
    }
}
