package quevedo.ClientBasket.dao;

import com.google.gson.Gson;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;
import quevedo.ClientBasket.dao.retrofit.IdentificacionApi;
import quevedo.common.modelo.Mensaje;
import quevedo.common.modelo.User;
import quevedo.common.modelo.UserLoged;

import javax.inject.Inject;

@Log4j2
public class IdentificacionDao extends DaoGenerics {


    private final IdentificacionApi identificacionApi;
    private final Gson gson;


    @Inject
    public IdentificacionDao(IdentificacionApi identificacionApi, Gson gson) {
        this.identificacionApi = identificacionApi;
        this.gson = gson;

    }


    public Single<Either<String, Mensaje>> register(User user) {

        return safeSingleApicall(identificacionApi.registrarse(user), gson);
    }


    public Single<Either<String, UserLoged>> login(User user) {

        return safeSingleApicall
                (identificacionApi.login(user.getCorreo(), user.getPassword()), gson);
    }


    public Single<Either<String, Void>> cambiarPassw(String correo) {

        return safeSingleApicall(identificacionApi.cambiarPassw(correo), gson);
    }


    public Single<Either<String, Mensaje>> addAdmin(User user) {

        return safeSingleApicall(identificacionApi.registerAdmin(user), gson);
    }


}
