package servicios;

import dao.DaoUsuarios;
import dao.modelo.Usuario;
import io.vavr.control.Either;

import javax.inject.Inject;
import java.util.List;

public class ServiciosUsuario {

    private final DaoUsuarios dao;

    @Inject
    public ServiciosUsuario(DaoUsuarios dao) {
        this.dao = dao;
    }

    public Either<String, List<Usuario>> getUsuarios() {
        return dao.getUsuarios();
    }
}
