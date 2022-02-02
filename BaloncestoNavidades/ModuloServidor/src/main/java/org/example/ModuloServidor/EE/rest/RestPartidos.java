package org.example.ModuloServidor.EE.rest;

import io.vavr.control.Either;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.Common.EE.errores.ApiError;
import org.example.Common.EE.utils.ApiRespuesta;
import org.example.Common.modelo.Partido;
import org.example.ModuloServidor.servicios.ServiciosPartidos;
import org.example.ModuloServidor.utils.Constantes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Path(Constantes.PARTIDOS)
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
    @RolesAllowed(Constantes.ADMIN)
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

    @DELETE
    @RolesAllowed(Constantes.ADMIN)
    public Response delPartido(@QueryParam(Constantes.ID) String ip) {
        Response response;
        Either<ApiError, ApiRespuesta> resultado = su.delPartido(ip);
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
    @RolesAllowed(Constantes.ADMIN)
    public Response updatePartido(Partido p) {
        Response response;
        if (Objects.equals(su.updatePartido(p), Constantes.ACTUALIZADO)) {
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
