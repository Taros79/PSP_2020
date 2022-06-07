package notas.ClienteModule.dao;

import com.google.gson.Gson;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;
import notas.ClienteModule.dao.retrofit.ApiParte;
import notas.CommonModule.modelo.Parte;
import notas.CommonModule.modeloDTO.ParteDesencriptadoDTO;
import notas.CommonModule.modeloDTO.ParteProfesorPadre;

import javax.inject.Inject;
import java.util.List;

@Log4j2
public class DaoParte extends DaoGenerics {

    private final ApiParte apiParte;

    @Inject
    public DaoParte(Gson gson, ApiParte apiParte) {
        super(gson);
        this.apiParte = apiParte;
    }

    public Single<Either<String, List<ParteDesencriptadoDTO>>> getPartesByUser(int idUsuario) {
        return safeSingleApicall(apiParte.getPartesByUser(idUsuario));
    }

    public Single<Either<String, String>> addParte(ParteProfesorPadre parte) {
        return safeSingleApicall(apiParte.addParte(parte));
    }

    public Either<String, Integer> addParteTO(Parte parte) {
        return construirEither(apiParte.addParteTO(parte));
    }

    public Single<Either<String, String>> addParteCompartido(int idUsuario, int idParte) {
        return safeSingleApicall(apiParte.addParteCompartido(idUsuario, idParte));
    }

    public Single<Either<String, String>> updateParte(int idParte, int estado) {
        return safeSingleApicall(apiParte.updateParte(idParte, estado));
    }

    public Single<Either<String, String>> firmarPartePadre(int idUsuario, int idParte, String mensaje) {
        return safeSingleApicall(apiParte.firmarPartePadre(idUsuario, idParte, mensaje));
    }
}
