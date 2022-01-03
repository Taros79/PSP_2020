package org.example.ModuloServidor.EE.servlet.mail;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet(name = "Activacion",urlPatterns = {"/activacion"})
public class ServletActivacion extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //coges parametro
        String cod = req.getParameter("codigo");
        System.out.println(cod);
        System.out.println("pepe");
        LocalDateTime d= LocalDateTime.now();

        LocalDateTime d1  = d.plusSeconds(30);


        //vas a la BD para ver si existe y si la fecha es mayor, y le activas

        //dispacher a un jsp de informacion

    }
}
