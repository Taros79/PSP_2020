package quevedo.ClientBasket.dao.retrofit;

import io.reactivex.rxjava3.core.Single;
import quevedo.common.modelo.Mensaje;
import quevedo.common.modelo.User;
import quevedo.common.modelo.dto.EquipoDTO;
import quevedo.common.modelo.dto.JornadaDTO;
import quevedo.common.modelo.dto.PartidoDTO;
import quevedo.common.utils.PathRest;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface GestionApi {



    @POST(PathRest.EQUIPOS_PATH)
    Single<EquipoDTO> addEquipo(@Body EquipoDTO equipoDTO);


    @POST(PathRest.JORNADAS_PATH)
    Single<JornadaDTO> addJornada(@Body JornadaDTO jornadaDTO);


    @POST(PathRest.PARTIDOS_PATH)
    Single<Mensaje> addPartido(@Body PartidoDTO partidoDTO);


}
