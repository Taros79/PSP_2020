package org.example.ModuloServidor.EE.servlet;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.core.Response;
import org.example.ModuloServidor.servicios.ServiciosUsuarios;

import java.io.IOException;

@WebServlet(name = "Login", urlPatterns = {"/doLogin"})
public class ServletLogin extends HttpServlet {

    private ServiciosUsuarios su;

    @Inject
    public ServletLogin(ServiciosUsuarios su) {
        this.su = su;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String passwd = request.getParameter("password");
        String user = request.getParameter("username");

        if (su.login(user, passwd)) {
            var usuario = su.getUsuario(user);
            if (usuario.isRight()) {
                if(usuario.get().getIsActivo() == 1){
                    request.getSession().setAttribute("user", usuario.get());
                    response.setStatus(200);
                }else{
                    response.sendError(500,"Usuario sin validar, su cuenta sera borrada por seguridad :D");
                }
            }else{
                response.sendError(404,"Usuario no encontrado");
            }
        } else {
            response.sendError(500,"Usuario no valido");
        }

    }
}
