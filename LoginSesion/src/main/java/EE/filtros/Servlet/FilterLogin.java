package EE.filtros.Servlet;

import dao.modelo.Usuario;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

@WebFilter(filterName = "FilterLogin",urlPatterns = {"/visitas","/logout"})
public class FilterLogin implements Filter {


    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
// codigo para comprobar session usuario

        Usuario u = (Usuario) ((HttpServletRequest)req).getSession().getAttribute("user");
        if (u != null)
            chain.doFilter(req, resp);
        else
            req.getRequestDispatcher("/errorFiltro.html").forward(req,resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
