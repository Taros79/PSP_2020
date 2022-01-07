package org.example.ModuloServidor.EE.rest;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.Common.EE.errores.ApiError;
import org.example.Common.EE.utils.ApiRespuesta;
import org.example.Common.modelo.Usuario;
import org.example.Common.modelo.UsuarioLoginDTO;
import org.example.ModuloServidor.servicios.ServiciosUsuarios;
import org.example.ModuloServidor.utils.Constantes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Path(Constantes.USUARIOS)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestUsuarios {

    private ServiciosUsuarios su;

    @Inject
    public RestUsuarios(ServiciosUsuarios su) {
        this.su = su;
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
    @Path(Constantes.USER_LOGIN)
    public Response getUsuarioLogin(@QueryParam(Constantes.USERNAME) String username) {
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

    @DELETE
    public Response delPersona(@QueryParam(Constantes.ID) String u) {
        Response response;
        Either<ApiError, ApiRespuesta> resultado = su.delUsuario(u);
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
    public Response updateDigimon(Usuario u) {
        Response response;
        if (Objects.equals(su.updateUsuario(u.getCodActivacion(), u.getIsActivo(),
                u.getFechaAlta(), u.getUsername()), Constantes.USUARIO_ACTUALIZADO)) {
            response = Response.status(Response.Status.CREATED)
                    .entity(new ApiRespuesta(Constantes.USUARIO_ACTUALIZADO, LocalDateTime.now()))
                    .build();
        } else {
            response = Response.status(Response.Status.NOT_FOUND)
                    .entity(new ApiError(Constantes.USUARIO_NO_EXISTE, LocalDateTime.now()))
                    .build();
        }

        return response;
    }
}
