package EE.rest;

import EE.errores.ApiError;
import EE.filtros.Filtered;
import dao.modelo.Usuario;
import org.modelmapper.ModelMapper;
import servicios.ServiciosUsuarios;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestUsuarios {

    @Inject
    private ServiciosUsuarios su;

    @Inject
    private ModelMapper mapper;

    //para todos los metodos
    //@Context HttpServletRequest request;

    @GET
    @Filtered
    @Path("/uno")
    public Response getUsuario(@QueryParam("id") String id,
                               @Context HttpServletRequest request) {

        AtomicReference<Response> r = new AtomicReference();
        su.dameUno(id)
                .peek(usuario -> r.set(Response.ok(usuario).build()))
                .peekLeft(apiError -> r.set(Response.status(Response.Status.NOT_FOUND)
                        .entity(apiError)
                        .build()));

        return r.get();

    }

    @GET
    @Filtered
    @Path("/{id}")
    public Response getUnUsuario(@PathParam("id") String id,
                                 @HeaderParam("kk") String head) {
        AtomicReference<Response> r = new AtomicReference();
        su.dameUno(id)
                .peek(usuario -> r.set(Response.ok().entity(usuario).build()))
                .peekLeft(apiError -> r.set(Response.status(Response.Status.NOT_FOUND)
                        .entity(new ApiError("error not found", LocalDateTime.now()))
                        .build()));

        return r.get();
    }

    @GET
    public List<Usuario> getAllUsuario() {
        return su.dameTodos();
    }

    @POST
    @Filtered
    public Usuario addUsuario(Usuario user) {
        return su.addUser(user);
    }

    @DELETE
    public Usuario delUsuario(Usuario user) {
        return user;
    }
}
