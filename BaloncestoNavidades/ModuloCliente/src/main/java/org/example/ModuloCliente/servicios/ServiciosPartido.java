package org.example.ModuloCliente.servicios;

import io.vavr.control.Either;
import org.example.Common.EE.errores.ApiError;
import org.example.Common.EE.utils.ApiRespuesta;
import org.example.Common.modelo.Equipo;
import org.example.Common.modelo.Partido;
import org.example.ModuloCliente.dao.DaoEquipos;
import org.example.ModuloCliente.dao.DaoPartidos;

import javax.inject.Inject;
import java.util.List;

public class ServiciosPartido {

    private DaoPartidos dao;

    @Inject
    public ServiciosPartido(DaoPartidos dao) {
        this.dao = dao;
    }

    public Either<ApiError, List<Partido>> getAllPartido() {
        return dao.getAllPartidos();
    }

    public Either<ApiError, Partido> addPartido(Partido p) {
        return dao.addPartido(p);
    }

    public Either<ApiError, ApiRespuesta> deletePartido(String id) {
        return dao.deletePartido(id);
    }

    public Either<ApiError, ApiRespuesta> updatePartido(Partido p) {
        return dao.updatePartido(p);
    }
}
