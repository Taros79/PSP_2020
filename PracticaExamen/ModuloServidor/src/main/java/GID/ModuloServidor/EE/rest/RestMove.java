package GID.ModuloServidor.EE.rest;

import EE.errores.ApiError;
import dao.modelo.Move;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import servicios.ServiciosMove;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Path("/move")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestMove {

    private ServiciosMove sm;

    @Inject
    public RestMove(ServiciosMove sm) {
        this.sm = sm;
    }

    @GET
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

    @POST
    public Response addMove(Move m) {
        Response response;
        Move move = sm.addMove(m);

        if (move != null) {
            response = Response.ok().entity(move).build();
        } else {
            response = Response.status(Response.Status.NOT_MODIFIED)
                    .entity(ApiError.builder()
                            .message("Movimiento no agregau")
                            .fecha(LocalDateTime.now())
                            .build())
                    .build();
        }
        return response;
    }

    @PUT
    public Response actualizarMove(Move m) {
        Response response;
        Move move = sm.actualizarMove(m);

        if (move != null) {
            response = Response.ok().entity(move).build();
        } else {
            response = Response.status(Response.Status.BAD_REQUEST)
                    .entity(ApiError.builder()
                            .message("Error al actualizar")
                            .fecha(LocalDateTime.now())
                            .build())
                    .build();
        }
        return response;
    }

}
