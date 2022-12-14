package rol.Servidor.EE.rest;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import lombok.extern.log4j.Log4j2;
import rol.Common.constantes.ConstantesRest;
import rol.Common.modelo.Personaje;
import rol.Common.modeloAO.PersonajeBBDD;
import rol.Servidor.EE.filtros.UsuarioBaneado;
import rol.Servidor.servicios.ServiciosPersonaje;
import rol.Servidor.utils.UserSecurity;

import java.util.List;

//@UsuarioBaneado
@Path(ConstantesRest.PATH_PERSONAJES)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Log4j2
public class RestPersonaje {

    private final ServiciosPersonaje serviciosPersonaje;
    private final UserSecurity userSecurity;

    @Inject
    public RestPersonaje(ServiciosPersonaje serviciosPersonaje, UserSecurity userSecurity) {
        this.serviciosPersonaje = serviciosPersonaje;
        this.userSecurity = userSecurity;
    }


    @GET
    public List<Personaje> getAllPersonajes() {
        return serviciosPersonaje.getAllPersonajes();
    }

    @POST
    public String addPersonaje(PersonajeBBDD p) {
        return serviciosPersonaje.addPersonaje(p);
    }

    @DELETE
    public String delPersonaje(@QueryParam("id_Personaje") int id_Personaje, @QueryParam("id_Estadistica") int id_Estadistica) {
        return serviciosPersonaje.delPersonaje(id_Personaje, id_Estadistica);
    }

    @PUT
    public String updatePersonaje(Personaje p) {
        return serviciosPersonaje.updatePersonaje(p);
    }

    @GET
    @Path(ConstantesRest.PATH_PERSONAJES_BY_ID_USUARIO)
    public List<Personaje> getPersonajesByIdUsuario(@QueryParam(ConstantesRest.ID) int id) {
        return serviciosPersonaje.getPersonajesByIdUsuario(id);
    }

   /* @POST
    @Path(ConstantesRest.PATH_PERSONAJE_ADD_TO_USUARIO)
    public String addPersonajeToUsuario(PersonajeBBDD p) {
        return serviciosPersonaje.addPersonajeToUsuario(p, userSecurity.getUserSession().getId());
    }*/

    @POST
    @Path(ConstantesRest.PATH_PERSONAJE_ADD_TO_USUARIO)
    public String addPersonajeToUsuario(PersonajeBBDD p) {
        return serviciosPersonaje.addPersonajeToUsuario(p);
    }
}