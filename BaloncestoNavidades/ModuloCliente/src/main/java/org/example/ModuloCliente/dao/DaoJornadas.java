package org.example.ModuloCliente.dao;

import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;
import org.example.Common.EE.errores.ApiError;
import org.example.Common.EE.utils.ApiRespuesta;
import org.example.Common.modelo.Equipo;
import org.example.Common.modelo.Jornada;
import org.example.Common.modelo.Partido;
import org.example.ModuloCliente.dao.retrofit.EquiposApi;
import org.example.ModuloCliente.dao.retrofit.JornadasApi;
import org.example.ModuloCliente.dao.retrofit.PartidosApi;
import org.example.ModuloCliente.gui.Producers;

import javax.inject.Inject;
import java.util.List;

@Log4j2
public class DaoJornadas extends DaoGenerics {

    private final Producers producers;

    @Inject
    public DaoJornadas(Producers producers) {
        this.producers = producers;
    }


    public Either<ApiError, List<Jornada>> getAllJornadas() {
        JornadasApi jornadasApi = producers.createApiJornadas();
        return safeApicall(jornadasApi.getJornadas());
    }

    public Either<ApiError, Jornada> addJornada(Jornada jornada) {
        JornadasApi jornadasApi = producers.createApiJornadas();
        return this.safeApicall(jornadasApi.addJornada(jornada));
    }

    public Either<ApiError, ApiRespuesta> deleteJornada(String id) {
        JornadasApi jornadasApi = producers.createApiJornadas();
        return this.safeApicall(jornadasApi.deleteJornada(id));
    }

    public Either<ApiError, ApiRespuesta> updateJornada(Jornada j) {
        JornadasApi jornadasApi = producers.createApiJornadas();
        return this.safeApicall(jornadasApi.updateJornada(j));

    }
}
