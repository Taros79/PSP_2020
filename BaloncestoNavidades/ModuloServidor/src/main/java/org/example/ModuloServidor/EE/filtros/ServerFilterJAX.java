package org.example.ModuloServidor.EE.filtros;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

@Provider
@Filtro
public class ServerFilterJAX implements ContainerRequestFilter {

    @Context
    private HttpServletRequest httpServletRequest;

    @Override
    public void filter(ContainerRequestContext containerRequestContext) {
        if (httpServletRequest.getSession().getAttribute("user") == null) {
            containerRequestContext.abortWith(Response.status(Response.Status.BAD_REQUEST)
                    .entity("Error de autentificacion y/o permisos")
                    .type(MediaType.APPLICATION_JSON_TYPE).build());
        }
    }
}
