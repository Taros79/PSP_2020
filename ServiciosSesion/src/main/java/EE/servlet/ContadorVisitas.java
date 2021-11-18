package EE.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "ContadorVisitas", urlPatterns = {"/visitas"})
public class ContadorVisitas extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        var sVisitas = (Integer) request.getSession().getAttribute("visitas");
        if (sVisitas == null) {
            request.getSession().setAttribute("visitas", 0);
        }


        var visitas = ((int) request.getSession().getAttribute("visitas"));

        visitas++;

        request.getSession().setAttribute("visitas", visitas);

        response.getWriter().println("visitas " + visitas);


    }
}
