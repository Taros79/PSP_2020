package org.example.ModuloServidor.EE.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.ModuloServidor.utils.Constantes;

import java.io.IOException;

@WebServlet(name = "ServletLogout", urlPatterns = {Constantes.LOGOUT})
public class ServletLogout extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().setAttribute(Constantes.USER_min, null);
        request.getSession().invalidate();

    }
}
