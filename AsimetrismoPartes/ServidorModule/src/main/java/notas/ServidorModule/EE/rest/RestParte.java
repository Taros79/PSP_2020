package notas.ServidorModule.EE.rest;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import notas.CommonModule.constantes.ConstantesRest;
import notas.CommonModule.modelo.Parte;
import notas.ServidorModule.servicios.ServiciosParte;
import notas.ServidorModule.utils.Constantes;

@Path(ConstantesRest.PATH_PARTES)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestParte {

    private final ServiciosParte serviciosParte;

    @Inject
    public RestParte(ServiciosParte serviciosParte) {
        this.serviciosParte = serviciosParte;
    }

 /*   @RolesAllowed({Constantes.JEFE_DE_ESTUDIOS, Constantes.PROFESOR})
    @GET
    public List<Parte> getAllPartes() {
        return serviciosParte.getAllPartes();
    }

    @GET
    @Path(ConstantesRest.PATH_PARTES_ALUMNOS_BY_USUARIO)
    public List<Parte> getPartesByUser(@QueryParam("idPadre") int idPadre) {
        return serviciosParte.getPartesByUser(idPadre);
    }*/

    @RolesAllowed({Constantes.PROFESOR})
    @POST
    public Integer addParte(Parte parte) {
        return serviciosParte.addParte(parte);
    }

    @RolesAllowed({Constantes.PROFESOR})
    @POST
    @Path(ConstantesRest.PATH_ADD_PARTE_COMPARTIDO)
    public String addParteCompartido(@QueryParam("username") String username, @QueryParam("idParte") int idParte) {
        return serviciosParte.addParteCompartido(username, idParte);
    }

    @RolesAllowed({Constantes.JEFE_DE_ESTUDIOS})
    @PUT
    public String updateParte(@QueryParam("idParte") int idParte, @QueryParam("estado") int estado) {
        return serviciosParte.updateParte(idParte, estado);
    }
}

/*
netstat -ano | findstr 8080

        Carlos, 17:24
        taskkill /F /PID pid_number.*/
