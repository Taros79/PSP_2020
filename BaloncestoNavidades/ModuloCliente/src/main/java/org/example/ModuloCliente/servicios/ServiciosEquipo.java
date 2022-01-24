package org.example.ModuloCliente.servicios;

import io.vavr.control.Either;
import org.example.Common.EE.errores.ApiError;
import org.example.Common.EE.utils.ApiRespuesta;
import org.example.Common.modelo.Equipo;
import org.example.ModuloCliente.dao.DaoEquipos;
import org.example.ModuloCliente.dao.retrofit.EquiposApi;

import javax.inject.Inject;
import java.util.List;

public class ServiciosEquipo {

    private DaoEquipos dao;

    @Inject
    public ServiciosEquipo(DaoEquipos dao) {
        this.dao = dao;
    }

    public Either<ApiError, List<Equipo>> getAllEquipos() {return dao.getAllEquipos();}

    public Either<ApiError, Equipo> addEquipo(Equipo equipo) {return dao.addEquipo(equipo);}

    public Either<ApiError, ApiRespuesta> deleteEquipo(String u) {return dao.deleteEquipo(u);}

    public Either<ApiError, ApiRespuesta> updateEquipo(Equipo e) {return dao.updateEquipo(e);}
}
