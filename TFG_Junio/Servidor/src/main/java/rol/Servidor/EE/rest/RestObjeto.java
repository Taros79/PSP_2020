package rol.Servidor.EE.rest;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import lombok.extern.log4j.Log4j2;
import rol.Common.constantes.ConstantesRest;
import rol.Common.modelo.Objeto;
import rol.Common.modeloAO.RelacionId;
import rol.Servidor.EE.filtros.UsuarioBaneado;
import rol.Servidor.servicios.ServiciosObjeto;
import rol.Servidor.utils.Constantes;

import java.util.List;

@UsuarioBaneado
@Path(ConstantesRest.PATH_OBJETOS)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Log4j2
public class RestObjeto {

    private final ServiciosObjeto serviciosObjeto;

    @Inject
    public RestObjeto(ServiciosObjeto serviciosObjeto) {
        this.serviciosObjeto = serviciosObjeto;
    }

    @GET
    public List<Objeto> getAllObjetos() {
        return serviciosObjeto.getAllObjetos();
    }

    @RolesAllowed(Constantes.ADMIN)
    @POST
    public String addObjeto(Objeto o) {
        return serviciosObjeto.addObjeto(o);
    }

    @RolesAllowed(Constantes.ADMIN)
    @DELETE
    public String delObjeto(@QueryParam(ConstantesRest.ID) int id) {
        return serviciosObjeto.delObjeto(id);
    }

    @RolesAllowed(Constantes.ADMIN)
    @PUT
    public String updateObjeto(Objeto o) {
        return serviciosObjeto.updateObjeto(o);
    }

    @GET
    @Path(ConstantesRest.PATH_OBJETOS_BY_ID_PERSONAJE)
    public List<Objeto> getObjetosByIdPersonaje(@QueryParam(ConstantesRest.ID) int id) {
        return serviciosObjeto.getObjetosByIdPersonaje(id);
    }

    @POST
    @Path(ConstantesRest.PATH_OBJETO_ADD_TO_PERSONAJE)
    public String addObjetoToPersonaje(RelacionId r) {
        return serviciosObjeto.addObjetoToPersonaje(r);
    }

    @DELETE
    @Path(ConstantesRest.PATH_DEL_OBJETO_TO_PERSONAJE)
    public String delObjetoToPersonaje(@QueryParam("id_Objeto") int id_Objeto, @QueryParam("id2_Personaje") int id2_Personaje) {
        return serviciosObjeto.delObjetoToPersonaje(id_Objeto, id2_Personaje);
    }
}
