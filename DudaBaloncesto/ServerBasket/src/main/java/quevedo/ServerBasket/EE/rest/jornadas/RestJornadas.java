package quevedo.ServerBasket.EE.rest.jornadas;


import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.log4j.Log4j2;
import quevedo.ServerBasket.services.jornadas.JornadasService;
import quevedo.ServerBasket.utils.constantes.ConstantesParametros;
import quevedo.common.error.ApiError;
import quevedo.common.modelo.dto.JornadaDTO;
import quevedo.common.utils.PathRest;
import quevedo.common.utils.StringsCommon;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicReference;

@Path(PathRest.JORNADAS_PATH)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Log4j2
public class RestJornadas {


    private final JornadasService jornadasService;

    @Inject
    public RestJornadas(JornadasService jornadasService) {
        this.jornadasService = jornadasService;
    }

    @GET
    @RolesAllowed({ConstantesParametros.USER})
    public Response getJornadasFiltradas() {

        AtomicReference<Response> response = new AtomicReference<>();
        jornadasService.getAllJornadas().peek(result ->
                response.set(Response.status(Response.Status.OK).entity(result).build())
        ).peekLeft(
                error -> response.set(Response.status(Response.Status.NOT_FOUND).entity(new ApiError(error)).build())
        );

        return response.get();

    }


    @GET
    @RolesAllowed({ConstantesParametros.USER})
    @Path(PathRest.JORNADAS_FILTRADAS_PATH)
    public Response getAllJornadas(@QueryParam(StringsCommon.FECHA) String fecha) {

        AtomicReference<Response> response = new AtomicReference<>();
        jornadasService.partidosPorJornada(LocalDate.parse(fecha)).peek(result ->
                response.set(Response.status(Response.Status.OK).entity(result).build())
        ).peekLeft(
                error -> response.set(Response.status(Response.Status.NOT_FOUND).entity(new ApiError(error)).build())
        );

        return response.get();

    }

    @POST
    @RolesAllowed({ConstantesParametros.ADMIN_ROLE})
    public Response addJornada(JornadaDTO jornadaDTO) {

        AtomicReference<Response> response = new AtomicReference<>();
        jornadasService.addJornada(jornadaDTO).peek(result ->
                response.set(Response.status(Response.Status.OK).entity(result).build())
        ).peekLeft(
                error -> response.set(Response.status(Response.Status.NOT_FOUND).entity(new ApiError(error)).build())
        );

        return response.get();

    }


}
