package org.example.ModuloServidor.servicios;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import org.example.Common.EE.errores.ApiError;
import org.example.Common.EE.utils.ApiRespuesta;
import org.example.Common.modelo.Jornada;
import org.example.ModuloServidor.dao.DaoJornadas;

import java.util.List;

public class ServiciosJornadas {

    private DaoJornadas dao;


    @Inject
    public ServiciosJornadas(DaoJornadas dao) {
        this.dao = dao;
    }

    public Either<ApiError, List<Jornada>> getJornadas() {
        return dao.getJornadas();
    }

    public Either<ApiError, ApiRespuesta> addJornada(Jornada jornada) {
        return dao.addJornada(jornada);
    }

    public String updateJornada(String date, String id) { return dao.updateJornada(date,id);}

    public Either<ApiError, ApiRespuesta> delJornada(String id) {return dao.delJornada(id);}
}
