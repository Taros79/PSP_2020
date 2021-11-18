package EE.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "ServletErrores", urlPatterns = {"/errores"})
public class ServletErrores extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (Math.random() > 0.5) {
            response.setStatus(500);
            response.getWriter().println("no hay productos");
        } else
            response.getWriter().println("TODO OK");

    }
}
