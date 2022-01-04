package org.example.ModuloServidor.EE.servlet.mail;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.ModuloServidor.utils.Utils;

import java.io.IOException;


@WebServlet(name = "ServletMail",urlPatterns = {"/mail"})
public class ServletMail extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MandarMail mail = new MandarMail();

        String correo = request.getParameter("correo");
        String username = request.getParameter("username");

        try {
            mail.generateAndSendEmail(correo, "<html> <a href=\"http://localhost:8080/ModuloServidor-1.0-SNAPSHOT/activacion?codigo="+ Utils.randomBytes()+"&username="+ username+ "\" >Activa tu cuenta </a> " + username + "</html>"
                    , "Activacion API-Baloncesto");
            response.getWriter().println("Codigo enviado, comprueba tu email (podria estar en spam o no)");
        } catch (Exception e) {
            response.getWriter().println(e.getMessage());

        }


    }
}
