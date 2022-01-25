package org.example.ModuloCliente.servicios;

import io.vavr.control.Either;
import org.example.Common.EE.errores.ApiError;
import org.example.Common.EE.utils.ApiRespuesta;
import org.example.Common.modelo.Equipo;
import org.example.Common.modelo.Jornada;
import org.example.ModuloCliente.dao.DaoEquipos;
import org.example.ModuloCliente.dao.DaoJornadas;

import javax.inject.Inject;
import java.util.List;

public class ServiciosJornada {

    private DaoJornadas dao;

    @Inject
    public ServiciosJornada(DaoJornadas dao) {
        this.dao = dao;
    }

    public Either<ApiError, List<Jornada>> getAllJornadas() {return dao.getAllJornadas();}

    public Either<ApiError, Jornada> addJornada(Jornada j) {return dao.addJornada(j);}

    public Either<ApiError, ApiRespuesta> deleteJornada(String id) {return dao.deleteJornada(id);}

    public Either<ApiError, ApiRespuesta> updateJornada(Jornada j) {return dao.updateJornada(j);}
}
