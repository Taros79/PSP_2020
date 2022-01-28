package org.example.ModuloServidor.EE.filtros;

import jakarta.inject.Inject;
import jakarta.security.enterprise.SecurityContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
    @Context
    private SecurityContext securityContext;
    @Context
    private HttpServletResponse httpServletResponse;

    @Inject
    public ServerFilterJAX(HttpServletRequest httpServletRequest, SecurityContext securityContext) {
        this.httpServletRequest = httpServletRequest;
        this.securityContext = securityContext;

        //this.httpServletResponse = httpServletResponse;
    }

    @Override
    public void filter(ContainerRequestContext containerRequestContext) {

//    HttpServletRequest httpServletRequest = (HttpServletRequest)containerRequestContext.getRequest();
        jakarta.ws.rs.core.SecurityContext securityContext = containerRequestContext.getSecurityContext();

        if (securityContext.getUserPrincipal() != null) {
            String name = securityContext.getUserPrincipal().getName();
        }

        //AuthenticationStatus status = securityContext.authenticate(httpServletRequest,httpServletResponse,null);

//    if (httpServletRequest.getSession().getAttribute("kk")==null) {
//      //httpServletRequest.getSession().setAttribute("kk",1);
//      containerRequestContext.abortWith(Response.status(Response.Status.BAD_REQUEST)
//              .entity("error de filtro")
//              .type(MediaType.APPLICATION_JSON_TYPE).build());
//    }
    }
}
