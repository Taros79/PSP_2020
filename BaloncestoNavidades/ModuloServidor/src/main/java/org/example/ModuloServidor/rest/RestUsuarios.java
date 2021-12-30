package org.example.ModuloServidor.rest;

import io.vavr.control.Either;
import jakarta.ws.rs.core.Response;
import org.example.Common.EE.errores.ApiError;
import org.example.ModuloServidor.dao.modelo.Usuario;
import org.example.ModuloServidor.dao.modelo.UsuarioEntity;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
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

    //para todos los metodos
    //@Context HttpServletRequest request;

    @GET
    @Path("/dameTodos")
    public List<Usuario> getAllUsuario() {
        return su.dameTodos();
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
    @Path("/hib")
    public List<UsuarioEntity> getAllUsuarioHibernate() {
        return su.dameTodosHibernate();
    }

}
