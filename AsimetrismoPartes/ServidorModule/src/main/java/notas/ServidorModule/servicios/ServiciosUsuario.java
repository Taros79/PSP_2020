package notas.ServidorModule.servicios;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import notas.CommonModule.modelo.Usuario;
import notas.ServidorModule.EE.security.encriptaciones.KeyStoreBuild;
import notas.ServidorModule.dao.DaoUsuario;

import java.util.List;

public class ServiciosUsuario {

    private final DaoUsuario daoUsuario;
    private final KeyStoreBuild keyStoreBuild;

    @Inject
    public ServiciosUsuario(DaoUsuario daoUsuario, KeyStoreBuild keyStoreBuild) {
        this.daoUsuario = daoUsuario;
        this.keyStoreBuild = keyStoreBuild;
    }

    public List<Usuario> getAllUsuarios() {
        return daoUsuario.getAllUsuarios();
    }

    public Usuario getUsuarioByNombre(String nombre, String pass) {
        return daoUsuario.getUsuarioByNombre(nombre, pass);
    }

    public Either<String, String> addUsuario(Usuario usuario) {
        daoUsuario.addUsuario(usuario);
        return keyStoreBuild.crearKeystoreYCertificado(usuario);
    }
}
