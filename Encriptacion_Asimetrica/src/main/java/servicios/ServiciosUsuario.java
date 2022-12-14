package servicios;

import dao.DaoUsuarios;
import dao.Encriptaciones;
import dao.modelo.Secreto;
import dao.modelo.SecretoCompartido;
import dao.modelo.Usuario;
import io.vavr.control.Either;

import javax.inject.Inject;
import java.util.List;

public class ServiciosUsuario {

    private final DaoUsuarios dao;
    private Encriptaciones encriptaciones;

    @Inject
    public ServiciosUsuario(DaoUsuarios dao, Encriptaciones encriptaciones) {
        this.dao = dao;
        this.encriptaciones = encriptaciones;
    }

    public Either<String, Usuario> addUsuario(Secreto s, Usuario usuario) {
        return dao.addUsuarioYSecreto(s, usuario);
    }

    public Either<String, List<Usuario>> getUsuarios() {
        return dao.getUsuarios();
    }

    public Either<String, List<Secreto>> getSecretos() {
        return dao.getSecretos();
    }

    public Either<String, List<SecretoCompartido>> getSecretosCompartidos() {
        return dao.getSecretosCompartidos();
    }

    public Either<String, Secreto> getSecretoPorID(int id) {
        return dao.getSecretoPorID(id);
    }

    public Either<String, String> compartirSecreto(SecretoCompartido s, Usuario u1, Usuario u2) {
        var getClaveCifrada = encriptaciones.desencriptarRSAClaveCifrada(s, u1);
        var encriptacionMaxima = encriptaciones.encriptarRSAConPublicaDestinatario(u2, getClaveCifrada.get());

        SecretoCompartido se = new SecretoCompartido(u2.getNombre(), encriptacionMaxima.get(), s.getIdSecreto());

        return dao.compartirSecreto(se);
    }
}
