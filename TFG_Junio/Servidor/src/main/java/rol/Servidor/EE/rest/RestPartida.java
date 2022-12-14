package rol.Servidor.EE.rest;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import lombok.extern.log4j.Log4j2;
import rol.Common.constantes.ConstantesRest;
import rol.Common.modelo.Partida;
import rol.Servidor.EE.filtros.UsuarioBaneado;
import rol.Servidor.servicios.ServiciosPartida;

import java.util.List;

//@UsuarioBaneado
@Path(ConstantesRest.PATH_PARTIDAS)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Log4j2
public class RestPartida {

    private final ServiciosPartida serviciosPartida;

    @Inject
    public RestPartida(ServiciosPartida serviciosPartida) {
        this.serviciosPartida = serviciosPartida;
    }

    @GET
    @Path(ConstantesRest.PATH_PARTIDAS_BY_ID_MASTER)
    public List<Partida> getAllPartidasByMaster(@QueryParam(ConstantesRest.ID) int id) {
        return serviciosPartida.getAllPartidasByMaster(id);
    }

    @POST
    public String addPartida(Partida p) {
        return serviciosPartida.addPartida(p);
    }

    @DELETE
    public String delPartida(@QueryParam(ConstantesRest.ID) int id) {
        return serviciosPartida.delPartida(id);
    }

    @PUT
    public String updatePartida(Partida p) {
        return serviciosPartida.updatePartida(p);
    }

    @POST
    @Path(ConstantesRest.PATH_PERSONAJE_TO_PARTIDA)
    public String addPersonajeToPartida(@QueryParam("idPartida") int idPartida, @QueryParam("idPersonaje") int idPersonaje) {
        return serviciosPartida.addPersonajeToPartida(idPartida, idPersonaje);
    }

    @GET
    public List<Partida> getAllPartidas() {
        return serviciosPartida.getAllPartidas();
    }
}
