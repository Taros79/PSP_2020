package notas.ClienteModule.dao;

import com.google.gson.Gson;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;
import notas.ClienteModule.dao.retrofit.ApiUsuario;
import notas.CommonModule.modelo.Usuario;

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

    public Single<Either<String, Usuario>> hacerLogin(String nombre, String pass) {
        return safeSingleApicall(apiUsuario.hacerLogin(nombre, pass));
    }

    public Single<Either<String, String>> hacerLogout() {
        return safeSingleApicall(apiUsuario.hacerLogout());
    }
}
