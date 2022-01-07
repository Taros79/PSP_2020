package org.example.ModuloServidor.EE.filtros;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import org.example.ModuloServidor.utils.Constantes;

@Provider
@Filtro
public class ServerFilterJAX implements ContainerRequestFilter {

    @Context
    private HttpServletRequest httpServletRequest;

    @Override
    public void filter(ContainerRequestContext containerRequestContext) {
        if (httpServletRequest.getSession().getAttribute(Constantes.USER) == null) {
            containerRequestContext.abortWith(Response.status(Response.Status.BAD_REQUEST)
                    .entity(Constantes.ERROR_PERMISOS)
                    .type(MediaType.APPLICATION_JSON_TYPE).build());
        }
    }
}
