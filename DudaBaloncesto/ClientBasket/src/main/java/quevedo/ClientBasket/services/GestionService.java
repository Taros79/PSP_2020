package quevedo.ClientBasket.services;

import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import quevedo.ClientBasket.dao.GestionarDao;
import quevedo.common.modelo.Mensaje;
import quevedo.common.modelo.dto.EquipoDTO;
import quevedo.common.modelo.dto.JornadaDTO;
import quevedo.common.modelo.dto.PartidoDTO;

import javax.inject.Inject;

public class GestionService {


    private final GestionarDao gestionarDao;

    @Inject
    public GestionService(GestionarDao gestionarDao) {
        this.gestionarDao = gestionarDao;
    }

    public Single<Either<String, EquipoDTO>> addEquipo(EquipoDTO equipoDTO) {
        return gestionarDao.addEquipo(equipoDTO);
    }

    public Single<Either<String, JornadaDTO>> addJornada(JornadaDTO jornada) {
        return gestionarDao.addJornada(jornada);
    }

    public Single<Either<String, Mensaje>> addPartido(PartidoDTO partidoDTO) {
        return gestionarDao.addPartido(partidoDTO);
    }


}
