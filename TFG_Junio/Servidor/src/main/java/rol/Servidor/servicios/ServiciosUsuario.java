package rol.Servidor.servicios;

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
}
