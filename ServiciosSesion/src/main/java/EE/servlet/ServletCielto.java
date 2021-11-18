package EE.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "Cielito", urlPatterns = {"/cielito"})
public class ServletCielto extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        var cielito = (String) request.getSession().getAttribute("cielito");

        if (cielito != null)
            response.getWriter().println(cielito);
        else
            response.getWriter().println("error");
    }
}
