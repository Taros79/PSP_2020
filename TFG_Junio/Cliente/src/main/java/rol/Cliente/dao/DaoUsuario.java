package rol.Cliente.dao;

import com.google.gson.Gson;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;
import rol.Cliente.dao.retrofit.ApiUsuario;
import rol.Common.modelo.Usuario;

import javax.inject.Inject;
import java.util.List;

@Log4j2
public class DaoUsuario extends DaoGenerics {


    private final ApiUsuario apiUsuario;


    @Inject
    public DaoUsuario(Gson gson, ApiUsuario apiUsuario) {
        super(gson);
        this.apiUsuario = apiUsuario;
    }

    public Single<Either<String, List<Usuario>>> getAllUsuarios() {
        return safeSingleApicall(apiUsuario.getAllUsuarios());
    }

    public Single<Either<String, String>> addUsuario(Usuario u) {
        return safeSingleApicall(apiUsuario.addUsuario(u));
    }

    public Single<Either<String, String>> delUsuario(int id) {
        return safeSingleApicall(apiUsuario.delUsuario(id));
    }

    public Single<Either<String, String>> updateUsuario(Usuario u) {
        return safeSingleApicall(apiUsuario.updateUsuario(u));
    }

    public Single<Either<String, Usuario>> hacerLogin() {
        return safeSingleApicall(apiUsuario.hacerLogin());
    }

    public Single<Either<String, String>> hacerLogout() {
        return safeSingleApicall(apiUsuario.hacerLogout());
    }
}
