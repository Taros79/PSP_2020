package org.example.ModuloServidor.EE.rest;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;
import org.example.Common.EE.errores.ApiError;
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
}
