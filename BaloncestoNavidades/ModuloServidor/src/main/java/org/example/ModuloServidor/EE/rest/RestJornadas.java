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
import org.example.ModuloServidor.servicios.ServiciosPartidos;
import org.example.ModuloServidor.utils.Constantes;

import java.util.List;

@Path(Constantes.PARTIDOS)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestJornadas {

    private ServiciosPartidos su;

    @Inject
    public RestJornadas(ServiciosPartidos su) {
        this.su = su;
    }

    @GET
    @Path(Constantes.GET_JORNADAS)
    public Response getAllJornadas() {
        Response response;
        Either<ApiError, List<Jornada>> resultado = su.getJornadas();
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
    @Path(Constantes.ADD_JORNADA)
    public Response addJornada(Jornada jornada) {
        Response response;
        Either<ApiError, ApiRespuesta> resultado = su.addJornada(jornada);
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
}
