package org.example.ModuloServidor.EE.rest;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.Common.EE.errores.ApiError;
import org.example.Common.EE.utils.ApiRespuesta;
import org.example.Common.modelo.Equipo;
import org.example.Common.modelo.Usuario;
import org.example.ModuloServidor.servicios.ServiciosEquipo;
import org.example.ModuloServidor.servicios.ServiciosPartidos;
import org.example.ModuloServidor.utils.Constantes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Path(Constantes.EQUIPOS)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestEquipos {

    private ServiciosEquipo se;

    @Inject
    public RestEquipos(ServiciosEquipo se) {
        this.se = se;
    }

    @GET
    public Response getAllEquipos() {
        Response response;
        Either<ApiError, List<Equipo>> resultado = se.getEquipos();
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
    @Path(Constantes.ADD_EQUIPO)
    public Response addEquipo(Equipo equipo) {
        Response response;
        Either<ApiError, ApiRespuesta> resultado = se.addEquipo(equipo);
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
    public Response delEquipo(@QueryParam(Constantes.ID) String u) {
        Response response;
        Either<ApiError, ApiRespuesta> resultado = se.delEquipo(u);
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
    public Response updateEquipo(Equipo e) {
        Response response;
        if (Objects.equals(se.updateEquipo(e.getNombre(), String.valueOf(e.getIdEquipo())), Constantes.ACTUALIZADO)) {
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
