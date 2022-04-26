package rol.Servidor.EE.rest;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import lombok.extern.log4j.Log4j2;
import rol.Common.constantes.ConstantesRest;
import rol.Common.modelo.Hechizo;
import rol.Common.modeloAO.RelacionId;
import rol.Servidor.EE.filtros.UsuarioBaneado;
import rol.Servidor.servicios.ServiciosHechizo;
import rol.Servidor.utils.Constantes;

import java.util.List;

@UsuarioBaneado
@Path(ConstantesRest.PATH_HECHIZOS)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Log4j2
public class RestHechizo {

    private final ServiciosHechizo serviciosHechizo;

    @Inject
    public RestHechizo(ServiciosHechizo serviciosHechizo) {
        this.serviciosHechizo = serviciosHechizo;
    }

    @GET
    public List<Hechizo> getAllHechizo() {
        return serviciosHechizo.getAllHechizo();
    }

    @RolesAllowed(Constantes.ADMIN)
    @POST
    public String addHechizo(Hechizo h) {
        return serviciosHechizo.addHechizo(h);
    }

    @RolesAllowed(Constantes.ADMIN)
    @DELETE
    public String delHechizo(@QueryParam(ConstantesRest.ID) int id) {
        return serviciosHechizo.delHechizo(id);
    }

    @RolesAllowed(Constantes.ADMIN)
    @PUT
    public String updateHechizo(Hechizo h) {
        return serviciosHechizo.updateHechizo(h);
    }

    @GET
    @Path(ConstantesRest.PATH_HECHIZOS_BY_ID_PERSONAJE)
    public List<Hechizo> getHechizosByIdPersonaje(@QueryParam(ConstantesRest.ID) int id) {
        return serviciosHechizo.getHechizosByIdPersonaje(id);
    }

    @POST
    @Path(ConstantesRest.PATH_HECHIZO_ADD_TO_PERSONAJE)
    public String addHechizoToPersonaje(RelacionId r) {
        return serviciosHechizo.addHechizoToPersonaje(r);
    }

    @DELETE
    @Path(ConstantesRest.PATH_DEL_HECHIZO_TO_PERSONAJE)
    public String delHechizoToPersonaje(@QueryParam("id_Hechizo") int id_Hechizo, @QueryParam("id2_Personaje") int id2_Personaje) {
        return serviciosHechizo.delHechizoToPersonaje(id_Hechizo, id2_Personaje);
    }
}
