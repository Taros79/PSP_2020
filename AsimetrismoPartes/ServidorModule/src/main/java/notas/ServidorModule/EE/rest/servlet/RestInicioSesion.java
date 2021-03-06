package notas.ServidorModule.EE.rest.servlet;

import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import notas.CommonModule.constantes.ConstantesRest;
import notas.CommonModule.modelo.Usuario;
import notas.ServidorModule.EE.security.encriptaciones.KeyStoreBuild;
import notas.ServidorModule.servicios.ServiciosUsuario;

@Path(ConstantesRest.PATH_LOGIN)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestInicioSesion {

    private final ServiciosUsuario su;

    @Context
    private HttpServletRequest httpServletRequest;

    @Inject
    public RestInicioSesion(ServiciosUsuario su, KeyStoreBuild ks) {
        this.su = su;
    }

    @GET
    public Usuario hacerLogin(@QueryParam("nombre") String nombre, @QueryParam("pass") String pass) {
        return su.getUsuarioByNombre(nombre, pass);
    }

    @GET
    @Path(ConstantesRest.PATH_REGISTRO_LOGOUT)
    public String hacerLogout() {
        //No hace falta en verdad, pero es una prueba
        httpServletRequest.getSession().setAttribute(ConstantesRest.USUARIO_LOGIN, null);
        httpServletRequest.getSession().invalidate();
        return ConstantesRest.SESION_FINALIZADA;
    }
}
