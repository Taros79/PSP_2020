package quevedo.ServerBasket.services.partidos;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import quevedo.ServerBasket.dao.PartidosDao;
import quevedo.common.modelo.ResultadosPartidos;
import quevedo.common.modelo.dto.PartidoDTO;

import java.util.List;

public class PartidosService {

    private final PartidosDao partidosDao;

    @Inject
    public PartidosService(PartidosDao partidosDao) {
        this.partidosDao = partidosDao;
    }

    public Either<String, List<ResultadosPartidos>> getAllPartidosResultados() {
        return partidosDao.getAllPartidos();
    }

    public Either<String, Boolean> addPartido(PartidoDTO partidoDTO) {
        return partidosDao.addPartido(partidoDTO);
    }

}
