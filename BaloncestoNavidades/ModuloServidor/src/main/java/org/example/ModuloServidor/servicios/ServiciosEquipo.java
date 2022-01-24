package org.example.ModuloServidor.servicios;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import org.example.Common.EE.errores.ApiError;
import org.example.Common.EE.utils.ApiRespuesta;
import org.example.Common.modelo.Equipo;
import org.example.Common.modelo.Jornada;
import org.example.Common.modelo.Partido;
import org.example.ModuloServidor.dao.DaoEquipos;
import org.example.ModuloServidor.dao.DaoPartidos;

import java.util.List;

public class ServiciosEquipo {

    private DaoEquipos dao;


    @Inject
    public ServiciosEquipo(DaoEquipos dao) {
        this.dao = dao;
    }

    public Either<ApiError, List<Equipo>> getEquipos() {
        return dao.getEquipos();
    }

    public Either<ApiError, ApiRespuesta> addEquipo(Equipo equipo) {
        return dao.addEquipo(equipo);
    }

    public String updateEquipo(String nombreNew, String id) { return dao.updateEquipo(nombreNew,id);}

    public Either<ApiError, ApiRespuesta> delEquipo(String u) {return dao.delEquipo(u);}
}
