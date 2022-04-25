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
@UsuarioBaneado
public class FiltroBaneos implements ContainerRequestFilter {

    private final SecurityContext securityContext;
    private ServiciosUsuario serviciosUsuario;

    @Inject
    public FiltroBaneos(@Context SecurityContext securityContext, ServiciosUsuario serviciosUsuario) {
        this.securityContext = securityContext;
        this.serviciosUsuario = serviciosUsuario;
    }

    @Override
    public void filter(ContainerRequestContext requestContext) {
        Usuario usuario = serviciosUsuario.getUsuarioByName(this.securityContext.getCallerPrincipal().getName());
        if (usuario.getBaneado() == 1) {
            requestContext.abortWith(Response.status(Response.Status.FORBIDDEN)
                    .type(MediaType.APPLICATION_JSON)
                    .entity(ApiError.builder().message("El usuario con el que intenta acceder está baneado").build())
                    .build());
        }
    }
}
