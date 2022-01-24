package org.example.ModuloServidor.servicios;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import org.example.Common.EE.errores.ApiError;
import org.example.Common.EE.utils.ApiRespuesta;
import org.example.Common.modelo.Equipo;
import org.example.Common.modelo.Jornada;
import org.example.Common.modelo.Partido;
import org.example.ModuloServidor.dao.DaoPartidos;

import java.util.List;

public class ServiciosPartidos {

    private DaoPartidos dao;


    @Inject
    public ServiciosPartidos(DaoPartidos dao) {
        this.dao = dao;
    }

    public Either<ApiError, List<Partido>> getPartidos() {
        return dao.getPartidos();
    }

    public Either<ApiError, ApiRespuesta> addPartido(Partido p) {
        return dao.addPartido(p);
    }

    public Either<ApiError, List<Jornada>> getJornadas() {
        return dao.getJornadas();
    }

    public Either<ApiError, ApiRespuesta> addJornada(Jornada jornada) {
        return dao.addJornada(jornada);
    }
}
