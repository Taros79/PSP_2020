package notas.Cliente.Servicios;

import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import notas.Cliente.dao.DaoUsuario;
import notas.Common.modelo.Usuario;

import javax.inject.Inject;
import java.util.List;

public class ServiciosUsuario {

    private DaoUsuario daoUsuario;

    @Inject
    public ServiciosUsuario(DaoUsuario daoUsuario) {
        this.daoUsuario = daoUsuario;
    }

    public Single<Either<String, List<Usuario>>> getAllUsuarios() {
        return daoUsuario.getAllUsuarios();
    }

    public Single<Either<String, Usuario>> hacerLogin(String nombre, String contraseña) {
        return daoUsuario.hacerLogin(nombre, contraseña);
    }

    public Single<Either<String, String>> hacerLogout() {
        return daoUsuario.hacerLogout();
    }
}
