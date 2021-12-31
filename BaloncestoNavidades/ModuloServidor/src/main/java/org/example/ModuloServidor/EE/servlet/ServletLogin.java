/*
package org.example.ModuloServidor.EE.servlet;

import dao.modelo.Usuario;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import servicios.ServiciosUsuarios;

import java.io.IOException;

@WebServlet(name = "Login",urlPatterns = {"/doLogin"})
public class ServletLogin extends HttpServlet {

    private ServiciosUsuarios su;

    @Inject
    public ServletLogin(ServiciosUsuarios su) {
        this.su = su;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String passwd = request.getParameter("pass");
        String user = request.getParameter("user");


        // mirara si el suuario es valido

        if (su.login(user,passwd))
        {
            Usuario u = new Usuario(user,passwd);
            request.getSession().setAttribute("user",u);
            response.getWriter().println("LoginOK");
        }
        else
        {
            response.getWriter().println("LoginFalse");

        }


        // si es valido guarda en sesion el usuario y va a un a pagina main con dos links productos o cesta

        // si no pagina error.

    }
}
*/
