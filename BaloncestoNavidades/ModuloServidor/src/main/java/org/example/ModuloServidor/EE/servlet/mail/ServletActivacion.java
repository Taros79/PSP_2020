package org.example.ModuloServidor.EE.servlet.mail;


import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.ModuloServidor.servicios.ServiciosUsuarios;

import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet(name = "ServletActivacion",urlPatterns = {"/activacion"})
public class ServletActivacion extends HttpServlet {

    private ServiciosUsuarios serviciosUsuarios;

    @Inject
    public ServletActivacion(ServiciosUsuarios serviciosUsuarios) {
        this.serviciosUsuarios = serviciosUsuarios;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //coges parametro
        String codigo = req.getParameter("codigo");
        String usuario = req.getParameter("username");
        if (!codigo.isEmpty() && !usuario.isEmpty()) {
            var user = serviciosUsuarios.getUsuarioLogin(usuario);
            if (user.isRight()) {
                req.getSession().setAttribute("usuario", user.get());
                resp.getWriter().println(codigo);
                resp.getWriter().println(req.getSession().getAttribute("usuario"));
            } else {
                resp.getWriter().println("es left");
            }
        } else {
            resp.getWriter().println("sos un pendejo que no se ha registrado");
        }

        LocalDateTime d= LocalDateTime.now();

        LocalDateTime d1  = d.plusSeconds(30);


        //vas a la BD para ver si existe y si la fecha es mayor, y le activas

        //dispacher a un jsp de informacion

    }
}
