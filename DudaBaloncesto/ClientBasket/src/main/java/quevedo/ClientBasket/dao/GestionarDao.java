package quevedo.ClientBasket.dao;

import com.google.gson.Gson;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import quevedo.ClientBasket.dao.retrofit.GestionApi;
import quevedo.common.modelo.Mensaje;
import quevedo.common.modelo.dto.EquipoDTO;
import quevedo.common.modelo.dto.JornadaDTO;
import quevedo.common.modelo.dto.PartidoDTO;

import javax.inject.Inject;

public class GestionarDao extends DaoGenerics {


    private final GestionApi gestionApi;
    private final Gson gson;

    @Inject
    public GestionarDao(GestionApi gestionApi, Gson gson) {
        this.gestionApi = gestionApi;
        this.gson = gson;
    }

    public Single<Either<String, EquipoDTO>> addEquipo(EquipoDTO equipoDTO) {

        return safeSingleApicall(gestionApi.addEquipo(equipoDTO), gson);
    }

    public Single<Either<String, JornadaDTO>> addJornada(JornadaDTO jornada) {

        return safeSingleApicall(gestionApi.addJornada(jornada), gson);
    }

    public Single<Either<String, Mensaje>> addPartido(PartidoDTO partidoDTO) {

        return safeSingleApicall(gestionApi.addPartido(partidoDTO), gson);
    }


}
