package quevedo.ServerBasket.EE.rest.partidos;


import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.log4j.Log4j2;
import quevedo.ServerBasket.services.partidos.PartidosService;
import quevedo.ServerBasket.utils.constantes.ConstantesParametros;
import quevedo.ServerBasket.utils.constantes.Mensajes;
import quevedo.common.error.ApiError;
import quevedo.common.modelo.Mensaje;
import quevedo.common.modelo.dto.PartidoDTO;
import quevedo.common.utils.PathRest;

import java.util.concurrent.atomic.AtomicReference;

@Path(PathRest.PARTIDOS_PATH)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Log4j2
public class RestPartidos {

    private final PartidosService partidosService;


    @Inject
    public RestPartidos(PartidosService partidosService) {
        this.partidosService = partidosService;
    }


    @GET
    @RolesAllowed({ConstantesParametros.USER})
    public Response getPartidosYresultados() {

        AtomicReference<Response> response = new AtomicReference<>();
        partidosService.getAllPartidosResultados().peek(result ->
                response.set(Response.status(Response.Status.OK).entity(result).build())
        ).peekLeft(
                error -> response.set(Response.status(Response.Status.NOT_FOUND).entity(new ApiError(error)).build())
        );

        return response.get();

    }

    @POST
    @RolesAllowed({ConstantesParametros.ADMIN_ROLE})
    public Response addPartido(PartidoDTO partidoDTO) {

        AtomicReference<Response> response = new AtomicReference<>();
        partidosService.addPartido(partidoDTO).peek(result ->
                response.set(Response.status(Response.Status.OK).entity(new Mensaje(Mensajes.AÑADIDO)).build())
        ).peekLeft(
                error -> response.set(Response.status(Response.Status.NOT_FOUND).entity(new ApiError(error)).build())
        );

        return response.get();

    }


}
