package EE.rest;

import EE.errores.ApiError;
import dao.modelo.Pokemon;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import servicios.ServiciosPokemon;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Path("/pokemon")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestPokemon {

    private ServiciosPokemon sp;

    @Inject
    public RestPokemon(ServiciosPokemon su) {
        this.sp = su;
    }

    @GET
    @Path("/{id}")
    public Response getPokemon(@PathParam("id") String id) {
        AtomicReference<Response> r = new AtomicReference();
        sp.getPokemon(id)
                .peek(pokemon -> r.set(Response.ok().entity(pokemon).build()))
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
    public List<Pokemon> getAllPokemon() {
        return sp.getAll();
    }

    @POST
    public Response addPokemon(Pokemon p) {
        Response response;
        Pokemon pokemon = sp.addPokemon(p);

        if (pokemon != null) {
            response = Response.ok().entity(pokemon).build();
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
    public Response delPokemon(@QueryParam("id") String id) {
        if (sp.borrarPokemon(id))
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
    public Response actualizarPokemon(Pokemon p) {
        Response response;
        Pokemon pokemon = sp.actualizarPokemon(p);

        if (pokemon != null) {
            response = Response.ok().entity(pokemon).build();
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
