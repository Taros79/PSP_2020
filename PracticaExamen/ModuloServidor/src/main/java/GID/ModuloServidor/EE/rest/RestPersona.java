package GID.ModuloServidor.EE.rest;

import GID.Commons.EE.utils.ApiRespuesta;
import GID.Commons.dao.modelo.Persona;
import GID.ModuloServidor.EE.errores.ApiError;
import GID.ModuloServidor.servicios.ServiciosPersona;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.time.LocalDateTime;
import java.util.List;

@Path(Constantes.PATH_PERSONAS)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestPersona {

    private final ServiciosPersona sp;

    @Inject
    public RestPersona(ServiciosPersona sp) {
        this.sp = sp;
    }

    @GET
    @Path("/{id}")
    public Response getPersona(@PathParam("id") String id) {
        Response response;
        Either<ApiError, Persona> resultado = sp.getPersona(id);
        if (resultado.isRight()) {
            response = Response.status(Response.Status.OK)
                    .entity(resultado.get())
                    .build();
        } else {
            response = Response.status(Response.Status.NOT_FOUND)
                    .entity(resultado.getLeft())
                    .build();
        }

        return response;
    }

    @GET
    public Response getAllPersona() {
        Response response;
        Either<ApiError, List<Persona>> resultado = sp.getAll();
        if (resultado.isRight()) {
            response = Response.status(Response.Status.OK)
                    .entity(resultado.get())
                    .build();
        } else {
            response = Response.status(Response.Status.NOT_FOUND)
                    .entity(resultado.getLeft())
                    .build();
        }
        return response;
    }

    @POST
    public Response addPersona(Persona p) {
        Response response;
        Either<ApiError, Persona> resultado = sp.addPersona(p);
        if (resultado.isRight()) {
            response = Response.status(Response.Status.CREATED)
                    .entity(resultado.get())
                    .build();
        } else {
            response = Response.status(400, "error")
                    .entity(resultado.getLeft())
                    .build();
        }

        return response;
    }

    @DELETE
    public Response delPersona(@QueryParam("id") String id) {
        Response response;
        Either<ApiError, ApiRespuesta> resultado = sp.borrarPersona(id);
        if (resultado.isRight()) {
            response = Response.status(Response.Status.ACCEPTED)
                    .entity(resultado.get())
                    .build();
        } else {
            response = Response.status(Response.Status.NOT_FOUND)
                    .entity(new ApiError(Constantes.NO_SE_ENCONTRO_EL_OBJETO, LocalDateTime.now()))
                    .build();
        }
        return response;
    }

    @PUT
    public Response casarPersonas(@QueryParam("idH") String idH, @QueryParam("idM") String idM) {
        Response response;
        Either<ApiError, ApiRespuesta> resultado = sp.casamientoPareja(idH, idM);
        if (resultado.isRight()) {
            response = Response.status(Response.Status.OK)
                    .entity(resultado.get())
                    .build();
        } else {
            response = Response.status(Response.Status.NOT_FOUND)
                    .entity(new ApiError(Constantes.NO_SE_ENCONTRO_EL_OBJETO, LocalDateTime.now()))
                    .build();
        }
        return response;
    }

    @PUT
    @Path("/procrear")
    public Response procrear(Persona p, @QueryParam("idPadres") String idPadres) {
        Response response;
        Either<ApiError, ApiRespuesta> resultado = sp.procrear(p, idPadres);
        if (resultado.isRight()) {
            response = Response.status(Response.Status.CREATED)
                    .entity(resultado.get())
                    .build();
        } else {
            response = Response.status(Response.Status.NOT_FOUND)
                    .entity(new ApiError(Constantes.NO_SE_ENCONTRO_EL_OBJETO, LocalDateTime.now()))
                    .build();
        }
        return response;
    }
}
