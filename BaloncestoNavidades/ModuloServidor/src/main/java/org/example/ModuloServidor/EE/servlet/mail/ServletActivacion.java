package org.example.ModuloServidor.EE.servlet.mail;


import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.ModuloServidor.servicios.ServiciosUsuarios;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

@WebServlet(name = "ServletActivacion", urlPatterns = {"/activacion"})
public class ServletActivacion extends HttpServlet {

    private ServiciosUsuarios serviciosUsuarios;

    @Inject
    public ServletActivacion(ServiciosUsuarios serviciosUsuarios) {
        this.serviciosUsuarios = serviciosUsuarios;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //coges parametro
        String cod = req.getParameter("codigo");
        String user = req.getParameter("username");
        if (!cod.isEmpty() && !user.isEmpty()) {
            var usuario = serviciosUsuarios.getUsuario(user);
            if (usuario.isRight()) {
                LocalDateTime fechaAlta = LocalDateTime.now();
                resp.getWriter().println(serviciosUsuarios.updateUsuario(cod, 1, fechaAlta, user));
                req.getSession().setAttribute("usuario", usuario.get());

                resp.getWriter().println(req.getSession().getAttribute("usuario"));
            } else {
                resp.getWriter().println("Usuario no valido");
            }
        } else {
            resp.getWriter().println("No estas registrado");
        }

        LocalDateTime d = LocalDateTime.now();

        LocalDateTime d1 = d.plusSeconds(30);


        //vas a la BD para ver si existe y si la fecha es mayor, y le activas

        //dispacher a un jsp de informacion

    }
}
