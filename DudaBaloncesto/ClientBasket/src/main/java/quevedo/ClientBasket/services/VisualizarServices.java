package quevedo.ClientBasket.services;

import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import quevedo.ClientBasket.dao.VisualizarDao;
import quevedo.common.modelo.ResultadosPartidos;
import quevedo.common.modelo.dto.EquipoDTO;
import quevedo.common.modelo.dto.JornadaDTO;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.List;

public class VisualizarServices {

    private final VisualizarDao visualizarDao;

    @Inject
    public VisualizarServices(VisualizarDao visualizarDao) {
        this.visualizarDao = visualizarDao;
    }

    public Single<Either<String, List<ResultadosPartidos>>> getAllPartidos() {
        return visualizarDao.todosLosPartidos();
    }

    public Single<Either<String, List<ResultadosPartidos>>> partidosPorJornadas(LocalDate jornada) {
        return visualizarDao.partidosPorJornada(jornada);
    }

    public Single<Either<String, List<ResultadosPartidos>>> partidosPorEquipo(String nombreEquipo) {
        return visualizarDao.partidosPorEquipo(nombreEquipo);
    }

    public Single<Either<String, List<EquipoDTO>>> allEquipos() {
        return visualizarDao.allEquipos();
    }

    public Single<Either<String, List<JornadaDTO>>> allJornadas() {
        return visualizarDao.allJornadas();
    }


}
