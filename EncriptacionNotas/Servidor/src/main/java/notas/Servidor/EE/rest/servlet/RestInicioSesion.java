package notas.Servidor.EE.rest.servlet;

import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import notas.Common.constantes.ConstantesRest;
import notas.Common.modelo.Usuario;
import notas.Servidor.servicios.ServiciosUsuario;

@Path(ConstantesRest.PATH_LOGIN)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestInicioSesion {

    private final ServiciosUsuario su;

    @Context
    private HttpServletRequest httpServletRequest;

    @Inject
    public RestInicioSesion(ServiciosUsuario su) {
        this.su = su;
    }

    @GET
    public Usuario hacerLogin(@QueryParam("nombre") String nombre, @QueryParam("contraseña") String contraseña) {
        return su.getUsuarioByNombre(nombre, contraseña);
    }

    @GET
    @Path(ConstantesRest.PATH_REGISTRO_LOGOUT)
    public String hacerLogout() {
        httpServletRequest.getSession().setAttribute(ConstantesRest.USUARIO_LOGIN, null);
        httpServletRequest.getSession().invalidate();
        return ConstantesRest.SESION_FINALIZADA;
    }
}
