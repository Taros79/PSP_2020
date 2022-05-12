package rol.Servidor.servicios;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import rol.Common.modelo.Usuario;
import rol.Servidor.dao.DaoUsuario;

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

    public String addUsuario(Usuario u) {
        return daoUsuario.addUsuario(u);
    }

    public String delUsuario(int id) {
        return daoUsuario.delUsuario(id);
    }

    public String updateUsuario(Usuario u) {
        return daoUsuario.updateUsuario(u);
    }

    public Usuario getUsuarioByCorreo(String correo, String pass) {
        return daoUsuario.getUsuarioByCorreo(correo, pass);
    }

    public Usuario getUsuarioByName(String correo) {
        return daoUsuario.getUsuarioByName(correo);
    }

    public Either<String,Usuario> getUsuarioByCorreoCredentials(String correo, String pass) {
        return daoUsuario.getUsuarioByCorreoCredentials(correo, pass);
    }
}
