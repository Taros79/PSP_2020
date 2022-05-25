package notas.ServidorModule.servicios;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import notas.CommonModule.modelo.Usuario;
import notas.ServidorModule.dao.DaoUsuario;

import java.util.List;

public class ServiciosUsuario {

    private final DaoUsuario daoUsuario;

    @Inject
    public ServiciosUsuario(DaoUsuario daoUsuario) {
        this.daoUsuario = daoUsuario;
    }

    public List<Usuario> getAllUsuarios() {
        return daoUsuario.getAllUsuarios();
    }

    public Usuario getUsuarioByNombre(String nombre, String pass) {
        return daoUsuario.getUsuarioByNombre(nombre, pass);
    }

    public Usuario getUsuarioById(int idAlumno) {
        return daoUsuario.getUsuarioById(idAlumno);
    }

    public Either<String, Usuario> getUsuarioByCorreoCredentials(String correo, String pass) {
        return daoUsuario.getUsuarioByCorreoCredentials(correo, pass);
    }
}
