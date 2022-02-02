package org.example.ModuloServidor.EE.servlet;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.ModuloServidor.servicios.ServiciosUsuarios;
import org.example.ModuloServidor.utils.Constantes;

import java.io.IOException;

@WebServlet(name = "Login", urlPatterns = {Constantes.DO_LOGIN})
public class ServletLogin extends HttpServlet {

    private ServiciosUsuarios su;

    @Inject
    public ServletLogin(ServiciosUsuarios su) {
        this.su = su;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String passwd = request.getParameter(Constantes.PASSWORD2);
        String user = request.getParameter(Constantes.USERNAME);

        if (su.login(user, passwd)) {
            var usuario = su.getUsuario(user);
            if (usuario.isRight()) {
                if (usuario.get().getIsActivo() == 1) {
                    request.getSession().setAttribute(Constantes.USER_min, usuario.get());
                    response.setStatus(200);
                } else {
                    var i = su.delUsuario(user);
                    response.sendError(500, "Usuario sin validar, su cuenta sera borrada por seguridad :D \n" +
                            "... " +
                            i.get().getMessage());
                }
            } else {
                response.sendError(404, Constantes.NO_EXISTE_OBJETO);
            }
        } else {
            response.sendError(500, Constantes.USUARIO_NO_VALIDO);
        }

    }
}
