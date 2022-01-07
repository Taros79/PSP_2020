package org.example.ModuloServidor.EE.servlet.mail;


import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.ModuloServidor.servicios.ServiciosUsuarios;
import org.example.ModuloServidor.utils.Constantes;

import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet(name = "ServletActivacion", urlPatterns = {Constantes.ACTIVACION})
public class ServletActivacion extends HttpServlet {

    private ServiciosUsuarios serviciosUsuarios;

    @Inject
    public ServletActivacion(ServiciosUsuarios serviciosUsuarios) {
        this.serviciosUsuarios = serviciosUsuarios;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //coges parametro
        String cod = req.getParameter(Constantes.CODIGO);
        String user = req.getParameter(Constantes.USERNAME);
        if (!cod.isEmpty() && !user.isEmpty()) {
            var usuario = serviciosUsuarios.getUsuario(user);
            if (usuario.isRight()) {
                LocalDateTime fechaAlta = LocalDateTime.now();
                resp.getWriter().println(serviciosUsuarios.updateUsuario(cod, 1, fechaAlta, user));
                req.getSession().setAttribute(Constantes.USUARIO, usuario.get());

                resp.getWriter().println(req.getSession().getAttribute(Constantes.USUARIO));
            } else {
                resp.getWriter().println(Constantes.USUARIO_NO_VALIDO);
            }
        } else {
            resp.getWriter().println(Constantes.NO_ESTAS_REGISTRADO);
        }

        //dispacher a un jsp de informacion

    }
}
