package quevedo.ServerBasket.services.jornadas;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import quevedo.ServerBasket.dao.JornadasDao;
import quevedo.common.modelo.ResultadosPartidos;
import quevedo.common.modelo.dto.JornadaDTO;

import java.time.LocalDate;
import java.util.List;

public class JornadasService {

    private final JornadasDao jornadasDao;

    @Inject
    public JornadasService(JornadasDao jornadasDao) {
        this.jornadasDao = jornadasDao;
    }

    public Either<String, JornadaDTO> addJornada(JornadaDTO jornadaDTO) {

        return jornadasDao.addJornada(jornadaDTO);

    }

    public Either<String, List<ResultadosPartidos>> partidosPorJornada(LocalDate fecha) {

        return jornadasDao.getPartidosPorJornada(fecha);

    }

    public Either<String, List<JornadaDTO>> getAllJornadas() {
        return jornadasDao.getAllJornadas();
    }


}
