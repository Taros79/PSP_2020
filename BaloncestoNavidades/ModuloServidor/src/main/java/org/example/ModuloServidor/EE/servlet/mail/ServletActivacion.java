package org.example.ModuloServidor.EE.servlet.mail;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet(name = "activacion",urlPatterns = {"/Activacion"})
public class ServletActivacion extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //coges parametro
        LocalDateTime d= LocalDateTime.now();

        LocalDateTime d1  = d.plusSeconds(30);


        //vas a la BD para ver si existe y si la fecha es mayor, y le activas

        //dispacher a un jsp de informacion

    }
}
