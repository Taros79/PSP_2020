package notas.ClienteModule.Servicios;

import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import notas.ClienteModule.dao.DaoUsuario;
import notas.CommonModule.modelo.Usuario;

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

    public Single<Either<String, Usuario>> hacerLogin(String nombre, String pass) {
        return daoUsuario.hacerLogin(nombre, pass);
    }

    public Single<Either<String, String>> hacerLogout() {
        return daoUsuario.hacerLogout();
    }

    public Single<Either<String, String>> crearKeyStore(Usuario u){
        return daoUsuario.crearKeyStore(u);
    }
}
