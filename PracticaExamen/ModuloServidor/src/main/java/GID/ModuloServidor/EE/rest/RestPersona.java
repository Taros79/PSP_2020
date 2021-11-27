package GID.ModuloServidor.EE.rest;

import GID.Commons.dao.modelo.Persona;
import GID.ModuloServidor.EE.errores.ApiError;
import GID.ModuloServidor.servicios.ServiciosPersona;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Path("/persona")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestPersona {

    private ServiciosPersona sp;

    @Inject
    public RestPersona(ServiciosPersona sp) {
        this.sp = sp;
    }

    @GET
    @Path("/{id}")
    public Response getPokemon(@PathParam("id") String id) {
        AtomicReference<Response> r = new AtomicReference();
        sp.getPersona(id)
                .peek(persona -> r.set(Response.ok().entity(persona).build()))
                .peekLeft(apiError -> r.set(Response.status(Response.Status.NOT_FOUND)
                        .entity(ApiError.builder()
                                .message(Constantes.ERROR_CON_EL_OBJETO)
                                .fecha(LocalDateTime.now())
                                .build())
                        .build()));

        return r.get();
    }

    @GET
    @Path("/getAll")
    public List<Persona> getAllPersona() {
        return sp.getAll();
    }

    @POST
    public Response addPersona(Persona p) {
        Response response;
        Persona persona = sp.addPersona(p);

        if (persona != null) {
            response = Response.ok().entity(persona).build();
        } else {
            response = Response.status(Response.Status.NOT_MODIFIED)
                    .entity(ApiError.builder()
                            .message(Constantes.ERROR_CON_EL_OBJETO)
                            .fecha(LocalDateTime.now())
                            .build())
                    .build();
        }
        return response;
    }

    @DELETE
    public Response delPersona(@QueryParam("id") String id) {
        if (sp.borrarPersona(id))
            return Response.status(Response.Status.NO_CONTENT).build();
        else
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(ApiError.builder()
                            .message(Constantes.ERROR_CON_EL_OBJETO)
                            .fecha(LocalDateTime.now())
                            .build())
                    .build();
    }

    @PUT
    public Response actualizarPersona(Persona p) {
        Response response;
        Persona persona = sp.actualizarPersona(p);

        if (persona != null) {
            response = Response.ok().entity(persona).build();
        } else {
            response = Response.status(Response.Status.BAD_REQUEST)
                    .entity(ApiError.builder()
                            .message(Constantes.ERROR_CON_EL_OBJETO)
                            .fecha(LocalDateTime.now())
                            .build())
                    .build();
        }
        return response;
    }
}
