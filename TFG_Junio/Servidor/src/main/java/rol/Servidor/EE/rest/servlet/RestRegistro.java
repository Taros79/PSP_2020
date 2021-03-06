package rol.Servidor.EE.rest.servlet;

import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import rol.Common.constantes.ConstantesRest;
import rol.Common.modelo.Usuario;
import rol.Servidor.servicios.ServiciosUsuario;
import rol.Servidor.utils.UserSecurity;

@Path(ConstantesRest.PATH_REGISTRO)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestRegistro {

    private final ServiciosUsuario su;

    @Context
    private HttpServletRequest httpServletRequest;

    @Inject
    public RestRegistro(ServiciosUsuario su) {
        this.su = su;
    }

    @GET
    public Usuario hacerLoging(@QueryParam("correo") String correo, @QueryParam("password") String password) {
        return su.getUsuarioByCorreo(correo, password);
    }

    @GET
    @Path(ConstantesRest.PATH_REGISTRO_LOGOUT)
    public String hacerLogout() {
        httpServletRequest.getSession().setAttribute(ConstantesRest.USUARIO_LOGIN, null);
        httpServletRequest.getSession().invalidate();
        return ConstantesRest.SESION_FINALIZADA;
    }
}
