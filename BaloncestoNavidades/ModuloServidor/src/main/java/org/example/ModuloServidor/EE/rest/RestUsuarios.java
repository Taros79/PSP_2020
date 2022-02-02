package org.example.ModuloServidor.EE.rest;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.vavr.control.Either;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.Common.EE.errores.ApiError;
import org.example.Common.EE.utils.ApiRespuesta;
import org.example.Common.modelo.Usuario;
import org.example.Common.modelo.UsuarioLoginDTO;
import org.example.ModuloServidor.servicios.ServiciosUsuarios;
import org.example.ModuloServidor.utils.Constantes;

import java.security.Key;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Path(Constantes.USUARIOS)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestUsuarios {

    private ServiciosUsuarios su;

    private final Key key;

    @Inject
    public RestUsuarios(ServiciosUsuarios su, @Named("JWT") Key key) {
        this.su = su;
        this.key = key;
    }


    @GET
    @RolesAllowed(Constantes.ADMIN)
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
    @RolesAllowed(Constantes.ADMIN)
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
    @RolesAllowed(Constantes.ADMIN)
    public Response updateUsuario(Usuario u) {
        Response response;
        if (Objects.equals(su.updateUsuario(u.getCodActivacion(), u.getIsActivo(),
                u.getFechaAlta(), u.getUsername()), Constantes.ACTUALIZADO)) {
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
