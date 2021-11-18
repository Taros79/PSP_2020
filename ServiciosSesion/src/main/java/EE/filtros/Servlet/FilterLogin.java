package EE.filtros.Servlet;

import dao.modelo.Usuario;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

@WebFilter(filterName = "FilterLogin", urlPatterns = {"/cesta", "/productos", "/privado/*"})
public class FilterLogin implements Filter {


    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
// codigo para comprobar session usuario

        Usuario g = (Usuario) ((HttpServletRequest) req).getSession().getAttribute("usuario");
        if (Math.random() > 0.5)
            chain.doFilter(req, resp);
        else
            req.getRequestDispatcher("/errorFiltro.html").forward(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
