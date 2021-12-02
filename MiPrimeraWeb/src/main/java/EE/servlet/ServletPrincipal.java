package EE.servlet;

import EE.Constantes;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "ServletG", value = Constantes.SERVLET_GUARDAR)
public class ServletPrincipal extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        var colIndex = request.getParameter(Constantes.COLOR_ELEGIDO);
        request.getSession().setAttribute("color", colIndex);

        response.getWriter().println("<A HREF=\"http://localhost:8080/MiPrimeraWeb-1.0-SNAPSHOT/ServletPintura\"> A ver si se pinto </A>");
        response.getWriter().close();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
