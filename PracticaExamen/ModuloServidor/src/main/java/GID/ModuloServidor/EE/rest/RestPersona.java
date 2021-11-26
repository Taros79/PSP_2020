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


}
