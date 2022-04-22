package rol.Servidor.EE.filtros;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import rol.Common.constantes.ConstantesRest;
import rol.Common.errores.ApiError;
import rol.Common.modelo.Usuario;


@Provider
@UsuarioBaneado
public class FiltroBaneos implements ContainerRequestFilter {

    @Context
    private HttpServletRequest httpServletRequest;


    @Override
    public void filter(ContainerRequestContext containerRequestContext) {
        Usuario usuario = (Usuario) httpServletRequest.getSession().getAttribute(ConstantesRest.USUARIO_LOGIN);
        if (usuario == null || usuario.getBaneado() == 1) {
            containerRequestContext.abortWith(Response.status(Response.Status.BAD_REQUEST)
                    .entity(ApiError.builder().message(ConstantesRest.NO_PUEDE_ACCEDER_A_ESTE_LUGAR_BANEADO).build())
                    .type(MediaType.APPLICATION_JSON_TYPE).build());
        }
    }
}
