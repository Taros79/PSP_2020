package rol.Servidor.EE.filtros.servlet;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import rol.Common.constantes.ConstantesRest;
import rol.Common.modelo.Usuario;

import java.io.IOException;

@WebFilter(filterName = "FilterLogin", urlPatterns = {ConstantesRest.API + ConstantesRest.PATH_DOTES,
        ConstantesRest.API + ConstantesRest.PATH_ESTADISTICAS, ConstantesRest.API + ConstantesRest.PATH_HECHIZOS,
        ConstantesRest.API + ConstantesRest.PATH_PERSONAJES, ConstantesRest.API + ConstantesRest.PATH_OBJETOS})
public class FiltroLogin implements Filter {


    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {

        Usuario u = (Usuario) ((HttpServletRequest) req).getSession().getAttribute(ConstantesRest.USUARIO_LOGIN);
        if (u != null)
            chain.doFilter(req, resp);
        else
            //((HttpServletResponse) resp).sendRedirect(ConstantesRest.PATH_REGISTRO);
           req.getRequestDispatcher(ConstantesRest.LOGIN_INCORRECTO_JSP).forward(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
