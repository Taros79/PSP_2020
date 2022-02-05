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

    public Either<String, Usuario> addUsuario(Usuario usuario, String pass) {
        return dao.addUsuario(usuario, pass);
    }

    public Either<String, String> getSecretos(String nombre, String pass) {
        return dao.getSecretos(nombre, pass);
    }

    public Either<String, List<Usuario>> getUsuarios() {
        return dao.getUsuarios();
    }

    public Either<String, List<String>> getSecretosPorPass(String password) {
        return dao.getSecretosPorPass(password);
    }
}
