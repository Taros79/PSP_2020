package org.example.ModuloCliente.dao;

import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;
import org.example.Common.EE.errores.ApiError;
import org.example.Common.EE.utils.ApiRespuesta;
import org.example.Common.modelo.Equipo;
import org.example.ModuloCliente.dao.retrofit.EquiposApi;
import org.example.ModuloCliente.gui.Producers;

import javax.inject.Inject;
import java.util.List;

@Log4j2
public class DaoEquipos extends DaoGenerics {

    private final Producers producers;

    @Inject
    public DaoEquipos(Producers producers) {
        this.producers = producers;
    }


    public Either<ApiError, List<Equipo>> getAllEquipos() {
        EquiposApi apiEquipos = producers.createApiEquipos();
        return safeApicall(apiEquipos.getEquipos());
    }

    public Either<ApiError, Equipo> addEquipo(Equipo equipo) {
        EquiposApi apiEquipos = producers.createApiEquipos();
        return this.safeApicall(apiEquipos.addEquipo(equipo));
    }

    public Either<ApiError, ApiRespuesta> deleteEquipo(String u) {
        EquiposApi apiEquipos = producers.createApiEquipos();
        return this.safeApicall(apiEquipos.deleteEquipo(u));
    }

    public Either<ApiError, ApiRespuesta> updateEquipo(Equipo e) {
        EquiposApi apiEquipos = producers.createApiEquipos();
        return this.safeApicall(apiEquipos.updateEquipo(e));

    }
}
