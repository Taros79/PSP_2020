package quevedo.ClientBasket.dao.retrofit;

import io.reactivex.rxjava3.core.Single;
import quevedo.common.modelo.ResultadosPartidos;
import quevedo.common.modelo.dto.EquipoDTO;
import quevedo.common.modelo.dto.JornadaDTO;
import quevedo.common.utils.PathRest;
import quevedo.common.utils.StringsCommon;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import java.time.LocalDate;
import java.util.List;

public interface VisualizarApi {


    @GET(PathRest.PARTIDOS_PATH)
    Single<List<ResultadosPartidos>> todosLosPartidos();

    @GET(PathRest.JORNADAS_PATH + PathRest.JORNADAS_FILTRADAS_PATH)
    Single<List<ResultadosPartidos>> partidosPorJornada(@Query(StringsCommon.FECHA) LocalDate jornada);

    @GET(PathRest.EQUIPOS_PATH + PathRest.EQUIPOS_FILTRADOS_PATH)
    Single<List<ResultadosPartidos>> partidosPorEquipo(@Query(StringsCommon.NOMBRE) String nombreEquipo);

    @GET(PathRest.EQUIPOS_PATH)
    Single<List<EquipoDTO>> allEquipos();

    @GET(PathRest.JORNADAS_PATH)
    Single<List<JornadaDTO>> allJornadas();


}
