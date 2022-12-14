package rol.Servidor.EE.rest;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import lombok.extern.log4j.Log4j2;
import rol.Common.constantes.ConstantesRest;
import rol.Common.modelo.Estadistica;
import rol.Servidor.EE.filtros.UsuarioBaneado;
import rol.Servidor.servicios.ServiciosEstadistica;

//@UsuarioBaneado
@Path(ConstantesRest.PATH_ESTADISTICAS)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Log4j2
public class RestEstadistica {

    private final ServiciosEstadistica serviciosEstadistica;

    @Inject
    public RestEstadistica(ServiciosEstadistica serviciosEstadistica) {
        this.serviciosEstadistica = serviciosEstadistica;
    }

    @GET
    public Estadistica getEstadisticaById(@QueryParam(ConstantesRest.ID) int id) {
        return serviciosEstadistica.getEstadisticaById(id);
    }

    @POST
    public String addEstadistica(Estadistica es) {
        return serviciosEstadistica.addEstadistica(es);
    }

    @DELETE
    public String delEstadistica(@QueryParam(ConstantesRest.ID) int id) {
        return serviciosEstadistica.delEstadistica(id);
    }

    @PUT
    public String updateEstadistica(Estadistica es) {
        return serviciosEstadistica.updateEstadistica(es);
    }
}
