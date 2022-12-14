package rol.Servidor.EE.rest;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import lombok.extern.log4j.Log4j2;
import rol.Common.constantes.ConstantesRest;
import rol.Common.modelo.Objeto;
import rol.Common.modelo.Usuario;
import rol.Servidor.EE.filtros.UsuarioBaneado;
import rol.Servidor.servicios.ServiciosUsuario;

import java.util.List;

//@UsuarioBaneado
@Path(ConstantesRest.PATH_USUARIOS)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Log4j2
public class RestUsuario {

    private final ServiciosUsuario serviciosUsuario;

    @Inject
    public RestUsuario(ServiciosUsuario serviciosUsuario) {
        this.serviciosUsuario = serviciosUsuario;
    }

    @GET
    public List<Usuario> getAllUsuarios() {
        return serviciosUsuario.getAllUsuarios();
    }

    @POST
    public Either<String, String> addUsuario(Usuario u) {
        return serviciosUsuario.addUsuario(u);
    }

    @DELETE
    public String delUsuario(@QueryParam(ConstantesRest.ID) int id) {
        return serviciosUsuario.delUsuario(id);
    }

    @PUT
    public String updateUsuario(Usuario u) {
        return serviciosUsuario.updateUsuario(u);
    }

    @GET
    @Path(ConstantesRest.PATH_USUARIO_BY_ID_USUARIO)
    public Usuario getObjetosByIdPersonaje(@PathParam(ConstantesRest.ID) int id) {
        return serviciosUsuario.getUsuarioById(id);
    }
}