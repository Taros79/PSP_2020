package EE.servlet;

import EE.Constantes;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "ServletP", value = Constantes.SERVLET_PINTURA)
public class Servlet2 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(Constantes.TEXT_HTML);
        var colorServletPintura = request.getSession().getAttribute("color");
        response.getWriter().println("<center><body bgcolor=" + colorServletPintura + "></body></center>");
        response.getWriter().close();
        request.getSession().setAttribute("color", null);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
