package org.example.ModuloCliente.dao;

import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;
import org.example.Common.EE.errores.ApiError;
import org.example.Common.modelo.Partido;
import org.example.ModuloCliente.dao.retrofit.PartidosApi;
import org.example.ModuloCliente.dao.utils.Constantes;
import org.example.ModuloCliente.gui.Producers;
import retrofit2.Response;

import javax.inject.Inject;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Log4j2
public class DaoPartidos extends DaoGenerics{

    private final Producers producers;

    @Inject
    public DaoPartidos(Producers producers) {
        this.producers = producers;
    }

    public Either<ApiError, List<Partido>> getAllPartidos() {
        Either<ApiError, List<Partido>> partidos;
        PartidosApi partidosApi = producers.createApiPartidos(producers.createRetrofit());
        try {
            Response<List<Partido>> response = partidosApi.getPartidos().execute();
            if (response.isSuccessful()) {
                partidos = Either.right(response.body());
            } else {
                partidos = Either.left(new ApiError(Constantes.OBJETO_NO_VALIDO, LocalDateTime.now()));
            }
        } catch (IOException e) {
            partidos = Either.left(new ApiError(Constantes.PROBLEMA_SERVIDOR, LocalDateTime.now()));
            log.error(e.getMessage(), e);
        }
        return safeApicall(partidosApi.getPartidos());
    }

}
