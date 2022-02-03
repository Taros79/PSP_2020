package quevedo.ServerBasket.EE.rest.equipos;


import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.log4j.Log4j2;
import quevedo.ServerBasket.services.equipos.EquiposService;
import quevedo.ServerBasket.utils.constantes.ConstantesParametros;
import quevedo.common.error.ApiError;
import quevedo.common.modelo.dto.EquipoDTO;
import quevedo.common.utils.PathRest;
import quevedo.common.utils.StringsCommon;

import java.util.concurrent.atomic.AtomicReference;

@Path(PathRest.EQUIPOS_PATH)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Log4j2

public class RestEquipos {

    private final EquiposService equiposService;

    @Inject
    public RestEquipos(EquiposService equiposService) {
        this.equiposService = equiposService;
    }


    @GET
    @RolesAllowed({ConstantesParametros.USER})
    @Path(PathRest.EQUIPOS_FILTRADOS_PATH)
    public Response getPartidosFiltradosPorEquipo(@QueryParam(StringsCommon.NOMBRE) String nombreEquipo) {

        AtomicReference<Response> response = new AtomicReference<>();
        equiposService.getPartidosPorEquipo(nombreEquipo).peek(result ->
                response.set(Response.status(Response.Status.OK).entity(result).build())
        ).peekLeft(
                error -> response.set(Response.status(Response.Status.NOT_FOUND).entity(new ApiError(error)).build())
        );

        return response.get();

    }

    @GET
    @RolesAllowed({ConstantesParametros.USER})
    public Response getAllPartidos() {

        AtomicReference<Response> response = new AtomicReference<>();
        equiposService.getAllEquipos().peek(result ->
                response.set(Response.status(Response.Status.OK).entity(result).build())
        ).peekLeft(
                error -> response.set(Response.status(Response.Status.NOT_FOUND).entity(new ApiError(error)).build())
        );

        return response.get();

    }

    @POST
    @RolesAllowed({ConstantesParametros.ADMIN_ROLE})
    public Response addEquipo(EquipoDTO equipoDTO) {

        AtomicReference<Response> response = new AtomicReference<>();
        equiposService.addEquipo(equipoDTO).peek(result ->
                response.set(Response.status(Response.Status.OK).entity(result).build())
        ).peekLeft(
                error -> response.set(Response.status(Response.Status.NOT_FOUND).entity(new ApiError(error)).build())
        );

        return response.get();

    }


}
