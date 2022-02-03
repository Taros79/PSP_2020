package quevedo.ClientBasket.dao;

import com.google.gson.Gson;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import quevedo.ClientBasket.dao.retrofit.VisualizarApi;
import quevedo.common.modelo.ResultadosPartidos;
import quevedo.common.modelo.dto.EquipoDTO;
import quevedo.common.modelo.dto.JornadaDTO;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.List;

public class VisualizarDao extends DaoGenerics {

    private final VisualizarApi visualizarApi;
    private final Gson gson;

    @Inject
    public VisualizarDao(VisualizarApi visualizarApi, Gson gson) {
        this.visualizarApi = visualizarApi;
        this.gson = gson;
    }

    public Single<Either<String, List<ResultadosPartidos>>> todosLosPartidos() {
        return safeSingleApicall(visualizarApi.todosLosPartidos(), gson);
    }

    public Single<Either<String, List<ResultadosPartidos>>> partidosPorJornada(LocalDate jornada) {
        return safeSingleApicall(visualizarApi.partidosPorJornada(jornada), gson);
    }

    public Single<Either<String, List<ResultadosPartidos>>> partidosPorEquipo(String nombreEquipo) {
        return safeSingleApicall(visualizarApi.partidosPorEquipo(nombreEquipo), gson);
    }

    public Single<Either<String, List<EquipoDTO>>> allEquipos() {
        return safeSingleApicall(visualizarApi.allEquipos(), gson);
    }

    public Single<Either<String, List<JornadaDTO>>> allJornadas() {
        return safeSingleApicall(visualizarApi.allJornadas(), gson);
    }


}
