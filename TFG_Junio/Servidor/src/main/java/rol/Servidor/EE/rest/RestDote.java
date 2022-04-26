package rol.Servidor.EE.rest;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import lombok.extern.log4j.Log4j2;
import rol.Common.constantes.ConstantesRest;
import rol.Common.modelo.Dote;
import rol.Common.modeloAO.RelacionId;
import rol.Servidor.EE.filtros.UsuarioBaneado;
import rol.Servidor.servicios.ServiciosDote;
import rol.Servidor.utils.Constantes;

import java.util.List;

@UsuarioBaneado
@Path(ConstantesRest.PATH_DOTES)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Log4j2
public class RestDote {

    private final ServiciosDote serviciosDote;

    @Inject
    public RestDote(ServiciosDote serviciosDote) {
        this.serviciosDote = serviciosDote;
    }

    @GET
    public List<Dote> getAllDotes() {
        return serviciosDote.getAllDotes();
    }

    @RolesAllowed(Constantes.ADMIN)
    @POST
    public String addDote(Dote d) {
        return serviciosDote.addDote(d);
    }

    @RolesAllowed(Constantes.ADMIN)
    @DELETE
    public String delDote(@QueryParam(ConstantesRest.ID) int id) {
        return serviciosDote.delDote(id);
    }

    @RolesAllowed(Constantes.ADMIN)
    @PUT
    public String updateDote(Dote d) {
        return serviciosDote.updateDote(d);
    }

    @GET
    @Path(ConstantesRest.PATH_DOTES_BY_ID_PERSONAJE)
    public List<Dote> getDotesByIdPersonaje(@QueryParam(ConstantesRest.ID) int id) {
        return serviciosDote.getDotesByIdPersonaje(id);
    }

    @POST
    @Path(ConstantesRest.PATH_DOTE_ADD_TO_PERSONAJE)
    public String addDoteToPersonaje(RelacionId r) {
        return serviciosDote.addDoteToPersonaje(r);
    }

    @DELETE
    @Path(ConstantesRest.PATH_DEL_DOTE_TO_PERSONAJE)
    public String delDoteToPersonaje(@QueryParam("id_Dote") int id_Dote, @QueryParam("id2_Personaje") int id2_Personaje) {
        return serviciosDote.delDoteToPersonaje(id_Dote, id2_Personaje);
    }
}

/*
netstat -ano | findstr 8080

        Carlos, 17:24
        taskkill /F /PID pid_number.*/
