package notas.ServidorModule.EE.rest;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import notas.CommonModule.constantes.ConstantesRest;
import notas.CommonModule.modeloDTO.ParteDesencriptadoDTO;
import notas.CommonModule.modeloDTO.ParteProfesorPadre;
import notas.ServidorModule.servicios.ServiciosParte;
import notas.ServidorModule.utils.Constantes;

import java.util.List;

@Path(ConstantesRest.PATH_PARTES)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestParte {

    private final ServiciosParte serviciosParte;

    @Inject
    public RestParte(ServiciosParte serviciosParte) {
        this.serviciosParte = serviciosParte;
    }

    @RolesAllowed({Constantes.JEFE_DE_ESTUDIOS, Constantes.PADRE})
    @GET
    public List<ParteDesencriptadoDTO> getPartesByUser(@QueryParam("idUsuario") int idUsuario) {
        return serviciosParte.getPartesByUser(idUsuario);
    }

    @RolesAllowed({Constantes.PROFESOR})
    @POST
    public String addParte(ParteProfesorPadre parte) {
        return serviciosParte.addParte(parte);
    }

    @RolesAllowed({Constantes.PROFESOR})
    @POST
    @Path(ConstantesRest.PATH_ADD_PARTE_COMPARTIDO)
    public String addParteCompartido(@QueryParam("idUsuario") int idUsuario, @QueryParam("idParte") int idParte) {
        return serviciosParte.addParteCompartido(idUsuario, idParte);
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
