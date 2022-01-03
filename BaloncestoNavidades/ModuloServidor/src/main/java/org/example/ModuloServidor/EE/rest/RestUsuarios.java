package org.example.ModuloServidor.EE.rest;

import io.vavr.control.Either;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.example.Common.EE.errores.ApiError;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MediaType;
import org.example.Common.modelo.Usuario;
import org.example.Common.modelo.UsuarioLoginDTO;
import org.example.ModuloServidor.servicios.ServiciosUsuarios;

import java.util.List;

@Path("/usuarios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestUsuarios {

    private ServiciosUsuarios su;

    @Inject
    public RestUsuarios(ServiciosUsuarios su) {
        this.su = su;
    }

    public RestUsuarios() {
    }

    @GET
    public Response getAllUsuarios() {
        Response response;
        Either<ApiError, List<Usuario>> resultado = su.getUsuarios();
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

    @GET
    @Path("/userLogin")
    public Response getUsuarioLogin(@QueryParam("username") String username) {
        Response response;
        Either<ApiError, UsuarioLoginDTO> resultado = su.getUsuarioLogin(username);
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
