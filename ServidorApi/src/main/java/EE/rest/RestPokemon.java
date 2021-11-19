package EE.rest;

import EE.errores.ApiError;
import EE.filtros.Filtered;
import EE.filtros.Writer;
import dao.modelo.Pokemon;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.modelmapper.ModelMapper;
import servicios.ServiciosPokemon;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestPokemon {

    private ServiciosPokemon sp;

    private ModelMapper mapper;

    @Inject
    public RestPokemon(ServiciosPokemon su, ModelMapper mapper) {
        this.sp = su;
        this.mapper = mapper;
    }

    //para todos los metodos
    //@Context HttpServletRequest request;

    @GET
    @Writer
    @Path("/{id}")
    public Response getPokemon(@PathParam("id") String id,
                                 @HeaderParam("kk") String head) {
        AtomicReference<Response> r = new AtomicReference();
        sp.getPokemon(id)
                .peek(pokemon -> r.set(Response.ok().entity(pokemon).build()))
                .peekLeft(apiError -> r.set(Response.status(Response.Status.NOT_FOUND)
                        .entity(ApiError.builder()
                                .message("error not found")
                                .fecha(LocalDateTime.now())
                                .build())
                        .build()));

        return r.get();
    }

    @GET
    public List<Pokemon> getAllPokemon() {
        return sp.getAll();
    }

    @POST
    public Pokemon addPokemon(Pokemon user) {
        return sp.addPokemon(user);
    }

    @DELETE
    public Response delPokemon(@QueryParam("id") String id) {
        if (sp.borrarPokemon(id))
            return Response.status(Response.Status.NO_CONTENT).build();
        else
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(ApiError.builder()
                            .message("pokemon no encontrado")
                            .fecha(LocalDateTime.now())
                            .build())
                    .build();
    }
}
