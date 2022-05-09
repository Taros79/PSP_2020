package rol.Cliente.Servicios;

import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import rol.Cliente.dao.DaoUsuario;
import rol.Common.modelo.Usuario;

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

    public Single<Either<String, String>> addUsuario(Usuario u) {
        return daoUsuario.addUsuario(u);
    }

    public Single<Either<String, String>> delUsuario(int id) {
        return daoUsuario.delUsuario(id);
    }

    public Single<Either<String, String>> updateUsuario(Usuario u) {
        return daoUsuario.updateUsuario(u);
    }

    public Single<Either<String, Usuario>> hacerLogin(String correo, String password) {
        return daoUsuario.hacerLogin(correo, password);
    }

    public Single<Either<String, String>> hacerLogout() {
        return daoUsuario.hacerLogout();
    }
}
