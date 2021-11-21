package EE.rest;

import EE.errores.ApiError;
import EE.filtros.Writer;
import dao.modelo.Move;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.modelmapper.ModelMapper;
import servicios.ServiciosMove;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Path("/move")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestMove {

    private ServiciosMove sm;

    private ModelMapper mapper;

    @Inject
    public RestMove(ServiciosMove sm, ModelMapper mapper) {
        this.sm = sm;
        this.mapper = mapper;
    }

    //para todos los metodos
    //@Context HttpServletRequest request;

    @GET
    @Writer
    @Path("/{id}")
    public Response getMove(@PathParam("id") String id) {
        AtomicReference<Response> r = new AtomicReference();
        sm.getMove(id)
                .peek(move -> r.set(Response.ok().entity(move).build()))
                .peekLeft(apiError -> r.set(Response.status(Response.Status.NOT_FOUND)
                        .entity(ApiError.builder()
                                .message("error not found")
                                .fecha(LocalDateTime.now())
                                .build())
                        .build()));

        return r.get();
    }

    @GET
    @Path("/getAll")
    public List<Move> getAllMove() {
        return sm.getAll();
    }

    @POST
    public Move addMove(@QueryParam("id") String id,
                        @QueryParam("name") String name,
                        @QueryParam("descripcion") String descripcion) {
        return sm.addMove(new Move(id, name, descripcion));
    }

    @DELETE
    public Response delMove(@QueryParam("id") String id) {
        if (sm.borrarMove(id))
            return Response.status(Response.Status.NO_CONTENT).build();
        else
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(ApiError.builder()
                            .message("Movimiento no encontrado")
                            .fecha(LocalDateTime.now())
                            .build())
                    .build();
    }
}
