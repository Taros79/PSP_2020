package org.example.ModuloServidor.EE.filtros;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.example.Common.modelo.UsuarioLoginDTO;

import java.io.IOException;

@WebFilter(filterName = "FilterLogin",urlPatterns = {"/visitas","/productos","/privado/*"})
public class FilterLogin implements Filter {


    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        UsuarioLoginDTO u = (UsuarioLoginDTO) ((HttpServletRequest)req).getSession().getAttribute("user");
        if (u != null)
            chain.doFilter(req, resp);
        else
            req.getRequestDispatcher("/errorFiltro.html").forward(req,resp);


    }

    public void init(FilterConfig config) throws ServletException {

    }

}
