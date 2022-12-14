package rol.Servidor.EE.filtros.servlet;

import jakarta.inject.Inject;
import jakarta.security.enterprise.SecurityContext;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.core.Context;
import rol.Common.constantes.ConstantesRest;

import java.io.IOException;

@WebFilter(filterName = "FilterLogin", urlPatterns = {//ConstantesRest.API + ConstantesRest.PATH_DOTES,
       // ConstantesRest.API + ConstantesRest.PATH_ESTADISTICAS, ConstantesRest.API + ConstantesRest.PATH_HECHIZOS,
       // ConstantesRest.API + ConstantesRest.PATH_PERSONAJES, ConstantesRest.API + ConstantesRest.PATH_OBJETOS
})
public class FiltroLogin implements Filter {

    private final SecurityContext securityContext;


    @Inject
    public FiltroLogin(@Context SecurityContext securityContext) {
        this.securityContext = securityContext;
    }

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        // codigo para comprobar session usuario

        if (securityContext != null && securityContext.getCallerPrincipal() != null && securityContext.getCallerPrincipal().getName() != null)
            chain.doFilter(req, resp);
        else
            ((HttpServletResponse) resp).sendError(HttpServletResponse.SC_FORBIDDEN, "FORBIDEN");

    }


    public void init(FilterConfig config) throws ServletException {

    }

}
