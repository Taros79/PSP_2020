package quevedo.ServerBasket.services.identificacion;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import quevedo.ServerBasket.dao.CambioPasswDao;

public class CambioPasswService {

    private final CambioPasswDao cambioPasswDao;

    @Inject
    public CambioPasswService(CambioPasswDao cambioPasswDao) {
        this.cambioPasswDao = cambioPasswDao;
    }


    public Either<String, Boolean> confirmaUser(String correo) {
        return cambioPasswDao.comprobarUser(correo);
    }

    public Either<String, Boolean> addCodAndTime(String cod, String correo) {
        return cambioPasswDao.addCodPasswAndTime(cod, correo);
    }

    public Either<String, Boolean> modificaPass(String passw, String cod) {
        return cambioPasswDao.modificarPassw(passw, cod);
    }

    public Either<String, Boolean> confirmaTiempo(String cod) {
        return cambioPasswDao.comprobarFecha(cod);
    }


}
