package EE.filtros.Servlet;

import dao.modelo.Usuario;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import servlet.Constantes;

import java.io.IOException;

@WebFilter(filterName = "FilterLogin", urlPatterns = {Constantes.VISITAS, Constantes.LOGOUT, Constantes.WELCOME_JSP})
public class FilterLogin implements Filter {


    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {

        Usuario u = (Usuario) ((HttpServletRequest) req).getSession().getAttribute(Constantes.USER);
        if (u != null)
            chain.doFilter(req, resp);
        else
            req.getRequestDispatcher(Constantes.LOGIN_INCORRECTO_JSP).forward(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
