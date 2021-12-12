package servlet;

import dao.modelo.Usuario;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import servicios.ServiciosUsuarios;

import java.io.IOException;

@WebServlet(name = "ServletLogin", urlPatterns = {Constantes.DO_LOGIN})
public class ServletLogin extends HttpServlet {

    private ServiciosUsuarios su;

    @Inject
    public ServletLogin(ServiciosUsuarios su) {
        this.su = su;
    }

    private void llamada(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("userName");
        String pass = request.getParameter("password");

        if (su.login(name, pass)) {
            Usuario u = new Usuario(name, pass);
            request.getSession().setAttribute(Constantes.USER, u);
            request.getRequestDispatcher(Constantes.WELCOME_JSP).forward(request, response);
        } else if (request.getSession().getAttribute(Constantes.USER) != null) {
            request.getRequestDispatcher(Constantes.WELCOME_JSP).forward(request, response);
        } else {
            request.getRequestDispatcher(Constantes.LOGIN_INCORRECTO_JSP).forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        llamada(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        llamada(request, response);
    }

}
