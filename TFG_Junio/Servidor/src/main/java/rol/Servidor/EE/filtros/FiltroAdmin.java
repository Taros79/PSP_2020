package rol.Servidor.EE.filtros;

import jakarta.inject.Inject;
import jakarta.security.enterprise.SecurityContext;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import rol.Common.errores.ApiError;
import rol.Common.modelo.Usuario;
import rol.Servidor.servicios.ServiciosUsuario;


@Provider
@Admin
public class FiltroAdmin implements ContainerRequestFilter {

    private final SecurityContext securityContext;
    private ServiciosUsuario serviciosUsuario;

    @Inject
    public FiltroAdmin(@Context SecurityContext securityContext, ServiciosUsuario serviciosUsuario) {
        this.securityContext = securityContext;
        this.serviciosUsuario = serviciosUsuario;
    }

    @Override
    public void filter(ContainerRequestContext requestContext) {
        Usuario usuario = serviciosUsuario.getUsuarioByName(this.securityContext.getCallerPrincipal().getName());
        if (usuario.getTipo_Usuario() != 3) {
            requestContext.abortWith(
                    Response.status(Response.Status.UNAUTHORIZED)
                            .entity(new ApiError("No tiene permisos para realizar esta acción D/Dña " + securityContext.getCallerPrincipal().getName()))
                            .type(MediaType.APPLICATION_JSON_TYPE)
                            .build());
        }
    }
}
