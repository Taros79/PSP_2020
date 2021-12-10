package EE.rest;

import EE.errores.ApiError;
import dao.modelo.Usuario;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import servicios.ServiciosUsuarios;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicReference;

@Path("/usuario")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestRegistro {

    private ServiciosUsuarios su;

    @Inject
    public RestRegistro(ServiciosUsuarios su) {
        this.su = su;
    }

    @POST
    public Response addUsuario(Usuario user){

        Usuario s = su.addUser(user);
        if (s !=null)
            return Response.status(Response.Status.CREATED).entity(s).build();
        else
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(ApiError.builder()
                            .message("usuario no añadido")
                            .fecha(LocalDateTime.now())
                            .build())
                    .build();
    }

}
