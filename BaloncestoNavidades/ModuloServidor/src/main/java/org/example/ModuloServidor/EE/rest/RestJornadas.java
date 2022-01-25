package org.example.ModuloServidor.EE.rest;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.Common.EE.errores.ApiError;
import org.example.Common.EE.utils.ApiRespuesta;
import org.example.Common.modelo.Equipo;
import org.example.Common.modelo.Jornada;
import org.example.ModuloServidor.servicios.ServiciosJornadas;
import org.example.ModuloServidor.servicios.ServiciosPartidos;
import org.example.ModuloServidor.utils.Constantes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Path(Constantes.JORNADAS)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestJornadas {

    private ServiciosJornadas sj;

    @Inject
    public RestJornadas(ServiciosJornadas sj) {
        this.sj = sj;
    }

    @GET
    public Response getAllJornadas() {
        Response response;
        Either<ApiError, List<Jornada>> resultado = sj.getJornadas();
        if (resultado.isRight()) {
            response = Response.status(Response.Status.OK)
                    .entity(resultado.get())
                    .build();
        } else {
            response = Response.status(Response.Status.NOT_FOUND)
                    .entity(resultado.getLeft())
                    .build();
        }
        return response;
    }

    @POST
    public Response addJornada(Jornada jornada) {
        Response response;
        Either<ApiError, ApiRespuesta> resultado = sj.addJornada(jornada);
        if (resultado.isRight()) {
            response = Response.status(Response.Status.ACCEPTED)
                    .entity(resultado.get())
                    .build();
        } else {
            response = Response.status(Response.Status.NOT_FOUND)
                    .entity(resultado.getLeft())
                    .build();
        }
        return response;
    }

    @DELETE
    public Response delJornada(@QueryParam(Constantes.ID) String id) {
        Response response;
        Either<ApiError, ApiRespuesta> resultado = sj.delJornada(id);
        if (resultado.isRight()) {
            response = Response.status(Response.Status.ACCEPTED)
                    .entity(resultado.get())
                    .build();
        } else {
            response = Response.status(Response.Status.NOT_FOUND)
                    .entity(resultado.getLeft())
                    .build();
        }
        return response;
    }

    @PUT
    public Response updateJornada(Jornada j) {
        Response response;
        if (Objects.equals(sj.updateJornada(String.valueOf(j.getId()), String.valueOf(j.getFecha())), Constantes.ACTUALIZADO)) {
            response = Response.status(Response.Status.CREATED)
                    .entity(new ApiRespuesta(Constantes.ACTUALIZADO, LocalDateTime.now()))
                    .build();
        } else {
            response = Response.status(Response.Status.NOT_FOUND)
                    .entity(new ApiError(Constantes.NO_EXISTE_OBJETO, LocalDateTime.now()))
                    .build();
        }

        return response;
    }
}
