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
import org.example.Common.modelo.Partido;
import org.example.ModuloServidor.servicios.ServiciosPartidos;

import java.util.List;

@Path("/partidos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestPartidos {

    private ServiciosPartidos su;

    @Inject
    public RestPartidos(ServiciosPartidos su) {
        this.su = su;
    }

    @GET
    public Response getAllPartidos() {
        Response response;
        Either<ApiError, List<Partido>> resultado = su.getPartidos();
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
    @Path("p")
    public Response addPartido(Partido partido) {
        Response response;
        Either<ApiError, ApiRespuesta> resultado = su.addPartido(partido);
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

    @POST
    public Response addEquipo(Equipo equipo) {
        Response response;
        Either<ApiError, ApiRespuesta> resultado = su.addEquipo(equipo);
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

    @POST
    @Path("")
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
